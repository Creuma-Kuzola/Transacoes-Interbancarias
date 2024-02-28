/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.kafka;

import com.example.KuzolaBankService.services.implementacao.TransferenciaServiceImpl;
import com.example.KuzolaBankService.utils.pojos.ClientePojoMini;
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

    public KafkaTransferenciaProducer(KafkaTemplate<String, String> kafkaTemplate)
    {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessageResponse(String data)
    {
        LOGGER.info(String.format("Message sent ==> %s ", data.toString()));
        Message<String> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, "transfer-kuzolabank")
                .build();
        kafkaTemplate.send(message);
    }

    public void sendMessageTransferenciaIntrabancaria(String data)
    {
        LOGGER.info(String.format("Message sent ==> %s ", data.toString()));
        Message<String> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, "intra-transfer-kuzola")
                .build();
        kafkaTemplate.send(message);
    }


    public void sendMessageTransferenciaIntraBancaria(String transferencia){

        LOGGER.info(String.format("Message sent -> %s", transferencia.toString()));

        Message<String> message = MessageBuilder
                .withPayload(transferencia)
                .setHeader(KafkaHeaders.TOPIC, "tr-intrabancarias-kb")
                .build();
        kafkaTemplate.send(message);
    }

    public void sendMessageTransferenciaIntraBancariaEmis(String transferencia){

        LOGGER.info(String.format("Message sent -> %s", transferencia.toString()));

        Message<String> message = MessageBuilder
                .withPayload(transferencia)
                .setHeader(KafkaHeaders.TOPIC, "tr-intrabancarias-kb-emis")
                .build();
        kafkaTemplate.send(message);
    }

    public void sendRespostaTransferenciaIntraBancariaInEmis(String transferencia){

        LOGGER.info(String.format("Message sent -> %s", transferencia.toString()));

        Message<String> message = MessageBuilder
                .withPayload(transferencia)
                .setHeader(KafkaHeaders.TOPIC, "resposta-tr-intrabancarias-kb-emis")
                .build();
        kafkaTemplate.send(message);
    }

    public void sendClienteInfo(String data)
    {
        LOGGER.info(String.format("info kuzola info sent ==> %s ", data.toString()));
        Message<String> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, "clienteKuzola")
                .build();
        kafkaTemplate.send(message);
    }

    public  void sendRespostaOfHistoricoDebito(String clienteJson) throws JsonProcessingException {

            System.out.println("Entrei no Kuzola Bank");
            Message<String> message = MessageBuilder
                    .withPayload(clienteJson)
                    .setHeader(KafkaHeaders.TOPIC, "resposta-historico-debito-kb-emis")
                    .build();

            kafkaTemplate.send(message);

    }

    public  void sendRespostaOfHistoricoCredito(String clienteJson) throws JsonProcessingException {

        System.out.println("Entrei no Credito Kuzola Bank");
        Message<String> message = MessageBuilder
                .withPayload(clienteJson)
                .setHeader(KafkaHeaders.TOPIC, "resposta-historico-credito-kb-emis")
                .build();

        kafkaTemplate.send(message);

    }

    public  void sendRespostaOfSaldoInfo(String clienteJson) throws JsonProcessingException {

        Message<String> message = MessageBuilder
                .withPayload(clienteJson)
                .setHeader(KafkaHeaders.TOPIC, "resposta-info-saldo-kb-emis")
                .build();

        kafkaTemplate.send(message);

    }

}
