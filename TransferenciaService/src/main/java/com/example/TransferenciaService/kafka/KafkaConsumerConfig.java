package com.example.TransferenciaService.kafka;

import com.example.TransferenciaService.dto.TransferenciaDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerConfig.class);
    @KafkaListener(topics = "transfer-kuzolabank", groupId = "consumerBanco")
    public void consumeMessage( String message){

        LOGGER.info(String.format("Message received -> %s", message.toString()));
    }

}
