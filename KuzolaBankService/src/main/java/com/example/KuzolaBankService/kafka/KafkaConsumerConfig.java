package com.example.KuzolaBankService.kafka;


import com.example.KuzolaBankService.entities.ContaBancaria;
import com.example.KuzolaBankService.services.implementacao.ContaBancariaServiceImpl;
import com.example.KuzolaBankService.utils.pojos.TransferenciaPOJO;
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
           System.out.println(" Account available to receive transfer money!");
       }
       else
       {
           System.out.println("Account unavalaible to receive transfer money!");
       }

    }

    public TransferenciaPOJO getTransferenciaPOJO()
    {
        return transferenciaPOJO;
    }

}
