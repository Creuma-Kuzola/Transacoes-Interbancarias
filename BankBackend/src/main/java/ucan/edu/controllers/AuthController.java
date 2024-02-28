package ucan.edu.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ucan.edu.config.auth.TokenProvider;
import ucan.edu.config.component.UserInfo;
import ucan.edu.dtos.JwtDto;
import ucan.edu.dtos.SignInDto;
import ucan.edu.dtos.SignUpDto;
import ucan.edu.entities.Conta;
import ucan.edu.entities.ContaBancaria;
import ucan.edu.entities.User;
import ucan.edu.repository.ContaBancariaRepository;
import ucan.edu.services.implementacao.AuthService;

import java.util.HashMap;
import java.util.Map;

// controllers/AuthController.java
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
        if (accessToken != " "  &&  ((User) authUser.getPrincipal()).getRole().getValue().equals("admin") == false)
        {
            User user = (User) authUser.getPrincipal();
            Integer clienteId = user.getFkCliente().getPkCliente();

            System.out.println("clienteId: "+clienteId);

            ContaBancaria contaBancaria = contaBancarioRepository.findByCliente(Math.toIntExact(clienteId));
            String username = ((User) authUser.getPrincipal()).getUsername();
            saveUserInfoTemporary(contaBancaria, username, accessToken);
            jwtdto = new JwtDto(accessToken);
        }
        else {
            jwtdto = new JwtDto(accessToken);
        }

        return ResponseEntity.ok(jwtdto);
    }
    @GetMapping("/logout")
    public ResponseEntity<?> logout()
    {
        tokenService.invalidateToken(userInfo.getUserInfo().get("token"));
        return ResponseEntity.ok("Logout feito com sucesso!");
    }



    private void saveUserInfoTemporary(ContaBancaria contaBancaria, String username, String accessToken) {
        HashMap<String, String> map = new HashMap<>();

        map.put("username", username);
        map.put("iban",contaBancaria.getIban());
        map.put("accountNumber",""+contaBancaria.getNumeroDeConta());
        map.put("pkCliente",""+contaBancaria.getFkCliente().getPkCliente());
        map.put("token",accessToken);
        userInfo.setUserInfo(map);

        System.out.println("IBAN:"+userInfo.getUserInfo().get("iban"));
        System.out.println("numeroDaConta:"+userInfo.getUserInfo().get("accountNumber"));
    }



}
