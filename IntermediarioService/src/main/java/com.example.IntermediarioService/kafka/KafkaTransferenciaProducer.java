/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.IntermediarioService.kafka;

import com.example.IntermediarioService.component.BancoComponent;
import com.example.IntermediarioService.entities.Banco;
import com.example.IntermediarioService.kafka.KafkaConsumerConfig;
import com.example.IntermediarioService.services.implementacao.BancoServiceImpl;
import com.example.IntermerdiarioService.utils.pojos.TransferenciaPOJO;
import java.text.SimpleDateFormat;
import java.util.List;

import com.example.IntermerdiarioService.utils.pojos.jsonUtils.CustomJsonPojos;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 *
 * @author jussyleitecode
 */
@Service
public class KafkaTransferenciaProducer
{

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTransferenciaProducer.class);

    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private BancoServiceImpl bancoServiceImpl;
    private  List<Banco> bancos;
    @Autowired
    private BancoComponent bancoComponent;

    private KafkaConsumerConfig kafkaConsumerConfig;

    private Integer bankUnikeIdentifiedNumber;
    public KafkaTransferenciaProducer(KafkaTemplate<String, String> kafkaTemplate, KafkaConsumerConfig kafkaConsumerConfig)
    {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaConsumerConfig = kafkaConsumerConfig;
    }

    public String criarStrToJson(TransferenciaPOJO transferenciaPOJO)
    {
        String str = "{\n"
                + "  \"pkTransferencia\": " + transferenciaPOJO.getPkTransferencia() + ",\n"
                + "   \"descricao\": \"" +  transferenciaPOJO.getDescricao() + "\",\n"
                + "    \"montante\": " + transferenciaPOJO.getMontante() + ",\n"
                + "    \"ibanDestinatario\": \"" + transferenciaPOJO.getIbanDestinatario() + "\",\n"
                + "    \"datahora\":\"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(transferenciaPOJO.getDatahora()) + "\",\n"
                + "    \"fkContaBancariaOrigem\": " + transferenciaPOJO.getFkContaBancariaOrigem() + ",\n"
                + "    \"tipoTransferencia\": \"" + transferenciaPOJO.getTipoTransferencia() + "\",\n"
                + "    \"estadoTransferencia\": \"" + transferenciaPOJO.getEstadoTransferencia() + "\",\n"
                + "    \"codigoTransferencia\": " + transferenciaPOJO.getCodigoTransferencia() + "\n"
                + "    \"bancoUdentifier\":"+transferenciaPOJO.getBancoUdentifier()+"\n"
                + "}";

        return str;
    }

    public void sendMessage(String data)
    {
        data = CustomJsonPojos.criarStrToJson(kafkaConsumerConfig.getTransferenciaPOJO());
        LOGGER.info(String.format("Message sent ==> %s ", data.toString()));

        Integer bankId = bancoComponent.geBancoComponent().get("UUID");

        Message<String> message = null;

        System.out.println(" " +(bankId == 1003) );

        if (bankId == 1003)
        {
            message= MessageBuilder
                    .withPayload(data)
                    .setHeader(KafkaHeaders.TOPIC, "transferencia2")
                    .build();
        }
        else if (bankId == 4040)
        {
            message= MessageBuilder
                    .withPayload(data)
                    .setHeader(KafkaHeaders.TOPIC, "transferencia")
                    .build();
        }
        kafkaTemplate.send(message);
    }

    public void sendMessageTransferenciaResponse(String data)
    {
        //data = t(kafkaConsumerConfig.getTransferenciaPOJO());
        LOGGER.info(String.format("Message sent ==> %s ", data.toString()));
        Message<String> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, "response")
                .build();

        kafkaTemplate.send(message);
    }
}
