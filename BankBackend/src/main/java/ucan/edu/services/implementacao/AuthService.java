package ucan.edu.services.implementacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.auth0.jwt.exceptions.JWTVerificationException;
import ucan.edu.dtos.SignUpDto;
import ucan.edu.entities.Cliente;
import ucan.edu.entities.Conta;
import ucan.edu.entities.User;
import ucan.edu.exceptions.InvalidJwtException;
import ucan.edu.repository.ContaRepository;
import ucan.edu.repository.UserRepository;

@Service
public class AuthService implements UserDetailsService
{

    @Autowired
    UserRepository repository;

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

        User newUser = new User(data.login(), encryptedPassword, data.role());

        return repository.save(newUser);

    }
//
//    @Autowired
//    ContaRepository repository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username)
//    {
//        System.out.println(" username: " + username);
//        var conta = repository.findContaByLogin(username);
//
//        Conta contaExist = (Conta) repository.findContaByLogin(username);
//
//        System.out.println("UserDetails");
//        System.out.println(" Username: " + conta.getUsername() + "Password: " + conta.getPassword() + "Role: " + conta.getAuthorities());
//
//        System.out.println("Password: " + contaExist.getPassword() + "Username " + conta.getUsername());
//        return conta;
//    }
//
//    public UserDetails signUp(SignUpDto data)
//    {
//        if (repository.findContaByLogin(data.username()) != null)
//        {
//            throw new RuntimeException("Username already exists");
//        }
//        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
//        Conta newUser = new Conta();
//
//        newUser.setPassword(encryptedPassword);
//        newUser.setLogin(data.username());
//        newUser.setRole(data.role());
//
//        Cliente cliente = new Cliente();
//        cliente.setPkCliente(data.fkCliente());
//        newUser.setFkCliente(cliente);
//
//        return repository.save(newUser);
//    }
}
