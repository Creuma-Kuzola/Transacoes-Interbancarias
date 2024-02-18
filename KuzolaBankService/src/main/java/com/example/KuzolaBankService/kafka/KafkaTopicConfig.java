package com.example.KuzolaBankService.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic Topic1(){
        return TopicBuilder
                .name("transfer-kuzolabank")
                .build();
    }

    @Bean
    public NewTopic Topic2(){
        return TopicBuilder
                .name("tr-intrabancarias-kb")
                .build();
    }

    @Bean
    public NewTopic topicTransferenciaIntrabancaria()
    {
        return TopicBuilder
                .name("intra-transfer-kuzola")
                .partitions(3)
                .build();
    }
}
