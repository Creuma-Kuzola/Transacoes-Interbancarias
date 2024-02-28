/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.controllers;

import com.example.KuzolaBankService.config.component.TransferenciaComponent;
import com.example.KuzolaBankService.config.component.TransferenciaMessage;
import com.example.KuzolaBankService.entities.ContaBancaria;
import com.example.KuzolaBankService.entities.Transferencia;
import com.example.KuzolaBankService.https.utils.ResponseBody;
import com.example.KuzolaBankService.kafka.KafkaTransferenciaProducer;
import com.example.KuzolaBankService.services.implementacao.ContaBancariaServiceImpl;
import com.example.KuzolaBankService.services.implementacao.TransferenciaServiceImpl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.KuzolaBankService.utils.jsonUtils.CustomJsonPojos;
import com.example.KuzolaBankService.utils.pojos.TransferenciaCustomPOJO;
import com.example.KuzolaBankService.utils.pojos.TransferenciaPOJO;
import com.example.KuzolaBankService.utils.pojos.TransferenciaPOJOEmis;
import com.example.KuzolaBankService.utils.pojos.TransferenciaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.KuzolaBankService.config.component.UserInfo;
import com.example.KuzolaBankService.config.component.TransferenciaResponseComponent;
/**
 *
 * @author creuma
 */
@RestController
@RequestMapping("/transferencia")
public class TransferenciaController extends BaseController
{
    @Autowired
    TransferenciaServiceImpl transferenciaServiceImpl;
    KafkaTransferenciaProducer kafkaTransferenciaProducer;
    @Autowired
    ContaBancariaServiceImpl contaBancariaServiceImpl;
    @Autowired
    TransferenciaMessage transferenciaMessage;
    @Autowired
    UserInfo userInfo;

    @Autowired
    TransferenciaResponseComponent transferenciaResponseComponent;

    @Autowired
    TransferenciaComponent transferenciaComponent;

    public TransferenciaController(KafkaTransferenciaProducer kafkaTransferenciaProducer)
    {
        this.kafkaTransferenciaProducer = kafkaTransferenciaProducer;
    }

    @GetMapping("/teste")
    public String transferencia()
    {
        return "Fazer a transferencia";
    }

    @GetMapping
    public ResponseEntity<ResponseBody> findAllTransferencia()
    {
        List<Transferencia> lista = transferenciaServiceImpl.findAllDesc();
        return this.ok("Transferencias encontradas com sucesso!", lista);
    }

    @GetMapping("/historico/debito")
    public ResponseEntity<ResponseBody> findHistoricoTransacoesDebito()
    {
        List<Transferencia> lista = transferenciaServiceImpl.findAllTransacoesDebitadas(userInfo.getUserInfo().get("iban"));
        return this.ok("Transações de Débito encontradas com sucesso!", lista);
    }

    @GetMapping("/historico/credito")
    public ResponseEntity<ResponseBody> findHistoricoTransacoesCreditadas()
    {
        String ibanDestino = userInfo.getUserInfo().get("iban");

        List<Transferencia> lista = transferenciaServiceImpl.findAllTransacoesCreditadas(ibanDestino);
        return this.ok("Transações de Crédito encontradas com sucesso!", lista);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ResponseBody> findTransferenciaByID(@PathVariable Integer id)
    {
        Optional<Transferencia> consulta = this.transferenciaServiceImpl.findById(id);
        if (consulta.isPresent())
        {
            return this.ok("Transferencia encontrada com sucesso.", consulta.get());
        }
        return this.naoEncontrado("Transferencia não encontrada", null);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createTransferencia(@RequestBody Transferencia transferencia)
    {
        //System.out.println("Transferencia"+ transferencia);
        Integer responseVerification = transferenciaServiceImpl.isValidInformationIban(transferencia.getIbanDestinatario(), transferencia.getMontante());
        String ibanOrigem = userInfo.getUserInfo().get("iban");
        Integer isSaldoEnought = contaBancariaServiceImpl
                .isSaldoPositiveToTransfer(new BigInteger(userInfo.getUserInfo().get("accountNumber")), transferencia.getMontante());

        // 1 - Transferencias Intrabancaria
        if(responseVerification == 1)
        {
            if (transferenciaServiceImpl.isTransferenciaInformationValid(transferencia.getIbanDestinatario(), transferencia.getMontante(), userInfo.getUserInfo().get("iban")))
            {
                return this.transferenciaEfectuada(transferenciaServiceImpl.transferenciaIntrabancaria(transferencia, ibanOrigem));
            }
            return  this.erro("ERRO: Informação inválida");
        }
        // 2 - Transferencias Interbancaria
        else if(responseVerification == 2){
            if (isSaldoEnought != -1) {
                TransferenciaCustomPOJO transferenciaCustomPOJO = transferenciaServiceImpl.convertToTransferenciaCustomPOJO(transferencia);
                transferenciaServiceImpl.saveTransferComponent(transferenciaCustomPOJO);
                String data = CustomJsonPojos.criarStrToJson(transferenciaCustomPOJO);
                kafkaTransferenciaProducer.sendMessageTransferenciaIntrabancaria(data);
                try {
                    Thread.sleep(9000);
                    return this.ok("Message: " + transferenciaMessage.getMessage().get("message"), this.transferenciaComponent.getTransferenciaResponse());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            else
            {
                return this.ok("Voce não possui saldo suficiente para efectuar esta operação!: " + transferenciaMessage.getMessage().get("message"), this.transferenciaComponent.getTransferenciaResponse());
            }
        }
        else if(responseVerification == 3){
            return this.erro("ERRO: O iban deve ter 17 digitos");
        } else if (responseVerification == 4 ) {
            return this.erro("ERRO: O montante deve ser um numero > 0");
        }
        return this.erro("Erro!!");
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deleteTransferencia(@PathVariable("id") Integer id)
    {
        return this.ok("Transferencia eliminada com sucesso.", this.transferenciaServiceImpl.eliminar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updateTransferencia(@PathVariable("id") Integer id, @RequestBody Transferencia transferencia)
    {
        return this.ok("Transferencia editada com sucesso.", (Transferencia) transferenciaServiceImpl.editar(id, transferencia));
    }

    @PostMapping("/response")
    public ResponseEntity<String> sendResponseTransferencia(@RequestBody TransferenciaResponse response)
    {
        response.setDescricao(transferenciaResponseComponent.getTransferenciaResponse().get("descricao"));
        response.setStatus(transferenciaResponseComponent.getTransferenciaResponse().get("status").equals("true") ? true : false) ;

        String data =  CustomJsonPojos.TransferenciaResponse(response);
        this.kafkaTransferenciaProducer.sendMessageResponse(data);
        System.out.println(" Resposta do banco kuzola enviada com sucesso! ");
      return  ResponseEntity.ok("Resposta envida com sucesso!" +data) ;
    }

}
