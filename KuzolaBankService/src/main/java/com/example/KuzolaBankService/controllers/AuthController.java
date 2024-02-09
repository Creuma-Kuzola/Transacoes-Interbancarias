package com.example.KuzolaBankService.controllers;

import com.example.KuzolaBankService.config.auth.TokenProvider;
import com.example.KuzolaBankService.config.component.UserInfo;
import com.example.KuzolaBankService.dto.JwtDto;
import com.example.KuzolaBankService.dto.SignInDto;
import com.example.KuzolaBankService.dto.SignUpDto;
import com.example.KuzolaBankService.entities.ContaBancaria;
import com.example.KuzolaBankService.entities.User;
import com.example.KuzolaBankService.repositories.ClienteRepository;
import com.example.KuzolaBankService.repositories.ContaBancariaRepository;
import com.example.KuzolaBankService.repositories.UserRepository;
import com.example.KuzolaBankService.services.implementacao.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController
{

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthService service;
    @Autowired
    private TokenProvider tokenService;

    @Autowired
    UserInfo userInfo;

    @Autowired
    ContaBancariaRepository contaBancarioRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpDto data)
    {
        service.signUp(data);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtDto> signIn(@RequestBody @Valid SignInDto data)
    {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());

        var authUser = authenticationManager.authenticate(usernamePassword);

        var accessToken = tokenService.generateAccessToken((User) authUser.getPrincipal());
        JwtDto jwtdto = null;
        if (accessToken != " " )
        {
            User user = (User) authUser.getPrincipal();
            Long clienteId = user.getFkCliente().getPkCliente();

            ContaBancaria contaBancaria = contaBancarioRepository.findByCliente(Math.toIntExact(clienteId));
            String username = ((User) authUser.getPrincipal()).getUsername();
            saveUserInfoTemporary(contaBancaria, username);
            jwtdto = new JwtDto(accessToken, contaBancaria.getIban(), contaBancaria.getNumeroDeConta());
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(jwtdto);
    }

    private void saveUserInfoTemporary(ContaBancaria contaBancaria, String username) {
        Map<String, String> map = new HashMap<>();

        map.put("username", username);
        map.put("iban",contaBancaria.getIban());
        map.put("accountNumber","" +contaBancaria.getNumeroDeConta());
        map.put("pkCliente",""+contaBancaria.getFkCliente().getPkCliente());
        userInfo.setUserInfo(map);

        System.out.println("IBAN:"+userInfo.getUserInfo().get("iban"));
    }

}