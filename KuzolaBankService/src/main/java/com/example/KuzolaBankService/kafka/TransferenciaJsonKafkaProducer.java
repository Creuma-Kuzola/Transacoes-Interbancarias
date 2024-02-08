package com.example.KuzolaBankService.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class TransferenciaJsonKafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferenciaJsonKafkaProducer.class);
    private KafkaTemplate <String, String> kafkaTemplate;

    public TransferenciaJsonKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String transferencia){

        LOGGER.info(String.format("Message sent -> %s", transferencia.toString()));

        Message<String> message = MessageBuilder
                .withPayload(transferencia)
                .setHeader(KafkaHeaders.TOPIC, "transfer-kuzolabank")
                .build();
        kafkaTemplate.send(message);
    }
}
