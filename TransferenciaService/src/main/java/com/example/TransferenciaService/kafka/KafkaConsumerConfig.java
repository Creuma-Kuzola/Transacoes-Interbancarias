package com.example.TransferenciaService.kafka;

import com.example.TransferenciaService.dto.TransferenciaDto;
import com.example.TransferenciaService.utils.pojos.TransferenciaPOJO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KafkaConsumerConfig
{

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerConfig.class);
    private TransferenciaPOJO transferenciaPOJO;
    @Autowired
    private RestTemplate restTemplate;

    public KafkaConsumerConfig()
    {
        transferenciaPOJO = new TransferenciaPOJO();
    }

    @KafkaListener(topics = "transfer-kuzolabank", groupId = "consumerBanco")
    public void consumeMessage(String message)
    {
        LOGGER.info(String.format("Message received -> %s", message.toString()));
    }
    @KafkaListener(topics = "bancowakanda", groupId = "myGroup")
    public void consumerMessage(String message)
    {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        LOGGER.info(String.format("Message received -> %s", message.toString()));
        TransferenciaPOJO obj = gson.fromJson(message.toString(), TransferenciaPOJO.class);
        System.out.println("Descricao " + obj.getDescricao());
        transferenciaPOJO = obj;

        String response = restTemplate.postForObject("http://localhost:8082/transferencia/publishTransferencia",transferenciaPOJO, String.class);
        System.out.println("Resposta: -> to another bank kusola:-> " +response);

    }

    @KafkaListener(topics = "transfer-kuzolabank", groupId = "kuzolaGroup")
    public void consumerMessageKuzola(String message)
    {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        LOGGER.info(String.format("Message received -> %s", message.toString()));
        //TransferenciaPOJO obj = gson.fromJson(message.toString(), TransferenciaPOJO.class);
        //System.out.println("Descricao " + obj.getDescricao());
        //transferenciaPOJO = obj;

        //String response = restTemplate.postForObject("http://localhost:8082/transferencia/publishTransferencia",transferenciaPOJO, String.class);
        //System.out.println("Resposta: -> to another bank kusola:-> " +response);

    }

    public TransferenciaPOJO getTransferenciaPOJO()
    {
        return transferenciaPOJO;
    }

}
