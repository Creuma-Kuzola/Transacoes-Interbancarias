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
    public NewTopic topicTransferenciaIntrabancariaEmis()
    {
        return TopicBuilder
                .name("tr-intrabancarias-kb-emis")
                .partitions(3)
                .build();
    }


    @Bean
    public NewTopic respostaHistoricoDeDebitoKuzolaBank()
    {
        return TopicBuilder
                .name("resposta-historico-debito-kb-emis")
                .partitions(3)
                .build();
    }


    @Bean
    public NewTopic topicRespostaTransferenciaIntrabancariaEmis()
    {
        return TopicBuilder
                .name("resposta-tr-intrabancarias-kb-emis")
                .partitions(3)
                .build();
    }

    @Bean NewTopic clienteKuzola()
    {
        return  TopicBuilder
                .name("clienteKuzola")
                .partitions(3)
                .build();
    }


    @Bean
    public NewTopic respostaHistoricoDeCreditoKuzolaBank()
    {
        return TopicBuilder
                .name("resposta-historico-credito-kb-emis")
                .partitions(3)
                .build();
    }


    @Bean
    public NewTopic respostaInfoSaldoContaBancaria()
    {
        return TopicBuilder
                .name("resposta-info-saldo-kb-emis")
                .partitions(3)
                .build();
    }


}
