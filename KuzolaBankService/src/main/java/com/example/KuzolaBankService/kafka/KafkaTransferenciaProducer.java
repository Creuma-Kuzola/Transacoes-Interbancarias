/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
}
