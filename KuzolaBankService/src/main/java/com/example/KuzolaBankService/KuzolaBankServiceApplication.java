package com.example.KuzolaBankService;

import com.example.KuzolaBankService.utils.ContaBancariaDetails;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.math.BigInteger;
import java.util.HashSet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@PropertySource("file:${user.dir}/.env")
public class KuzolaBankServiceApplication {

	@Bean
	@Primary
	public RestTemplate getRestTemplate()
	{
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(KuzolaBankServiceApplication.class, args);
	}

}
