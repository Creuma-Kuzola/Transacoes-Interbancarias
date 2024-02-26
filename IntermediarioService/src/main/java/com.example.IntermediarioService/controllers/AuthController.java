package com.example.IntermediarioService.controllers;

import com.example.IntermediarioService.component.UserInfo;
import com.example.IntermediarioService.config.TokenProvider;
import com.example.IntermediarioService.dto.JwtDto;
import com.example.IntermediarioService.dto.SignInDto;
import com.example.IntermediarioService.dto.SignUpDto;
import com.example.IntermediarioService.entities.Cliente;
import com.example.IntermediarioService.entities.User;
import com.example.IntermediarioService.repositories.UserRepository;
import com.example.IntermediarioService.services.implementacao.AuthService;
import com.example.IntermediarioService.services.implementacao.ClienteServiceImpl;
import com.example.IntermediarioService.services.implementacao.TransferenciaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/intermediario/auth")
public class AuthController
{
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthService service;
    @Autowired
    private TokenProvider tokenService;

    @Autowired
    ClienteServiceImpl clienteServiceImpl;

    @Autowired
    UserInfo userInfo;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransferenciaServiceImpl transferenciaServiceImpl;


    @GetMapping
    public String teste()
    {
        return "/api/intermediario/auth/";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Validated SignUpDto data)
    {
        //System.out.println("AuthController.pkFuncionario:"+data.fkFuncionario().getPkFuncionario());
        service.signUp(data);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtDto> signIn(@RequestBody @Validated SignInDto data)
    {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());

        var authUser = authenticationManager.authenticate(usernamePassword);

        var accessToken = tokenService.generateAccessToken((User) authUser.getPrincipal());
        JwtDto jwtdto = null;

       // jwtdto = new JwtDto(accessToken, "", null);
        if (accessToken != " " && ((User) authUser.getPrincipal()).getRole().getValue().equals("admin") == false)
        {
            User user = (User) authUser.getPrincipal();
            Integer clienteId = user.getFkCliente().getPkCliente();

            Optional<Cliente> cliente = clienteServiceImpl.findById(clienteId);
            String username = ((User) authUser.getPrincipal()).getUsername();
            transferenciaServiceImpl.saveUserInfoTemporary(cliente.get(), username);
            jwtdto = new JwtDto(accessToken, cliente.get().getIban(), cliente.get().getNumeroDeConta());
        }
       else {
            jwtdto = new JwtDto(accessToken, "", null);
        }
        //jwtdto = new JwtDto(accessToken, "", null);
        return ResponseEntity.ok(jwtdto);
    }

}
