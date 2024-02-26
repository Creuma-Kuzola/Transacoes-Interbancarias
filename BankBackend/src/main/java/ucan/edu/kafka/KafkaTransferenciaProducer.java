/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ucan.edu.config.component.TransferenciaComponent;
import ucan.edu.utils.pojos.TransferenciaPOJO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jussyleitecode
 */
@Service
public class KafkaTransferenciaProducer
{
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTransferenciaProducer.class);
    private KafkaTemplate<String, String> kafkaTemplate;
    private TransferenciaComponent transferenciaComponent;
    public KafkaTransferenciaProducer(KafkaTemplate<String, String> kafkaTemplate)
    {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String data)
    {
        LOGGER.info(String.format("Message sent ==> %s ", data.toString()));
        Message<String> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, "bancowakanda")
                .build();
        kafkaTemplate.send(message);
    }

    public void sendTransferenciaIntrabancaria(String transferencia){
        LOGGER.info(String.format("Message sent -> %s", transferencia.toString()));
        Message<String> message = MessageBuilder
                .withPayload(transferencia)
                .setHeader(KafkaHeaders.TOPIC, "tr-intrabancarias-wd")
                .build();
        kafkaTemplate.send(message);

    }


    public void sendMessageResposta(String data)
    {
        LOGGER.info(String.format("Message sent ==> %s ", data.toString()));
        Message<String> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, "responseWakanda")
                .build();
        kafkaTemplate.send(message);
    }

    public void sendClienteInfo(String data)
    {
        LOGGER.info(String.format("info wakanda info sent ==> %s ", data.toString()));
        Message<String> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, "clienteWakanda")
                .build();
        kafkaTemplate.send(message);
    }
}
