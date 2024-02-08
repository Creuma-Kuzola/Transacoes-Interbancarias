/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ucan.edu.utils.pojos.TransferenciaPOJO;
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

    private KafkaTemplate<String, TransferenciaPOJO> kafkaTemplate;

    public KafkaTransferenciaProducer(KafkaTemplate<String, TransferenciaPOJO> kafkaTemplate)
    {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(TransferenciaPOJO data)
    {
        LOGGER.info(String.format("Message sent ==> %s ", data.toString()));
        Message<TransferenciaPOJO> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, "bancowakanda")
                .build();

        kafkaTemplate.send(message);
    }
}
