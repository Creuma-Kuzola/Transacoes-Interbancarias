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
    public NewTopic intermediario2()
    {
        return TopicBuilder.name("transferencia2")
                .partitions(5)
                .build();
    }
}
