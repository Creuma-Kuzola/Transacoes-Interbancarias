package com.example.KuzolaBankService.kafka;


import com.example.KuzolaBankService.entities.ContaBancaria;
import com.example.KuzolaBankService.services.implementacao.ContaBancariaServiceImpl;
import com.example.KuzolaBankService.utils.pojos.TransferenciaPOJO;
import com.example.KuzolaBankService.utils.pojos.TransferenciaResponse;
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

    @KafkaListener(topics = "transferencia", groupId = "myGroup")
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
           String availble =  restTemplate.postForObject("http://localhost:8081//transferencia/response", transferenciaResponse,String.class);
           System.out.println("Response: "+availble);

           
       }
       else
       {
           boolean availble =  restTemplate.postForObject("",true,Boolean.class);
           //System.out.println("Account unavalaible to receive transfer money!");
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

        Map<String, String > response = new HashMap<>();
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


    @KafkaListener(topics = "tr-intrabancarias-kuzolabank", groupId = "consumerBanco")
    public void consumeMessageTransferenciasIntrabancarias(String message)  {

        String messageReceived = message;

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        gson = builder.create();
        LOGGER.info(String.format("Message received -> %s", message.toString()));

        TransferenciaPOJO obj = gson.fromJson(message, TransferenciaPOJO.class);

        System.out.println("Descricao " + obj.getDescricao());
        transferenciaPOJO = obj;

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
        LOGGER.info(String.format("Message received -> %s", message.toString()));
    }

}
