package com.example.KuzolaBankService.services.implementacao;

import com.example.KuzolaBankService.dto.SignUpDto;
import com.example.KuzolaBankService.entities.User;
import com.example.KuzolaBankService.exceptions.InvalidJwtException;
import com.example.KuzolaBankService.repositories.UserRepository;
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

        System.out.println("FROM DTO: login" + data.login() + "Pass" + data.getPassword() + "Role " + data.role() + "Cliente " + data.fkCliente().getPkCliente());

        if (repository.findByLogin(data.login()) != null)
        {
            throw new InvalidJwtException("Username already exists");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        User newUser = new User(data.login(), encryptedPassword, data.role(), data.fkCliente());

        System.out.println(" login:  " + newUser.getUsername() + " role" + newUser.getAuthorities() + "Cliente: " + newUser.getFkCliente().getPkCliente());

        return repository.save(newUser);

    }

}
