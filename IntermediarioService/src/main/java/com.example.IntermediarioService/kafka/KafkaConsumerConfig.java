package com.example.IntermediarioService.kafka;

import com.example.IntermediarioService.authoriazation.Authorizarization;
import com.example.IntermediarioService.component.BancoComponent;
import com.example.IntermediarioService.component.TransferenciaComponent;
import com.example.IntermediarioService.component.TransferenciaPojoComponent;
import com.example.IntermediarioService.component.TransferenciaResponseComponent;
import com.example.IntermediarioService.entities.Cliente;
import com.example.IntermediarioService.services.implementacao.AuthService;
import com.example.IntermediarioService.services.implementacao.ClienteServiceImpl;
import com.example.IntermediarioService.services.implementacao.TransferenciaServiceImpl;
import com.example.IntermediarioService.utils.pojos.*;
import com.example.IntermediarioService.utils.pojos.jsonUtils.CustomJsonPojos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class KafkaConsumerConfig
{
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerConfig.class);
    private TransferenciaPOJO transferenciaPOJO;
    @Autowired
    private ClienteServiceImpl clienteImpl;
    @Autowired
    private AuthService authService;
    @Autowired
    private TransferenciaPojoComponent transferenciaPOJOComponent;
    @Autowired
    TransferenciaComponent transferenciaComponent;
    @Autowired
    private BancoComponent bancoComponent;
    @Autowired
    private TransferenciaResponseComponent transferenciaResponseComponent;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    TransferenciaServiceImpl transferenciaServiceImpl;

    @Autowired
    TransferenciaHistoricoDebitoComponent transferenciaHistoricoDebitoComponent;

    @Autowired
    TransferenciaHistoricoCreditoComponent TransferenciaHistoricoCreditoComponent;

    @Autowired
    SaldoResponseComponent saldoResponseComponent;

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
        System.out.println("iBanOrigem: " + obj.getIbanOrigem());
        transferenciaPOJO = obj;

        Map<String,Integer> identificadorBanco = new HashMap<>();
        identificadorBanco.put("UUID",obj.getBancoUdentifier());
        bancoComponent.setBancoComponent(identificadorBanco);
        System.out.println("Data: " +transferenciaPOJO.getDatahora()+ "Data: " +obj.getDatahora());
        CustomJsonPojos.saveTransferComponent(transferenciaPOJO, transferenciaPOJOComponent);

        HttpEntity entity = Authorizarization.createBody();
        HttpEntity entityResponse = restTemplate.exchange("http://localhost:8082/transferencia/publishTransferencia", HttpMethod.POST,entity, String.class);

        System.out.println(":entityResponse: "+entityResponse);
    }

    @KafkaListener(topics = "transfer-kuzolabank", groupId = "kuzolaGroup")
    public void consumerMessageKuzola(String message)
    {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        RestTemplate restTemplate1 = new RestTemplate();

        LOGGER.info(String.format("Message received - Response: -> %s", message.toString()));

        TransferenciaResponse response = gson.fromJson(message.toString(), TransferenciaResponse.class);
        CustomJsonPojos.saveTransferResponseComponent(response, transferenciaResponseComponent);
        System.out.println(" --------------- TRANSFERENCIAS RESPONSE ----------------------");
        System.out.println("Descricao " + response.getDescricao());
        System.out.println("Status " + response.getStatus());

        HttpEntity entity = Authorizarization.createBody();
        HttpEntity entityResponse = restTemplate.exchange("http://localhost:8082/transferencia/response", HttpMethod.POST,entity, String.class);

        System.out.println("Response transfer-kuzolabank:"+entityResponse);
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
        CustomJsonPojos.saveTransferResponseComponent(response, transferenciaResponseComponent);
        System.out.println(" --------------- TRANSFERENCIAS RESPONSE ----------------------");
        System.out.println("Descricao " + response.getDescricao());
        System.out.println("Status " + response.getStatus());

        HttpEntity entity = Authorizarization.createBody();
        HttpEntity entityResponse = restTemplate.exchange("http://localhost:8082/transferencia/responseTokuzola", HttpMethod.POST,entity, String.class);

        System.out.println("DELCIA:"+entityResponse);
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
        CustomJsonPojos.saveTransferComponent(transferenciaPOJO, transferenciaPOJOComponent);

        HttpEntity entity = Authorizarization.createBody();
        HttpEntity entityResponse = restTemplate.exchange("http://localhost:8082/transferencia/publishTransferencia", HttpMethod.POST,entity, String.class);

        System.out.println(" entityResponse:" +entityResponse);
    }

    @KafkaListener(topics ={"tr-intrabancarias-kb-emis", "resposta-tr-intrabancarias-kb-emis"}, groupId = "emisGroup")
    public void consumeMessageOfTransferenciaIntrabancaria(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        TransferenciaPOJOEmis transferenciaPOJOEmis = objectMapper.readValue(message, TransferenciaPOJOEmis.class);

        LOGGER.info(String.format("Message received in emis -> %s", message.toString()));
        System.out.println("Mensagem Recebida TransferenciaPOJOEmis: "+ transferenciaPOJOEmis.toString());
        System.out.println("Converting into Transferencia"+ transferenciaServiceImpl.convertingIntoTransferencia(transferenciaPOJOEmis).toString());

        transferenciaServiceImpl.salvarTransferencia(transferenciaServiceImpl.convertingIntoTransferencia(transferenciaPOJOEmis));
    }

    @KafkaListener(topics = "clienteWakanda", groupId = "myGroup")
    public  void consumerWankadaClienteInfo(String message)
    {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();
        LOGGER.info(String.format("Message received -> %s", message.toString()));
        ClientePOJO obj = gson.fromJson(message.toString(), ClientePOJO.class);

        Cliente cliente = clienteImpl.saveCliente(obj);
        authService.signUp(obj, cliente);
    }

    @KafkaListener(topics = "clienteKuzola", groupId = "kuzolaGroup")
    public  void consumerKuzolaClienteInfo(String message)
    {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();
        LOGGER.info(String.format("Message received from kuzola -> %s", message.toString()));
        ClientePOJO obj = gson.fromJson(message.toString(), ClientePOJO.class);

        Cliente cliente = clienteImpl.saveCliente(obj);
        authService.signUp(obj, cliente);
    }

    @KafkaListener(topics = "wakandaResponseToIntermdiario", groupId = "myGroup")
    public void consumerResponseFromWakandaTransferEmis(String message)
    {

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        LOGGER.info(String.format("Message received - Response: -> %s", message.toString()));

        TransferenciaResponse response = gson.fromJson(message.toString(), TransferenciaResponse.class);
        CustomJsonPojos.saveTransferResponseComponent(response, transferenciaResponseComponent);

        if(response.getStatus())
        {
            String data = CustomJsonPojos.convertTransfereciComponentString(transferenciaComponent);
            System.out.println("transferenciaComponent: " +transferenciaComponent.getTransferencia().values());
            System.out.println(data);

            HttpEntity entity = Authorizarization.createBody();
            HttpEntity entityResponse = restTemplate.exchange("http://localhost:8082/transferencia/sendSolicitacaoTransferenciaKuzola", HttpMethod.POST,entity, String.class);

            System.out.println(data);
        }
        else
        {
            System.out.println();
        }
        LOGGER.info(String.format("Message received from wakandaResponseToIntermdiario -> %s", message.toString()));
    }


    public TransferenciaPOJO getTransferenciaPOJO()
    {
        return transferenciaPOJO;
    }

    @KafkaListener(topics = "resposta-historico-debito-kb-emis", groupId = "emisGroup")
    public void consumeMessageTransferenciaEmisDebito(String message) throws JsonProcessingException {

        System.out.println("Entrei resposta EMis");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        TransferenciaPOJOEmis[] st = objectMapper.readValue(message, TransferenciaPOJOEmis[].class);
        List<TransferenciaPOJOEmis> lista = new ArrayList<>(List.of(st));

        transferenciaHistoricoDebitoComponent.setTransferenciaResponseHistoricoList(TransferenciaResponseHistoricoDebito.convertingIntoListTransferenciaHistorico(List.of(st)));

        System.out.println("Lista Resposta Emis: "+ Arrays.toString(st));
        System.out.println("Lista Resposta Emis lista: "+ lista.toString());

    }

    @KafkaListener(topics = "resposta-historico-credito-kb-emis", groupId = "emisGroup")
    public void consumeMessageTransferenciaEmisCredito(String message) throws JsonProcessingException {

        System.out.println("Entrei resposta EMis");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        TransferenciaPOJOEmis[] st = objectMapper.readValue(message, TransferenciaPOJOEmis[].class);
        List<TransferenciaPOJOEmis> lista = new ArrayList<>(List.of(st));

        TransferenciaHistoricoCreditoComponent.setTransferenciaResponseHistoricoList(TransferenciaResponseHistoricoCredito.convertingIntoListTransferenciaHistorico(List.of(st)));

        System.out.println("Lista Credito Resposta Emis: "+ Arrays.toString(st));
        System.out.println("Lista Credito Resposta Emis lista: "+ lista.toString());

    }

    @KafkaListener(topics = "resposta-historico-debito-wb-emis", groupId = "emisGroup")
    public void consumeMessageTransferenciaEmisDebitoWakanda(String message) throws JsonProcessingException {

        System.out.println("Entrei resposta EMis");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        TransferenciaPOJOEmis[] st = objectMapper.readValue(message, TransferenciaPOJOEmis[].class);
        List<TransferenciaPOJOEmis> lista = new ArrayList<>(List.of(st));

        TransferenciaHistoricoCreditoComponent.setTransferenciaResponseHistoricoList(TransferenciaResponseHistoricoCredito.convertingIntoListTransferenciaHistorico(List.of(st)));

        System.out.println("Lista Debito Resposta Emis: "+ Arrays.toString(st));
        System.out.println("Lista Debito Resposta Emis lista: "+ lista.toString());

    }

    @KafkaListener(topics = "resposta-historico-credito-wb-emis", groupId = "emisGroup")
    public void consumeMessageTransferenciaEmisCreditoWakanda(String message) throws JsonProcessingException {

        System.out.println("Entrei resposta EMis");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        TransferenciaPOJOEmis[] st = objectMapper.readValue(message, TransferenciaPOJOEmis[].class);
        List<TransferenciaPOJOEmis> lista = new ArrayList<>(List.of(st));

        TransferenciaHistoricoCreditoComponent.setTransferenciaResponseHistoricoList(TransferenciaResponseHistoricoCredito.convertingIntoListTransferenciaHistorico(List.of(st)));

        System.out.println("Lista Debito Resposta Emis: "+ Arrays.toString(st));
        System.out.println("Lista Debito Resposta Emis lista: "+ lista.toString());

    }

    @KafkaListener(topics = "resposta-info-saldo-kb-emis", groupId = "emisGroup")
    public void consumeMessageRespostaInfoSaldoKuzolaBank(String message) throws JsonProcessingException {

        System.out.println("Entrei resposta EMis");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        SaldoResponse st = objectMapper.readValue(message, SaldoResponse.class);

        saldoResponseComponent.setSaldoResponse(st);
        System.out.println("Saldo Intermediario info "+ st.toString());

    }

    @KafkaListener(topics = "resposta-info-saldo-wb-emis", groupId = "emisGroup")
    public void consumeMessageRespostaInfoSaldoWakandaBank(String message) throws JsonProcessingException {

        System.out.println("Entrei resposta EMis");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        SaldoResponse st = objectMapper.readValue(message, SaldoResponse.class);

        saldoResponseComponent.setSaldoResponse(st);

        System.out.println("Saldo Intermediario info "+ st.toString());

    }


}
