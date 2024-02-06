package com.example.KuzolaBankService.kafka;

import com.example.KuzolaBankService.entities.Transferencia;
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
    private KafkaTemplate <String, Object> kafkaTemplate;

    public TransferenciaJsonKafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Transferencia transferencia){

        LOGGER.info(String.format("Message received -> %s", transferencia.toString()));

        Message<Transferencia> message = MessageBuilder
                .withPayload(transferencia)
                .setHeader(KafkaHeaders.TOPIC, "transkuzolabank")
                .build();
        kafkaTemplate.send(message);
    }
}
