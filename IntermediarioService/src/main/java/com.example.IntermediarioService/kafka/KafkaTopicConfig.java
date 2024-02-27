/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.IntermediarioService.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 *
 * @author jussyleitecode
 */
@Configuration
public class KafkaTopicConfig
{

    @Bean
    public NewTopic intermediario()
    {
        return TopicBuilder.name("transferencia")
                .partitions(5)
                .build();
    }

    @Bean
    public NewTopic intermediarioResponse()
    {
        return TopicBuilder.name("response")
                .partitions(5)
                .build();
    }
    @Bean
    public NewTopic intermediarioResponse2()
    {
        return TopicBuilder.name("response2")
                .partitions(5)
                .build();
    }



    @Bean
    public NewTopic intermediario2()
    {
        return TopicBuilder.name("transferencia2")
                .partitions(5)
                .build();
    }

    @Bean
    public NewTopic transferenciaIntrabancariaKuzolaBank()
    {
        return TopicBuilder
                .name("transf-intrabancarias-kb-emis")
                .partitions(3)
                .build();
    }

    @Bean
    public NewTopic transferenciaIntrabancariaWakandaBank()
    {
        return TopicBuilder
                .name("transf-intrabancarias-wb-emis")
                .partitions(3)
                .build();
    }

    @Bean
    public NewTopic transferenciaIntrabancariaWakandaKuzolaBank()
    {
        return TopicBuilder
                .name("tr-interbancaria-wbkb-emis")
                .partitions(3)
                .build();
    }

    @Bean
    public NewTopic transferenciaIntrabancariaWakandaKuzolaBankEmis()
    {
        return TopicBuilder
                .name("transferenciaEmis2")
                .partitions(3)
                .build();
    }

    @Bean
    public NewTopic intermediarioTransferToKuzolaEmis()
    {
        return TopicBuilder
                .name("intermediarioTransferToKuzola")
                .partitions(3)
                .build();
    }




    @Bean
    public NewTopic historicoDeCreditoKuzolaBank()
    {
        return TopicBuilder
                .name("historico-credito-kb-emis")
                .partitions(3)
                .build();
    }


    @Bean
    public NewTopic historicoDeDebitoKuzolaBank()
    {
        return TopicBuilder
                .name("historico-debito-kb-emis")
                .partitions(3)
                .build();
    }

    @Bean
    public NewTopic historicoDeCreditoWakandaBank()
    {
        return TopicBuilder
                .name("historico-credito-wb-emis")
                .partitions(3)
                .build();
    }


    @Bean
    public NewTopic historicoDeDebitoWakandaBank()
    {
        return TopicBuilder
                .name("historico-debito-wb-emis")
                .partitions(3)
                .build();
    }




}
