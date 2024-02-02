package ucan.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("file:${user.dir}/.env")
public class BankBackendApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(BankBackendApplication.class, args);
    }

}
