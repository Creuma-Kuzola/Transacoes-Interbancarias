/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.IntermediarioService.controllers;

import com.example.IntermediarioService.component.*;
import com.example.IntermediarioService.entities.Transferencia;
import com.example.IntermediarioService.https.utils.ResponseBody;
import com.example.IntermediarioService.kafka.KafkaTransferenciaProducer;
import com.example.IntermediarioService.services.implementacao.TransferenciaServiceImpl;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import com.example.IntermediarioService.utils.pojos.*;
import com.example.IntermediarioService.utils.pojos.jsonUtils.CustomJsonPojos;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author creuma
 */

@RestController
@RequestMapping("/transferencia")
public class TransferenciaController extends BaseController {

    @Autowired
    TransferenciaServiceImpl transferenciaServiceImpl;
    @Autowired
    BancoComponent bancoComponent;

    @Autowired
    TransferenciaComponent transferenciaComponent;
    @Autowired
    private KafkaTransferenciaProducer kafkaTransferenciaProducer;

    @Autowired
    TransferenciaPojoComponent transferenciaPOJOComponent;
    @Autowired
    TransferenciaResponseComponent transferenciaResponseComponent;

    @Autowired
    UserInfo userInfo;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TransferenciaHistoricoDebitoComponent transferenciaHistoricoDebitoComponent;

    @Autowired
    TransferenciaHistoricoCreditoComponent transferenciaHistoricoCreditoComponent;

    @Autowired
    SaldoResponseComponent saldoResponseComponent;

    @GetMapping
    public ResponseEntity<ResponseBody> findAllTransferencias()
    {
        List<Transferencia> lista = transferenciaServiceImpl.findAll();
        return this.ok("Transferencias encontradas com sucesso!", lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> findTransferenciaByID(@PathVariable Integer id)
    {
        Optional<Transferencia> consulta = this.transferenciaServiceImpl.findById(id);
        if (consulta.isPresent())
        {
            return this.ok("Transferencia encontrada com sucesso.", consulta.get());
        }
        return this.naoEncontrado("Transferencia não encontrada", null);
    }

    @GetMapping("/historico/debito")
    public ResponseEntity<ResponseBody> findHistoricoTransacoesDebito() throws JsonProcessingException {

        if(transferenciaServiceImpl.isKuzolaBankIban(userInfo.getUserInfo().get("iban"))) {
            kafkaTransferenciaProducer.sendClientePojoMiniOfHistoricoDebitoKuzolaBank(transferenciaServiceImpl.convertingIntoClientePojoMiniJson());
            try {
                Thread.sleep(3000);
                return this.historicoDebito(transferenciaHistoricoDebitoComponent.getTransferenciaResponseHistoricoList());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else if (transferenciaServiceImpl.isWakandaBankIban(userInfo.getUserInfo().get("iban"))) {
            kafkaTransferenciaProducer.sendClientePojoMiniOfHistoricoDebitoWakandaBank(transferenciaServiceImpl.convertingIntoClientePojoMiniJson());
            try {
                Thread.sleep(3000);
                return this.historicoDebito(transferenciaHistoricoDebitoComponent.getTransferenciaResponseHistoricoList());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return this.erro("ERRO!");
    }

    @GetMapping("/historico/credito")
    public ResponseEntity<ResponseBody> findHistoricoTransacoesCreditadas() throws JsonProcessingException {

        if(transferenciaServiceImpl.isKuzolaBankIban(userInfo.getUserInfo().get("iban"))) {
            kafkaTransferenciaProducer.sendClientePojoMiniOfHistoricoCreditoKuzolaBank(transferenciaServiceImpl.convertingIntoClientePojoMiniJson());

            try {
                Thread.sleep(3000);
                return this.historicoCredito(transferenciaHistoricoCreditoComponent.getTransferenciaResponseHistoricoList());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else if(transferenciaServiceImpl.isWakandaBankIban(userInfo.getUserInfo().get("iban"))) {

            kafkaTransferenciaProducer.sendClientePojoMiniOfHistoricoCreditoWakandaBank(transferenciaServiceImpl.convertingIntoClientePojoMiniJson());

            try {
                Thread.sleep(3000);
                return this.historicoCredito(transferenciaHistoricoCreditoComponent.getTransferenciaResponseHistoricoList());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        return  this.erro("ERRO");
    }

    @GetMapping("/saldo")
    public ResponseEntity<ResponseBody> findSaldoDoUser() throws JsonProcessingException {

        if(transferenciaServiceImpl.isKuzolaBankIban(userInfo.getUserInfo().get("iban"))) {
            kafkaTransferenciaProducer.sendClientePojoMiniOfSaldoKuzolaBank(transferenciaServiceImpl.convertingIntoClientePojoMiniJson());

            try {
                Thread.sleep(3000);
                return this.ok("Informações do seu saldo",saldoResponseComponent.getSaldoResponse());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else if(transferenciaServiceImpl.isWakandaBankIban(userInfo.getUserInfo().get("iban"))) {

            kafkaTransferenciaProducer.sendClientePojoMiniOfSaldoWakandaBank(transferenciaServiceImpl.convertingIntoClientePojoMiniJson());

            try {
                Thread.sleep(3000);
                return this.ok("Informações do seu saldo",saldoResponseComponent.getSaldoResponse());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        return  this.erro("ERRO");

    }

    @PostMapping("/publishTransferencia")
    public ResponseEntity<String> publishTransferencia(@RequestBody TransferenciaPOJO transferencia)
    {
        try
        {
            transferencia = CustomJsonPojos.convertToTransferenciaPOJO(transferenciaPOJOComponent);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        String data = CustomJsonPojos.criarStrToJson(transferencia);
        kafkaTransferenciaProducer.sendMessage(data);
        return ResponseEntity.ok("Transferencia envida com sucesso no topic");
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createTransferencia(@RequestBody Transferencia transferencia) throws JsonProcessingException {

        CustomJsonPojos customJsonPojos = new CustomJsonPojos();
        System.out.println("Transferencia no metodo"+ transferencia);
        String ibanOrigem = userInfo.getUserInfo().get("iban");

        Integer responseVerification = transferenciaServiceImpl.isValidInformationIban(transferencia.getIbanDestinatario(), ibanOrigem, transferencia.getMontante());
        if (responseVerification == 1) {
            transferenciaServiceImpl.fillingTransactionFields(transferencia, ibanOrigem);
            kafkaTransferenciaProducer.sendMessageTransferenciaInEmis(customJsonPojos.criarStrToJson(transferencia).toString());
        } else if (responseVerification == 2) {
            return erro("O iban de Origem e o iban destinatario não podem ser iguais");
        } else if (responseVerification == 3){
            return erro("O iban destinatario deve ter 17 digitos");
        } else if (responseVerification == 4){
            return erro("O montante deve ser um numero > 0");
        }

        return ok("sjdfkjggh", transferencia);
        //return this.created("Transferencia adicionada com sucesso.", this.transferenciaServiceImpl.criar(transferencia));
    }

    @PostMapping("/interbancaria")
    public  ResponseEntity createTransferenciaInterbancariaEmis(@RequestBody Transferencia transferencia)
    {
        String ibanOrigem = userInfo.getUserInfo().get("iban");
        String accountNumber = userInfo.getUserInfo().get("accountNumber");
        transferencia = transferenciaServiceImpl.fillingTransferenciaFields(transferencia, ibanOrigem);
        CustomJsonPojos.saveTransferComponent(transferencia, transferenciaComponent, ibanOrigem, accountNumber);
        String data = CustomJsonPojos.criarStrToJson2(transferencia, transferenciaComponent);
        kafkaTransferenciaProducer.sendMessageTransferenciaFromEmis(data);
        try {
            Thread.sleep(200);
            return this.ok("Ok tudo bem...", this);
            //return this.ok(""+transferenciaMessage.getMessage().get("message"),transferenciaComponent.getTransferenciaResponse().values()) ;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/sendSolicitacaoTransferenciaKuzola")
    public ResponseEntity sendSolicitacaoTransferenciaKuzola(@RequestBody Transferencia transferencia)
    {
        String data = CustomJsonPojos.convertTransfereciComponentString(transferenciaComponent);

        if(transferenciaComponent.getTransferencia().get("bancoUdentifier").equals("4040"))
        {
            kafkaTransferenciaProducer.sendSolicitacaoTransferenciaKuzola(data);
        }
        else if(transferenciaComponent.getTransferencia().get("bancoUdentifier").equals("1003"))
        {
            kafkaTransferenciaProducer.sendSolicitacaoTransferenciaWakanda(data);
        }
        return this.ok("Envida com sucesso! ",this);
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
        response = CustomJsonPojos.componenteResponseToResponse(transferenciaResponseComponent);
        String data =  CustomJsonPojos.TransferenciaResponse(response);
        this.kafkaTransferenciaProducer.sendMessageTransferenciaResponse(data);
        System.out.println(" Resposta envida com sucesso wakanda bank! " +bancoComponent.geBancoComponent().get("UUID"));
        return  ResponseEntity.ok("Resposta envida com sucesso! para o wakanda bank" +data) ;
    }

    @PostMapping("/responseTokuzola")
    public ResponseEntity<String> sendResponseTransferencia2(@RequestBody TransferenciaResponse response)
    {
        response = CustomJsonPojos.componenteResponseToResponse(transferenciaResponseComponent);
        String data =  CustomJsonPojos.TransferenciaResponse(response);
        this.kafkaTransferenciaProducer.sendMessageTransferenciaResponse2(data);
        System.out.println(" Resposta envida com sucesso wakanda bank! " +bancoComponent.geBancoComponent().get("UUID"));
        return  ResponseEntity.ok("Resposta envida com sucesso! para o wakanda bank" +data) ;
    }

}
