package com.example.KuzolaBankService;

import com.example.KuzolaBankService.utils.ContaBancariaDetails;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.math.BigInteger;
import java.util.HashSet;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("file:${user.dir}/.env")
public class KuzolaBankServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KuzolaBankServiceApplication.class, args);
	}

}
