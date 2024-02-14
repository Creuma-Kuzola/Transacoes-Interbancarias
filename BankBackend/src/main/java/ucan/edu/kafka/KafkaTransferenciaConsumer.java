/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.kafka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ucan.edu.services.implementacao.TransferenciaServiceImpl;

/**
 *
 * @author jussyleitecode
 */
@Service
public class KafkaTransferenciaConsumer
{
    private final TransferenciaServiceImpl transferenciaServiceImpl;
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
    public void consumerMessageResponse(String message)
    {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        //jussy leite
        Gson gson = builder.create();
        LOGGER.info(String.format("Message received response transferencia status from transferencia topic-> %s", message.toString()));
    }
}
