package com.example.TransferenciaService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class TransferenciaServiceApplication
{

    @Bean
    @Primary
    public RestTemplate getRestTemplate()
    {
        return new RestTemplate();
    }

    public static void main(String[] args)
    {
        SpringApplication.run(TransferenciaServiceApplication.class, args);
    }

}
