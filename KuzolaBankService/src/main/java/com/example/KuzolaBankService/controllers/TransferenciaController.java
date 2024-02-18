/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.controllers;

import com.example.KuzolaBankService.config.component.TransferenciaMessage;
import com.example.KuzolaBankService.entities.ContaBancaria;
import com.example.KuzolaBankService.entities.Transferencia;
import com.example.KuzolaBankService.https.utils.ResponseBody;
import com.example.KuzolaBankService.kafka.KafkaTransferenciaProducer;
import com.example.KuzolaBankService.kafka.TransferenciaJsonKafkaProducer;
import com.example.KuzolaBankService.services.implementacao.ContaBancariaServiceImpl;
import com.example.KuzolaBankService.services.implementacao.TransferenciaServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.KuzolaBankService.utils.jsonUtils.CustomJsonPojos;
import com.example.KuzolaBankService.utils.pojos.TransferenciaCustomPOJO;
import com.example.KuzolaBankService.utils.pojos.TransferenciaPOJO;
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
    TransferenciaJsonKafkaProducer transferenciaJsonKafkaProducer;
    @Autowired
    ContaBancariaServiceImpl contaBancariaServiceImpl;
    @Autowired
    TransferenciaMessage transferenciaMessage;
    @Autowired
    UserInfo userInfo;

    @Autowired
    TransferenciaResponseComponent transferenciaResponseComponent;

    @Autowired
    private KafkaTransferenciaProducer kafkaTransferenciaProducer;

    Transferencia transferenciaCreated;

    public TransferenciaController(TransferenciaJsonKafkaProducer transferenciaJsonKafkaProducer)
    {
        this.transferenciaJsonKafkaProducer = transferenciaJsonKafkaProducer;
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
        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria = contaBancariaServiceImpl.findContaBancaraByIban(userInfo.getUserInfo().get("iban"));

        List<Transferencia> lista = transferenciaServiceImpl.findAllTransacoesDebitadas(contaBancaria.getPkContaBancaria());
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

    @PostMapping("/publishTransferencia")
    public ResponseEntity<String> publishTranasferencia(@RequestBody TransferenciaCustomPOJO transferencia)
    {
        transferencia.setDatahora(new Date());
        String data = CustomJsonPojos.criarStrToJson(transferencia);

        kafkaTransferenciaProducer.sendMessageTransferenciaIntrabancaria(data);
        return  ResponseEntity.ok("Transferencia enviada" +data);
        /*String montaneStr = transferencia.getMontante().toString();
        System.out.println("valor: " +montaneStr);
        boolean isSaldoEnought = contaBancariaServiceImpl
                .isSaldoPositiveToTransfer
                        (Integer.valueOf(userInfo.getUserInfo().get("accountNumber")), Integer.parseInt(montaneStr));
        if (isSaldoEnought)
        {
            transferencia.setFkContaBancariaOrigem(Integer.valueOf(userInfo.getUserInfo().get("accountNumber")));
            transferencia.setDatahora(new Date());

            saveTransferComponent(transferencia);

            String data = CustomJsonPojos.criarStrToJson(transferencia);
            KafkaTransferenciaProducer.sendMessage(data);
            try {
                Thread.sleep(9000);
                return ResponseEntity.ok("Message:" +
                        " "+transferenciaMessage.getMessage().get("message")+
                        " " +transferenciaComponent.getTransferenciaResponse().values());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        else
        {
            return ResponseEntity.ok("Message: " +
                    "Você possui saldo insuficiente, para " +
                    "efectuar a transfências!" +isSaldoEnought);
        }   */
    }


    @PostMapping
    public ResponseEntity<ResponseBody> createTransferencia(@RequestBody Transferencia transferencia)
    {
        System.out.println("Transferencia"+ transferencia);
        Integer responseVerification = transferenciaServiceImpl.isValidInformationIban(transferencia.getIbanDestinatario());

        System.out.println("responseVerification: " +responseVerification);

        // 1 - Transferencias Intrabancaria
        if(responseVerification == 1)
        {
            if (transferenciaServiceImpl.isTransferenciaInformationValid(transferencia.getIbanDestinatario(), transferencia.getMontante(), userInfo.getUserInfo().get("iban")))
            {
                transferenciaCreated = new Transferencia();
                transferenciaServiceImpl.fillingTransactionFields(transferencia);

                transferenciaCreated = this.transferenciaServiceImpl.criar(transferencia);

                TransferenciaPOJO transferenciaPOJO = transferenciaServiceImpl.convertingIntoTransferenciaPOJO(transferenciaCreated, userInfo.getUserInfo().get("iban"));

                String transferenciaJson = CustomJsonPojos.criarStrToJson(transferenciaPOJO);
                System.out.println("Data Json" + transferenciaJson);

                transferenciaJsonKafkaProducer.sendMessageTransferenciaIntraBancaria(transferenciaJson.toString());
                return this.transferenciaEfectuada(transferenciaCreated);
            }
            return  this.erro("ERRO: Informação inválida");
        }
        // 1 - Transferencias Interbancaria
        else if(responseVerification == 2){
            TransferenciaCustomPOJO transferenciaCustomPOJO = transferenciaServiceImpl.convertToTransferenciaCustomPOJO(transferencia);
            transferenciaServiceImpl.saveTransferComponent(transferenciaCustomPOJO);
            String data = CustomJsonPojos.criarStrToJson(transferenciaCustomPOJO);
            kafkaTransferenciaProducer.sendMessageTransferenciaIntrabancaria(data);
            /*try {
                Thread.sleep(9000);
                return this.ok("Message: " + transferenciaMessage.getMessage().get("message"));

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } */

            return this.ok("",this);
        }
        else{
            return this.erro("ERRO: IBAN inválido");
        }

        //return this.erro("Erro!!");
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


    // http://localhost:8080//transferencia/response
    @PostMapping("/response")
    public ResponseEntity<String> sendResponseTransferencia(@RequestBody TransferenciaResponse response)
    {
        response.setDescricao(transferenciaResponseComponent.getTransferenciaResponse().get("descricao"));
        response.setStatus(transferenciaResponseComponent.getTransferenciaResponse().get("status").equals("true") ? true : false) ;

      String data =  CustomJsonPojos.TransferenciaResponse(response);
       this.kafkaTransferenciaProducer.sendMessageResponse(data);
        System.out.println(" Resposta envida com sucesso! ");
      return  ResponseEntity.ok("Resposta envida com sucesso!" +data) ;
    }

}
