package com.example.KuzolaBankService.kafka;

import com.example.KuzolaBankService.dto.SignInDto;
import com.example.KuzolaBankService.entities.ContaBancaria;
import com.example.KuzolaBankService.services.implementacao.ContaBancariaServiceImpl;
import com.example.KuzolaBankService.utils.pojos.TransferenciaPOJO;
import com.example.KuzolaBankService.utils.pojos.TransferenciaResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.example.KuzolaBankService.config.component.TransferenciaResponseComponent;
import com.example.KuzolaBankService.utils.jsonUtils.CustomJsonPojos;
import com.example.KuzolaBankService.dto.JwtDto;
import org.springframework.http.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

@Service
public class KafkaConsumerConfig
{
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerConfig.class);
    private TransferenciaPOJO transferenciaPOJO;
    private RestTemplate restTemplate;
    @Autowired
    private ContaBancariaServiceImpl contaBancariServiceImpl;

    //777
    private String welcome;

    @Autowired
    TransferenciaResponseComponent transferenciaResponseComponent;

    @Autowired
    Gson gson;


    public KafkaConsumerConfig()
    {
        transferenciaPOJO = new TransferenciaPOJO();
    }

    @KafkaListener(topics = "transfer-kuzolabank", groupId = "consumerBanco")
    public void consumeMessage(String message)
    {
        LOGGER.info(String.format("Message received -> %s", message.toString()));
    }

    public JwtDto createHeader(String login, String password)
    {
        RestTemplate restTemplate1 = new RestTemplate();
        SignInDto signInDto = new SignInDto(login,password);
        JwtDto token = restTemplate1.postForObject("http://localhost:8081/api/v1/auth/signin", signInDto, JwtDto.class);
        return token;
    }

    @KafkaListener(topics = "transferencia", groupId = "transferenciaGroup")
    public void consumerMessage(String message)
    {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        LOGGER.info(String.format("Message received -> %s", message.toString()));
        TransferenciaPOJO obj = gson.fromJson(message.toString(), TransferenciaPOJO.class);
        System.out.println("Descricao " + obj.getDescricao());
        transferenciaPOJO = obj;
        //verify the iban and account status
       boolean isValidIban = contaBancariServiceImpl.existsIban(obj.getIbanDestinatario());
       ContaBancaria isActiva = contaBancariServiceImpl.isAccountStatus(obj.getIbanDestinatario(), "Activo");
       if (isValidIban && isActiva != null)
       {
           TransferenciaResponse transferenciaResponse = new TransferenciaResponse();
           transferenciaResponse.setDescricao("Conta disponivel");
           transferenciaResponse.setStatus(true);
           sendResposta(transferenciaResponse);
       }
       else
       {
           //boolean availble =  restTemplate.postForObject("",true,Boolean.class);
           System.out.println("account unavaible");

       }
    }
    private void sendResposta(TransferenciaResponse transferenciaResponse) {

        RestTemplate restTemplate = new RestTemplate();
        String jsonStr =  CustomJsonPojos.TransferenciaResponse(transferenciaResponse);
        JwtDto token = createHeader("admin","admin");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
        headers.setBearerAuth(token.accessToken());
        //headers.add("body",jsonStr);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("body",jsonStr);
        HttpEntity<?> entity = new HttpEntity<>(body, headers);

        HashMap<String, String > response = new HashMap<>();
        response.put("descricao",transferenciaResponse.getDescricao());
        response.put("status",""+transferenciaResponse.getStatus());

        transferenciaResponseComponent.setTransferenciaResponse(response);

        HttpEntity entityResponse = restTemplate.exchange("http://localhost:8081/transferencia/response", HttpMethod.POST,entity, String.class);

        System.out.println("entity: headers " +entity.getHeaders());
        System.out.println("entity: body " +entity.getBody());
        System.out.println("entity: headers " +body.get("body"));


        System.out.println( "token: " +token);
        System.out.println("header:" +headers);
        System.out.println("entityResponse: "+ entityResponse.getBody());
        System.out.println("entityResponse: "+ entityResponse.getHeaders());
    }

    public TransferenciaPOJO getTransferenciaPOJO()
    {
        return transferenciaPOJO;
    }

    @KafkaListener(topics = "response", groupId = "transferenciaGroup")
    public void consumerMessageResponse(String message)
    {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        LOGGER.info(String.format("Message kuzola received response transferencia status from transferencia topic-> %s", message.toString()));
    }


    @KafkaListener(topics = "tr-intrabancarias-kb", groupId = "consumerBanco")
    public void consumeMessageTransferenciasIntrabancarias(String message)  {

        System.out.println("Message in Consumer"+ message);
        String messageReceived = message;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            TransferenciaPOJO transferenciaPOJO = objectMapper.readValue(message, TransferenciaPOJO.class);
            Optional<ContaBancaria> contaBancaria = contaBancariServiceImpl.findById(transferenciaPOJO.getFkContaBancariaOrigem());

            contaBancariServiceImpl.debito(contaBancaria.get().getIban(), transferenciaPOJO.getMontante());
            contaBancariServiceImpl.credito(transferenciaPOJO.getIbanDestinatario(), transferenciaPOJO.getMontante());
            System.out.println("ID: " + transferenciaPOJO);
            // Access other properties as needed
        } catch (Exception e) {
            e.printStackTrace();
        }

        LOGGER.info(String.format("Message received -> %s", message.toString()));

        //TransferenciaPOJO obj = gson.fromJson(message, TransferenciaPOJO.class);
       // System.out.println("Descricao " + obj.getDescricao());
       /* try {
            String fromTransfrerenciaJson = gson.toJson(message);

            TransferenciaPOJO transferenciaPOJO1 = gson.fromJson(fromTransfrerenciaJson, TransferenciaPOJO.class);
            System.out.println("TransferenciaPojo"+ transferenciaPOJO1);
            System.out.println("Message received" + messageReceived);

        }catch (IllegalStateException | JsonSyntaxException exception){

            System.out.println("Exception in conversion"+ exception);
        }
        /*GsonBuildere builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        TransferenciaPOJO obj = gson.fromJson(message.toString(), TransferenciaPOJO.class);
        System.out.println("Transferencia POJO " + obj.getDescricao());
        transferenciaPOJO = obj;*/

    }

}
