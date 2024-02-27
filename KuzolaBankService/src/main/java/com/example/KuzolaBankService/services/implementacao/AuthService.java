package com.example.KuzolaBankService.services.implementacao;

import com.example.KuzolaBankService.config.component.ClienteComponent;
import com.example.KuzolaBankService.dto.SignUpDto;
import com.example.KuzolaBankService.entities.Cliente;
import com.example.KuzolaBankService.entities.User;
import com.example.KuzolaBankService.enums.UserRole;
import com.example.KuzolaBankService.exceptions.InvalidJwtException;
import com.example.KuzolaBankService.kafka.KafkaTransferenciaProducer;
import com.example.KuzolaBankService.repositories.UserRepository;
import com.example.KuzolaBankService.utils.jsonUtils.CustomJsonPojos;
import com.example.KuzolaBankService.utils.pojos.ClientePOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService implements UserDetailsService
{

    @Autowired
    UserRepository repository;

    @Autowired
    KafkaTransferenciaProducer kafkaTransferenciaProducer;

    @Autowired
    private ClienteComponent clienteComponent;

    private String DEFAULT_PASSWORD = "usuario1";

    @Override
    public UserDetails loadUserByUsername(String username)
    {
        System.out.println(" username: " + username);
        var user = repository.findByLogin(username);

        System.out.println(" username:  " + user.getUsername() + "Password: " + user.getPassword());
        return user;
    }

    public UserDetails signUp(SignUpDto data) throws InvalidJwtException
    {
       // System.out.println("FROM DTO: login" + data.login() + "Pass" + data.getPassword() + "Role " + data.role() + "Cliente " + "Funcionario" + data.fkFuncionario().getPkFuncionario());
        if (repository.findByLogin(data.getLogin()) != null)
        {
            throw new InvalidJwtException("Username already exists");
        }
        User newUser = null;
        if (data.role().getValue().equals("admin"))
        {
            String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
            newUser = new User(data.login(), encryptedPassword, data.role(), data.fkFuncionario());
        }
        else if (data.role().getValue().equals("cliente"))
        {
            String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
            newUser = new User(data.login(), encryptedPassword, data.role(), data.fkCliente());

            ClientePOJO clientePOJO = createClientePOJO(clienteComponent.getClienteComponent(), data);
            String dataStr = CustomJsonPojos.clientePOJOjson(clientePOJO);
            kafkaTransferenciaProducer.sendClienteInfo(dataStr);
        }


        return repository.save(newUser);
    }


    private ClientePOJO createClientePOJO(Map<String, String> clienteComponent, SignUpDto data) {
        ClientePOJO clientePOJO = new ClientePOJO();

        clientePOJO.setPkCliente(Integer.parseInt(clienteComponent.get("pkCliente")));
        clientePOJO.setNome(clienteComponent.get("nome"));
        clientePOJO.setNumeroConta(""+clienteComponent.get("numeroConta"));
        clientePOJO.setIban(""+clienteComponent.get("iban"));
        clientePOJO.setLogin(data.login());
        clientePOJO.setPassword(data.password());
        clientePOJO.setRole(UserRole.CLIENTE.getValue());
        clientePOJO.setFkBanco("1");
        return clientePOJO;
    }

}
