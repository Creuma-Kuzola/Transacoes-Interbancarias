package com.example.IntermediarioService.kafka;

import com.example.IntermediarioService.component.BancoComponent;
import com.example.IntermediarioService.component.TransferenciaResponseComponent;
import com.example.IntermediarioService.utils.pojos.TransferenciaPOJO;
import com.example.IntermediarioService.utils.pojos.TransferenciaResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaConsumerConfig
{

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerConfig.class);
    private TransferenciaPOJO transferenciaPOJO;

    @Autowired
    private BancoComponent bancoComponent;

    @Autowired
    private TransferenciaResponseComponent transferenciaResponseComponent;
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
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        Gson gson = builder.create();

        LOGGER.info(String.format("Message received -> %s", message.toString()));

        TransferenciaPOJO obj = gson.fromJson(message.toString(), TransferenciaPOJO.class);
        System.out.println("Descricao " + obj.getDescricao());
        transferenciaPOJO = obj;

        System.out.println("Data: " +transferenciaPOJO.getDatahora()+ "Data: " +obj.getDatahora());
        //String response = restTemplate.postForObject("http://localhost:8082/transferencia/publishTransferencia",transferenciaPOJO, String.class);
        //System.out.println("Resposta: -> to another bank kusola:-> " +response);
    }

    @KafkaListener(topics = "transfer-kuzolabank", groupId = "kuzolaGroup")
    public void consumerMessageKuzola(String message)
    {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        //jussy leite
        Gson gson = builder.create();
        RestTemplate restTemplate1 = new RestTemplate();

        LOGGER.info(String.format("Message received - Response: -> %s", message.toString()));

        TransferenciaResponse response = gson.fromJson(message.toString(), TransferenciaResponse.class);
        System.out.println(" --------------- TRANSFERENCIAS RESPONSE ----------------------");
        System.out.println("Descricao " + response.getDescricao());
        System.out.println("Status " + response.getStatus());

        String strResponse = restTemplate1.postForObject("http://localhost:8082/transferencia/response",response, String.class);
        System.out.println("Resposta: -> to another bank wakanda:-> " +strResponse);
    }

    @KafkaListener(topics = "responseWakanda", groupId = "myGroup")
    public void consumerWakandaResponse(String message)
    {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        RestTemplate restTemplate1 = new RestTemplate();

        LOGGER.info(String.format("Message received - Response: -> %s", message.toString()));

        TransferenciaResponse response = gson.fromJson(message.toString(), TransferenciaResponse.class);
        System.out.println(" --------------- TRANSFERENCIAS RESPONSE ----------------------");
        System.out.println("Descricao " + response.getDescricao());
        System.out.println("Status " + response.getStatus());

        String strResponse = restTemplate1.postForObject("http://localhost:8082/transferencia/responseTokuzola",response, String.class);
        System.out.println("DELCIA " +strResponse);
    }

   @KafkaListener(topics = "intra-transfer-kuzola", groupId = "kuzolaGroup")
    public void consumerMessageTransferIntraBakKuzola(String message)
    {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        Gson gson = builder.create();
        LOGGER.info(String.format("Message received -> %s", message.toString()));
        TransferenciaPOJO obj = gson.fromJson(message.toString(), TransferenciaPOJO.class);
        System.out.println("Descricao " + obj.getDescricao());
        transferenciaPOJO = obj;

        Map<String,Integer> identificadorBanco = new HashMap<>();

        identificadorBanco.put("UUID",obj.getBancoUdentifier());
        bancoComponent.setBancoComponent(identificadorBanco);

       System.out.println("Data: " +transferenciaPOJO.getDatahora()+ "Data: " +obj.getDatahora());
       String response = restTemplate.postForObject("http://localhost:8082/transferencia/publishTransferencia",transferenciaPOJO, String.class);
       System.out.println("Resposta: -> to another bank kusola:-> " +response);
    }

    @KafkaListener(topics = "tr-intrabancarias-kb-emis", groupId = "emisGroup")
    public void consumeMessageOfTransferenciaIntrabancaria(String message)
    {
        LOGGER.info(String.format("Message received in emis -> %s", message.toString()));
        System.out.println("Mensagem Recebida");
    }


    public TransferenciaPOJO getTransferenciaPOJO()
    {
        return transferenciaPOJO;
    }

}
