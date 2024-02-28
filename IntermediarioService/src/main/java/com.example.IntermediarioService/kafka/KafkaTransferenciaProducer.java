/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.IntermediarioService.kafka;

import com.example.IntermediarioService.component.BancoComponent;
import com.example.IntermediarioService.entities.Banco;
import com.example.IntermediarioService.entities.Transferencia;
import com.example.IntermediarioService.services.implementacao.BancoServiceImpl;
import com.example.IntermediarioService.services.implementacao.TransferenciaServiceImpl;
import com.example.IntermediarioService.utils.pojos.ClientePojoMini;

import java.util.List;

import com.example.IntermediarioService.utils.pojos.jsonUtils.CustomJsonPojos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 *
 * @author jussyleitecode
 */
@Service
public class KafkaTransferenciaProducer
{
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTransferenciaProducer.class);

    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private BancoServiceImpl bancoServiceImpl;
    private  List<Banco> bancos;
    @Autowired
    private BancoComponent bancoComponent;

    private KafkaConsumerConfig kafkaConsumerConfig;

    @Autowired
    TransferenciaServiceImpl transferenciaServiceImpl;

    private Integer bankUnikeIdentifiedNumber;
    public KafkaTransferenciaProducer(KafkaTemplate<String, String> kafkaTemplate, KafkaConsumerConfig kafkaConsumerConfig)
    {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaConsumerConfig = kafkaConsumerConfig;
    }

    public void sendMessage(String data)
    {
        data = CustomJsonPojos.criarStrToJson(kafkaConsumerConfig.getTransferenciaPOJO());
        LOGGER.info(String.format("Message sent ==> %s ", data.toString()));
        Integer bankId = bancoComponent.geBancoComponent().get("UUID");
        Message<String> message = null;

        if (bankId == 1003)
        {
            System.out.println("PASSOU AQUI: BANCO IDENTIFICADOR KUZOLA: " +bankId);
            message= MessageBuilder
                    .withPayload(data)
                    .setHeader(KafkaHeaders.TOPIC, "transferencia2")
                    .build();
        }
        else if (bankId == 4040)
        {
            System.out.println("PASSOU AQUI: BANCO IDENTIFICADOR WAKANDA: " +bankId);
            message= MessageBuilder
                    .withPayload(data)
                    .setHeader(KafkaHeaders.TOPIC, "transferencia")
                    .build();
        }
        kafkaTemplate.send(message);
    }

    public void sendMessageTransferenciaFromEmis(String data)
    {
        ///data = CustomJsonPojos.criarStrToJson(kafkaConsumerConfig.getTransferenciaPOJO());
        LOGGER.info(String.format("Message sent from emis to cliente ==> %s ", data.toString()));
        Integer bankId = bancoComponent.geBancoComponent().get("UUID");
        Message<String> message = null;

        if (bankId == 1003)
        {
            System.out.println("PASSOU AQUI: BANCO IDENTIFICADOR KUZOLA: " +bankId);
            message= MessageBuilder
                    .withPayload(data)
                    .setHeader(KafkaHeaders.TOPIC, "transferenciaEmis1")
                    .build();
            kafkaTemplate.send(message);
        }
        else if (bankId == 4040)
        {
            System.out.println("PASSOU AQUI: BANCO IDENTIFICADOR WAKANDA: " +bankId);
            message= MessageBuilder
                    .withPayload(data)
                    .setHeader(KafkaHeaders.TOPIC, "transferenciaEmis2")
                    .build();
            kafkaTemplate.send(message);
        }

    }

    public  void sendMessageTransferenciaInEmis(String transferencia) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Transferencia transferenciaDes = objectMapper.readValue(transferencia, Transferencia.class);

        System.out.println("TransferenciaJson"+ transferencia);
        System.out.println("TransferenciaDes"+ transferenciaDes);
        if(transferenciaServiceImpl.isTransferenciaInterbancariaKuzolaBank(transferenciaDes.getibanOrigem(), transferenciaDes.getIbanDestinatario()))
        {
            Message<String> message = MessageBuilder
                    .withPayload(transferencia)
                    .setHeader(KafkaHeaders.TOPIC, "transf-intrabancarias-kb-emis")
                    .build();

            kafkaTemplate.send(message);

        }
        else if(transferenciaServiceImpl.isTransferenciaIntrabancariaWakandaBank(transferenciaDes.getibanOrigem(), transferenciaDes.getIbanDestinatario()))
        {
            Message<String> message = MessageBuilder
                    .withPayload(transferencia)
                    .setHeader(KafkaHeaders.TOPIC, "transf-intrabancarias-wb-emis")
                    .build();

            kafkaTemplate.send(message);
        }
        else if(transferenciaServiceImpl.isTransferenciaInterBancaria(transferenciaDes.getibanOrigem(),transferenciaDes.getIbanDestinatario())) {

            Message<String> message = MessageBuilder
                    .withPayload(transferencia)
                    .setHeader(KafkaHeaders.TOPIC, "tr-interbancaria-wbkb-emis")
                    .build();

            kafkaTemplate.send(message);
        }

    }

    public  void sendClientePojoMiniOfHistoricoDebitoKuzolaBank(String clienteJson) throws JsonProcessingException {

       ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        ClientePojoMini clientePojoMini = objectMapper.readValue(clienteJson, ClientePojoMini.class);

        System.out.println("ClienteJson: "+ clienteJson);
        System.out.println("ClientePOJOMini: "+ clientePojoMini);

            System.out.println("Entrei no Kuzola Bank");
            Message<String> message = MessageBuilder
                    .withPayload(clienteJson)
                    .setHeader(KafkaHeaders.TOPIC, "historico-debito-kb-emis")
                    .build();

            kafkaTemplate.send(message);



    }

    public  void sendClientePojoMiniOfHistoricoDebitoWakandaBank(String clienteJson) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        ClientePojoMini clientePojoMini = objectMapper.readValue(clienteJson, ClientePojoMini.class);

        System.out.println("ClienteJson: "+ clienteJson);
        System.out.println("ClientePOJOMini: "+ clientePojoMini);

            Message<String> message = MessageBuilder
                    .withPayload(clienteJson)
                    .setHeader(KafkaHeaders.TOPIC, "historico-debito-wb-emis")
                    .build();

            kafkaTemplate.send(message);


    }

    public  void sendClientePojoMiniOfHistoricoCreditoKuzolaBak(String clienteJson) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        ClientePojoMini clientePojoMini = objectMapper.readValue(clienteJson, ClientePojoMini.class);

        System.out.println("ClienteJson: "+ clienteJson);
        System.out.println("ClientePOJOMini: "+ clientePojoMini);

        if(transferenciaServiceImpl.isKuzolaBankIban(clientePojoMini.getIban()))
        {
            System.out.println("Entrei no Kuzola Bank");
            Message<String> message = MessageBuilder
                    .withPayload(clienteJson)
                    .setHeader(KafkaHeaders.TOPIC, "historico-credito-kb-emis")
                    .build();

            kafkaTemplate.send(message);

        }
        else if(transferenciaServiceImpl.isWakandaBankIban(clientePojoMini.getIban()))
        {
            Message<String> message = MessageBuilder
                    .withPayload(clienteJson)
                    .setHeader(KafkaHeaders.TOPIC, "historico-credito-wb-emis")
                    .build();

            kafkaTemplate.send(message);
        }


    }


    public void sendMessageTransferenciaResponse(String data)
    {
        //data = t(kafkaConsumerConfig.getTransferenciaPOJO());
        LOGGER.info(String.format("Message sent ==> %s ", data.toString()));
        Message<String> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, "response")
                .build();

        kafkaTemplate.send(message);
    }

    public void sendMessageTransferenciaResponse2(String data)
    {
        //data = t(kafkaConsumerConfig.getTransferenciaPOJO());
        System.out.println("PASSOU AQUI");
        LOGGER.info(String.format("Message sent response2 ==> %s ", data.toString()));
        Message<String> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, "response2")
                .build();

        kafkaTemplate.send(message);
    }

    public void sendSolicitacaoTransferenciaKuzola(String data)
    {
        System.out.println("sendSolicitacaoTransferenciaKuzola AQUI");
        LOGGER.info(String.format("intermediarioTransferToKuzola ==> %s ", data.toString()));
        Message<String> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, "intermediarioTransferToKuzola")
                .build();

        kafkaTemplate.send(message);
    }

    public void sendSolicitacaoTransferenciaWakanda(String data) {
    }
}
