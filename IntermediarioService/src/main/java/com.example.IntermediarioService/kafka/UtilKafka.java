package com.example.IntermediarioService.kafka;

import org.springframework.beans.factory.annotation.Autowired;

public class UtilKafka {
    @Autowired
    private static KafkaTransferenciaProducer kafkaTransferenciaProducer;

    public static void sendDataToKuzolaEmis(String data)
    {
        kafkaTransferenciaProducer.sendSolicitacaoTransferenciaKuzola(data);
    }
}
