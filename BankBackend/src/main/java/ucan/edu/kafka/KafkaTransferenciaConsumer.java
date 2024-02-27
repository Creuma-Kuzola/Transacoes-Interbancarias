/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ucan.edu.component.TransferenciaMessage;
import ucan.edu.config.component.TransferenciaComponent;
import ucan.edu.config.component.TransferenciaResponseComponent;
import ucan.edu.dtos.JwtDto;
import ucan.edu.dtos.SignInDto;
import ucan.edu.entities.ContaBancaria;
import ucan.edu.entities.Transferencia;
import ucan.edu.services.implementacao.ContaBancariaServiceImpl;
import ucan.edu.services.implementacao.TransferenciaServiceImpl;
import ucan.edu.utils.enums.StatusContaBancaria;
import ucan.edu.utils.jsonUtils.CustomJsonPojos;
import ucan.edu.utils.pojos.TransferenciaCustomPOJO;
import ucan.edu.utils.pojos.TransferenciaPOJO;
import ucan.edu.utils.pojos.TransferenciaResponse;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author jussyleitecode
 */
@Service
public class KafkaTransferenciaConsumer
{
    private final TransferenciaServiceImpl transferenciaServiceImpl;
    @Autowired
    private TransferenciaComponent transferenciaComponent;

    @Autowired
    private KafkaTransferenciaProducer kafkaTransferenciaProducer;
    @Autowired
    ContaBancariaServiceImpl contaBancariServiceImpl;
    private TransferenciaPOJO transferenciaPOJO;
    private TransferenciaCustomPOJO transferenciaCustomPOJO;

    // private TransferenciaCustomPOJO transferenciaCustomPOJO;
    @Autowired
    TransferenciaResponseComponent transferenciaResponseComponent;
    @Autowired
    private TransferenciaMessage transferenciaMessage;
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTransferenciaConsumer.class);
    public KafkaTransferenciaConsumer(TransferenciaServiceImpl transferenciaServiceImpl)
    {
        this.transferenciaServiceImpl = transferenciaServiceImpl;
        transferenciaPOJO = new TransferenciaPOJO();
        transferenciaCustomPOJO = new TransferenciaCustomPOJO();
    }

    @KafkaListener(topics = "transferencia2", groupId = "transferenciaGroup")
    public void consumerMessage(String message)
    {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        Gson gson = builder.create();
        LOGGER.info(String.format("Message received -> %s", message.toString()));

        TransferenciaCustomPOJO  obj = gson.fromJson(message.toString(), TransferenciaCustomPOJO.class);
        System.out.println("Descricao " + obj.getDescricao());
        transferenciaCustomPOJO = obj;

        //verify the iban and account status
        boolean isValidIban = contaBancariServiceImpl.existsIban(obj.getIbanDestinatario());
        ContaBancaria isActiva = contaBancariServiceImpl.isAccountStatus(obj.getIbanDestinatario(), StatusContaBancaria.ACTIVO);

        TransferenciaResponse transferenciaResponse = new TransferenciaResponse();
        if (isValidIban && isActiva != null)
        {
            transferenciaResponse.setDescricao("Conta disponivel");
            transferenciaResponse.setStatus(true);
            contaBancariServiceImpl.credito(obj.getIbanDestinatario(),obj.getMontante());
            Transferencia transferencia = new Transferencia();

            transferencia.setDescricao(obj.getDescricao());
            transferencia.setOperacao("RECEBIDA");
            transferencia.setCodigoTransferencia(obj.getCodigoTransferencia());
            transferencia.setEstadoTransferencia(obj.getEstadoTransferencia());
            transferencia.setFkContaBancariaOrigem(obj.getFkContaBancariaOrigem().intValue());
            transferencia.setIbanDestinatario(obj.getIbanDestinatario());
            transferencia.setDatahora(obj.getDatahora());
            transferencia.setMontante(obj.getMontante());
            transferencia.setTipoTransferencia("INTERBANCARIA");
            transferencia.setEstadoTransferencia("Realizada com sucesso");

            transferenciaServiceImpl.criaTransferencia(transferencia);
            sendResposta(transferenciaResponse);
            System.out.println("message: " +transferenciaResponse.getDescricao());
        }
        else
        {
            transferenciaResponse.setDescricao("Conta Indisponivel");
            transferenciaResponse.setStatus(false);
            sendResposta(transferenciaResponse);
        }
    }

    public JwtDto createHeader(String login, String password)
    {
        RestTemplate restTemplate1 = new RestTemplate();
        SignInDto signInDto = new SignInDto(login,password);
        JwtDto token = restTemplate1.postForObject("http://localhost:8080/api/v1/auth/signin", signInDto, JwtDto.class);
        return token;
    }

    private void sendResposta(TransferenciaResponse transferenciaResponse) {

       RestTemplate restTemplate = new RestTemplate();
       String jsonStr =  CustomJsonPojos.TransferenciaResponse(transferenciaResponse);
       JwtDto token = createHeader("admin","admin");

        System.out.println("TOKEN: " +token);

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

        System.out.println("transferenciaResponseComponent: " +transferenciaResponseComponent.getTransferenciaResponse().get("status"));

        HttpEntity entityResponse = restTemplate.exchange("http://localhost:8080/transferencia/response", HttpMethod.POST,entity, String.class);

        System.out.println("entity: headers " +entity.getHeaders());
        System.out.println("entity: body " +entity.getBody());
        System.out.println("entity: headers " +body.get("body"));


        System.out.println( "token: " +token);
        System.out.println("header:" +headers);
        System.out.println("entityResponse: "+ entityResponse.getBody());
        System.out.println("entityResponse: "+ entityResponse.getHeaders());
        System.out.println("D "+transferenciaResponse.getDescricao());
        System.out.println("STATUS "+transferenciaResponse.getStatus());
    }

    @KafkaListener(topics = "response")
    public void consumerMessageResponse(String message) throws ParseException {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        TransferenciaResponse transferenciaResponse = gson.fromJson(message.toString(), TransferenciaResponse.class);
        Map<String, String> messageT = new HashMap<>();

        if(transferenciaResponse.getStatus() == true)
        {
          Integer numeroDeConta  = Integer.parseInt(transferenciaComponent.getTransferenciaResponse().get("fkContaBancariaOrigem"));
          BigDecimal montante = new BigDecimal(transferenciaComponent.getTransferenciaResponse().get("montante"));
          ContaBancaria contaBancaria = contaBancariServiceImpl.transferInterbancariaDebito(numeroDeConta,montante);

         if (contaBancaria != null)
         {
             messageT.put("message","Transferência efectuada com sucesso!");
             messageT.put("status","true");
             transferenciaMessage.setMessage(messageT);
            
             Transferencia transferencia = buildTransferencia(transferenciaComponent);
             Transferencia transferenciaSaved =  transferenciaServiceImpl.criaTransferencia(transferencia);

             this.builderTransferenciaToTrasnferenciaComponent(transferenciaSaved,transferenciaComponent);

             System.out.println("Debto feito com sucesso!");
         }
        }
        else
        {
            messageT.put("message","Transferência não concluída com sucesso!");
            messageT.put("status","false");
            transferenciaMessage.setMessage(messageT);

            System.out.println(" Não é possivel completar a operação!");
        }
        LOGGER.info(String.format("Message received response transferencia status from transferencia topic-> %s", message.toString()));
    }
    private Transferencia buildTransferencia(TransferenciaComponent transferenciaComponent) throws ParseException
    {
        System.out.println( "transferenciaComponent.getTransferenciaResponse().get(\"datahora\"): " +transferenciaComponent.getTransferenciaResponse().get("datahora"));
        Transferencia transferencia = new Transferencia();
        transferencia.setDescricao(transferenciaComponent.getTransferenciaResponse().get("descricao"));
        transferencia.setMontante(new BigDecimal(transferenciaComponent.getTransferenciaResponse().get("montante")));
        transferencia.setDatahora(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(transferenciaComponent.getTransferenciaResponse().get("datahora")));
        transferencia.setIbanDestinatario(transferenciaComponent.getTransferenciaResponse().get("ibanDestinatario"));
        transferencia.setFkContaBancariaOrigem(Integer.parseInt(transferenciaComponent.getTransferenciaResponse().get("fkContaBancariaOrigem")));
        transferencia.setTipoTransferencia(transferenciaComponent.getTransferenciaResponse().get("tipoTransferencia"));
        transferencia.setEstadoTransferencia("Realizada com sucesso");
        transferencia.setCodigoTransferencia(transferenciaComponent.getTransferenciaResponse().get("codigoTransferencia"));
        transferencia.setIbanOrigem(transferenciaComponent.getTransferenciaResponse().get("ibanOrigem"));
        if (transferenciaComponent.getTransferenciaResponse().get("bancoUdentifier").equals("4040"))
        {
            transferencia.setOperacao("ENVIADA");
            return transferencia;
        }
        transferencia.setOperacao("RECEBIDA");
        return transferencia;
    }

    private void builderTransferenciaToTrasnferenciaComponent(Transferencia transferencia, TransferenciaComponent transferenciaComponent )
            throws ParseException {
        Map<String, String> component = new HashMap<>();
        component.put("descricao",transferencia.getDescricao());
        component.put("montante","" +transferencia.getMontante());
        component.put("ibanDestinatario",transferencia.getIbanDestinatario());
        component.put("datahora", "" +transferencia.getDatahora());
        component.put("fkContaBancariaOrigem",""+transferencia.getFkContaBancariaOrigem());
        component.put("estadoTransferencia",transferencia.getEstadoTransferencia());
        component.put("tipoTransferencia",transferencia.getTipoTransferencia());
        component.put("codigoTransferencia",transferencia.getCodigoTransferencia());
        transferenciaComponent.setTransferenciaResponse(component);
    }

    private Transferencia builderTransferencia(Map<String, String> transferenciaResponse) throws ParseException {
        Transferencia transferencia = new Transferencia();
        //transferencia.setEstadoTransferencia("FEITA COM SUCESSO");
        transferencia.setDescricao(transferenciaResponse.get("descricao"));
        transferencia.setMontante( new BigDecimal(transferenciaResponse.get("montante")));
        transferencia.setIbanDestinatario(transferenciaResponse.get("ibanDestinatario"));
        transferencia.setDatahora(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(transferenciaResponse.get("datahora")));
        transferencia.setFkContaBancariaOrigem(Integer.parseInt(transferenciaResponse.get("ibanDestinatario")));
        transferencia.setEstadoTransferencia("SUCESSO");
        transferencia.setTipoTransferencia(transferenciaResponse.get("tipoTransferencia"));
        transferencia.setCodigoTransferencia(transferenciaResponse.get("codigoTransferencia"));
        return transferencia;
    }

    @KafkaListener(topics = "tr-intrabancarias-wd", groupId = "myGroup")
    public void consumeMessageTransferenciasIntrabancarias(String message)  {
        System.out.println("Entrei");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            System.out.println("Entrei Try Catch");

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


    @KafkaListener(topics = "transferenciaEmis2", groupId = "transferenciaGroup")
    public void consumerMessageFromIntermediarioTransfer(String message)
    {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        Gson gson = builder.create();
        LOGGER.info(String.format("Message received -> %s", message.toString()));

        TransferenciaPOJO  obj = gson.fromJson(message.toString(), TransferenciaPOJO.class);
        System.out.println("Descricao " + obj.getDescricao());
        transferenciaPOJO = obj;

        CustomJsonPojos.saveTransferComponent(obj,transferenciaComponent);

        System.out.println("saved: " + transferenciaComponent.getTransferenciaResponse().values());

        boolean isValidIban = contaBancariServiceImpl.existsIban(obj.getIbanOrigem());
        ContaBancaria isActiva = contaBancariServiceImpl.isAccountStatus(obj.getIbanOrigem(), StatusContaBancaria.ACTIVO);

        TransferenciaResponse transferenciaResponse = new TransferenciaResponse();
        String data = " ";

        if (isValidIban && isActiva != null)
        {
            transferenciaResponse.setStatus(true);
            transferenciaResponse.setDescricao("Disponivel para efectuar a operação");
            data = CustomJsonPojos.TransferenciaResponse(transferenciaResponse);
        }
        else
        {
            transferenciaResponse.setStatus(false);
            transferenciaResponse.setDescricao("Conta indisponivel para efectuar operação");
            data = CustomJsonPojos.TransferenciaResponse(transferenciaResponse);
        }
        kafkaTransferenciaProducer.sendResponseToIntermediario(data);
    }




    public void consumer()
    {

    }

   /* @KafkaListener(topics = "tr-intrabancarias-kb", groupId = "consumerBanco")
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


    }*/

}
