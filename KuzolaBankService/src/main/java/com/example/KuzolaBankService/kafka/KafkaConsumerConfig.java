package com.example.KuzolaBankService.kafka;

import com.example.KuzolaBankService.config.component.TransferenciaComponent;
import com.example.KuzolaBankService.config.component.TransferenciaMessage;
import com.example.KuzolaBankService.dto.SignInDto;
import com.example.KuzolaBankService.entities.ContaBancaria;
import com.example.KuzolaBankService.entities.Transferencia;
import com.example.KuzolaBankService.services.implementacao.ContaBancariaServiceImpl;
import com.example.KuzolaBankService.services.implementacao.TransferenciaServiceImpl;
import com.example.KuzolaBankService.utils.pojos.TransferenciaCustomPOJO;
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class KafkaConsumerConfig
{
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerConfig.class);
    private TransferenciaPOJO transferenciaPOJO;
    private TransferenciaCustomPOJO transferenciaCustomPOJO;
    private RestTemplate restTemplate;
    @Autowired
    private ContaBancariaServiceImpl contaBancariServiceImpl;

    @Autowired
    TransferenciaMessage transferenciaMessage;
    @Autowired
    TransferenciaResponseComponent transferenciaResponseComponent;

    @Autowired
    TransferenciaComponent transferenciaComponent;

    @Autowired
    Gson gson;

    @Autowired
    private TransferenciaServiceImpl transferenciaServiceImpl;

    @Autowired
    TransferenciaJsonKafkaProducer transferenciaJsonKafkaProducer;

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
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        Gson gson = builder.create();
        LOGGER.info(String.format("Message received -> %s", message.toString()));
        TransferenciaCustomPOJO obj = gson.fromJson(message.toString(), TransferenciaCustomPOJO.class);
        System.out.println("Descricao " + obj.getDescricao());
        transferenciaCustomPOJO = obj;

        //verify the iban and account status
       boolean isValidIban = contaBancariServiceImpl.existsIban(obj.getIbanDestinatario());
       ContaBancaria isActiva = contaBancariServiceImpl.isAccountStatus(obj.getIbanDestinatario(), "Activo");

       TransferenciaResponse transferenciaResponse = new TransferenciaResponse();
       if (isValidIban && isActiva != null)
       {
           transferenciaResponse.setDescricao("Conta disponivel");
           transferenciaResponse.setStatus(true);
           contaBancariServiceImpl.credito(transferenciaCustomPOJO.getIbanDestinatario(),transferenciaCustomPOJO.getMontante());
           sendResposta(transferenciaResponse);
       }
       else
       {
           transferenciaResponse.setDescricao("Conta Indisponivel");
           transferenciaResponse.setStatus(false);
           sendResposta(transferenciaResponse);
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

    @KafkaListener(topics ="tr-intrabancarias-kb")
    public void consumeMessageTransferenciasIntrabancarias(String message)  {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            TransferenciaPOJO transferenciaPOJO = objectMapper.readValue(message, TransferenciaPOJO.class);

            Optional<ContaBancaria> contaBancaria = contaBancariServiceImpl.findById(transferenciaPOJO.getFkContaBancariaOrigem());
            contaBancariServiceImpl.debito(contaBancaria.get().getIban(), transferenciaPOJO.getMontante());
            contaBancariServiceImpl.credito(transferenciaPOJO.getIbanDestinatario(), transferenciaPOJO.getMontante());

            LOGGER.info(String.format(" Transferencia efectuada com sucesso ", message.toString()));

            System.out.println("\nTransferencia efectuada com sucesso: "+
                    "\n"+ "Data-Hora: "+ transferenciaPOJO.getDatahora()+ "\n"+
                    "Montante (Kz): "+ transferenciaPOJO.getMontante()+ "\n"+
                    "Estado: "+ transferenciaPOJO.getEstadoTransferencia()+ "\n"+
                    "Iban do Destinatario: "+ transferenciaPOJO.getIbanDestinatario()
            );

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @KafkaListener(topics = "response2")
    public void consumerMessageResponse2(String message) throws ParseException {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        TransferenciaResponse transferenciaResponse = gson.fromJson(message.toString(), TransferenciaResponse.class);
        Map<String, String> messageT = new HashMap<>();

        if(transferenciaResponse.getStatus() == true)
        {

            messageT.put("message","Transferência efectuada com sucesso!");
            messageT.put("status","true");
            transferenciaMessage.setMessage(messageT);

            System.out.println(transferenciaMessage.getMessage().get("message"));
            BigInteger numeroDeConta  = new BigInteger(transferenciaComponent.getTransferenciaResponse().get("fkContaBancariaOrigem"));
            BigDecimal montante = new BigDecimal(transferenciaComponent.getTransferenciaResponse().get("montante"));
            ContaBancaria contaBancaria = contaBancariServiceImpl.debito(numeroDeConta,montante);

            if (contaBancaria != null)
            {
                messageT.put("message","Transferência efectuada com sucesso!");
                messageT.put("status","true");
                transferenciaMessage.setMessage(messageT);
              //  Transferencia transferencia = transferenciaServiceImpl.buildTransferencia(transferenciaComponent);
                //Transferencia transferenciaSaved =  transferenciaServiceImpl.criaTransferencia(transferencia);

                //transferenciaServiceImpl.builderTransferenciaToTrasnferenciaComponent(transferenciaSaved,transferenciaComponent);
                System.out.println("Debito feito com sucesso!");
            }
        }
        else
        {
            messageT.put("message","Transferência não concluída com sucesso!");
            messageT.put("status","false");
            transferenciaMessage.setMessage(messageT);

            System.out.println(" Não é possivel completar a operação!");
            System.out.println(transferenciaMessage.getMessage().get("message"));;
        }
        LOGGER.info(String.format("Message received response transferencia status from transferencia topic-> %s", message.toString()));
    }



}
