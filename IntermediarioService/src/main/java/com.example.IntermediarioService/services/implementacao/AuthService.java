package com.example.IntermediarioService.services.implementacao;

import com.example.IntermediarioService.config.exception.InvalidJwtException;
import com.example.IntermediarioService.dto.SignUpDto;
import com.example.IntermediarioService.entities.Cliente;
import com.example.IntermediarioService.entities.User;
import com.example.IntermediarioService.repositories.UserRepository;
import com.example.IntermediarioService.utils.pojos.ClientePOJO;
import com.example.IntermediarioService.utils.pojos.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


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
        }
        return repository.save(newUser);
    }

    public UserDetails signUp(ClientePOJO data, Cliente cliente) throws InvalidJwtException
    {
        if (repository.findByLogin(data.getLogin()) != null)
        {
            throw new InvalidJwtException("Username already exists");
        }
        User newUser = null;
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        newUser = new User(data.getLogin(), encryptedPassword, UserRole.CLIENTE, cliente);
        return repository.save(newUser);
    }

}
