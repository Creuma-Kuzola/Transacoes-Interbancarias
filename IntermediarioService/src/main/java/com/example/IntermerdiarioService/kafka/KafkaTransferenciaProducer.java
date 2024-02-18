/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.IntermerdiarioService.kafka;

import com.example.IntermerdiarioService.utils.pojos.TransferenciaPOJO;
import java.text.SimpleDateFormat;

import com.example.IntermerdiarioService.utils.pojos.jsonUtils.CustomJsonPojos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
                + "}";

        return str;
    }

    public void sendMessage(String data)
    {
        data = CustomJsonPojos.criarStrToJson(kafkaConsumerConfig.getTransferenciaPOJO());
        LOGGER.info(String.format("Message sent ==> %s ", data.toString()));
        bankUnikeIdentifiedNumber = 4040;
        Message<String> message = null;
        if (bankUnikeIdentifiedNumber == 4040)
        {
            message= MessageBuilder
                    .withPayload(data)
                    .setHeader(KafkaHeaders.TOPIC, "transferencia2")
                    .build();
        }
        else if (bankUnikeIdentifiedNumber == 2930)
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
