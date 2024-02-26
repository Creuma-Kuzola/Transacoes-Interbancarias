package ucan.edu.services.implementacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ucan.edu.config.component.ClienteComponent;
import ucan.edu.dtos.SignUpDto;
import ucan.edu.entities.Cliente;
import ucan.edu.entities.ContaBancaria;
import ucan.edu.entities.User;
import ucan.edu.exceptions.InvalidJwtException;
import ucan.edu.kafka.KafkaTransferenciaProducer;
import ucan.edu.repository.UserRepository;
import ucan.edu.utils.enums.UserRole;
import ucan.edu.utils.jsonUtils.CustomJsonPojos;
import ucan.edu.utils.pojos.ClientePOJO;

import java.util.Map;

@Service
public class AuthService implements UserDetailsService
{

    @Autowired
    UserRepository repository;
    @Autowired
    private ClienteComponent clienteComponent;
    @Autowired
    private KafkaTransferenciaProducer kafkaTransferenciaProducer;

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
        if (repository.findByLogin(data.login()) != null)
        {
            throw new InvalidJwtException("Username already exists");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role(), data.fkCliente());

        ClientePOJO clientePOJO = createClientePOJO(clienteComponent.getClienteComponent(), data);
        String dataStr = CustomJsonPojos.clientePOJOjson(clientePOJO);
        kafkaTransferenciaProducer.sendClienteInfo(dataStr);

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
        clientePOJO.setFkBanco("2");
        return clientePOJO;
    }

}
