package ucan.edu.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic Topic1(){
        return TopicBuilder
                .name("bancowakanda")
                .partitions(5)
                .build();
    }

    @Bean
    public NewTopic Topic2(){
        return TopicBuilder
                .name("transferencia-wakanda")
                .partitions(5)
                .build();
    }

    @Bean
    public NewTopic Topic3(){
        return TopicBuilder
                .name("tr-intrabancarias-wd")
                .partitions(5)
                .build();
    }

    @Bean
    public NewTopic responseWakanda(){
        return TopicBuilder
                .name("responseWakanda")
                .partitions(5)
                .build();
    }

    @Bean
    public NewTopic wakandaClienteInfo()
    {
        return TopicBuilder
                .name("clienteWakanda")
                .partitions(5)
                .build();
    }
}
