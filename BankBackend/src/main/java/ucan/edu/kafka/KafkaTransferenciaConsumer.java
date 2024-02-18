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
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ucan.edu.component.TransferenciaMessage;
import ucan.edu.config.component.TransferenciaComponent;
import ucan.edu.entities.ContaBancaria;
import ucan.edu.entities.Transferencia;
import ucan.edu.services.implementacao.ContaBancariaServiceImpl;
import ucan.edu.services.implementacao.TransferenciaServiceImpl;
import ucan.edu.utils.pojos.TransferenciaPOJO;
import ucan.edu.utils.pojos.TransferenciaResponse;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
    ContaBancariaServiceImpl contaBancariServiceImpl;
    @Autowired
    private TransferenciaMessage transferenciaMessage;
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTransferenciaConsumer.class);
    public KafkaTransferenciaConsumer(TransferenciaServiceImpl transferenciaServiceImpl)
    {
        this.transferenciaServiceImpl = transferenciaServiceImpl;
    }
    public void readTransferenciaFrom()
    {
        /*
        1- Ler o topic
        2- Verficar se a chave do topic recebida == ao numero do banco
        3- se topic.key == banknumber  
                - Persistir as informacoes do object transferancia serialized na banco de dados do banco destino
           se nao
                - não persistir
        */
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
             /*messageT.put("message","Transferência efectuada com sucesso!");
             messageT.put("status","true");
             transferenciaMessage.setMessage(messageT); */

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
        transferencia.setEstadoTransferencia("REALIZADO");
        transferencia.setCodigoTransferencia(transferenciaComponent.getTransferenciaResponse().get("codigoTransferencia"));
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
