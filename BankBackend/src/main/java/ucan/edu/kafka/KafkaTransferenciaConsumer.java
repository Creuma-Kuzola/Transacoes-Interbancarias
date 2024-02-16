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
          BigDecimal montante = new BigDecimal( transferenciaComponent.getTransferenciaResponse().get("montante"));
         ContaBancaria contaBancaria = contaBancariServiceImpl.transferInterbancariaDebito(numeroDeConta,montante);



         if (contaBancaria != null)
         {
             messageT.put("message","Transferência efectuada com sucesso!");
             transferenciaMessage.setMessage(messageT);
             System.out.println("Debto feito com sucesso!");
             //Transferencia transferencia =  builderTransferencia(transferenciaComponent.getTransferenciaResponse());
             //transferenciaServiceImpl.criar(transferencia);
         }
        }
        else
        {
            messageT.put("message","Transferência efectuada com sucesso!");
            transferenciaMessage.setMessage(messageT);
            System.out.println(" Não é possivel completar a operação!");
        }
        LOGGER.info(String.format("Message received response transferencia status from transferencia topic-> %s", message.toString()));
    }

    private Transferencia builderTransferencia(Map<String, String> transferenciaResponse) throws ParseException {
        Transferencia transferencia = new Transferencia();
        //transferencia.setEstadoTransferencia("FEITA COM SUCESSO");
        transferencia.setDescricao(transferenciaResponse.get("descricao"));
        transferencia.setMontante( new BigInteger(transferenciaResponse.get("montante")));
        transferencia.setIbanDestinatario(transferenciaResponse.get("ibanDestinatario"));
        transferencia.setDatahora(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(transferenciaResponse.get("datahora")));
        transferencia.setFkContaBancariaOrigem(Integer.parseInt(transferenciaResponse.get("ibanDestinatario")));
        transferencia.setEstadoTransferencia("SUCESSO");
        transferencia.setTipoTransferencia(transferenciaResponse.get("tipoTransferencia"));
        transferencia.setCodigoTransferencia(transferenciaResponse.get("codigoTransferencia"));
        return transferencia;
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
