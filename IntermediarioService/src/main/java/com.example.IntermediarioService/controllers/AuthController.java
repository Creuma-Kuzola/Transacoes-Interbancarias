package com.example.IntermediarioService.controllers;

import com.example.IntermediarioService.config.TokenProvider;
import com.example.IntermediarioService.dto.JwtDto;
import com.example.IntermediarioService.dto.SignInDto;
import com.example.IntermediarioService.dto.SignUpDto;
import com.example.IntermediarioService.entities.User;
import com.example.IntermediarioService.repositories.UserRepository;
import com.example.IntermediarioService.services.implementacao.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

   // @Autowired
   // ContaBancariaRepository contaBancarioRepository;

    @Autowired
    UserRepository userRepository;


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

       /* jwtdto = new JwtDto(accessToken, "", null);
        if (accessToken != " " && ((User) authUser.getPrincipal()).getRole().getValue().equals("admin") == false)
        {
            User user = (User) authUser.getPrincipal();
            Long clienteId = user.getFkCliente().getPkCliente();

            ContaBancaria contaBancaria = contaBancarioRepository.findByCliente(Math.toIntExact(clienteId));
            String username = ((User) authUser.getPrincipal()).getUsername();
            saveUserInfoTemporary(contaBancaria, username);
            jwtdto = new JwtDto(accessToken, contaBancaria.getIban(), contaBancaria.getNumeroDeConta());
        }
       else {
            jwtdto = new JwtDto(accessToken, "", null);
        }  */
        jwtdto = new JwtDto(accessToken, "", null);
        return ResponseEntity.ok(jwtdto);
    }

   /* private void saveUserInfoTemporary(ContaBancaria contaBancaria, String username) {
        HashMap<String, String> map = new HashMap<>();

        map.put("username", username);
        map.put("iban",contaBancaria.getIban());
        map.put("accountNumber",""+contaBancaria.getNumeroDeConta());
        map.put("pkCliente",""+contaBancaria.getFkCliente().getPkCliente());
        userInfo.setUserInfo(map);

        System.out.println("IBAN:"+userInfo.getUserInfo().get("iban"));
        System.out.println("numeroDaConta:"+userInfo.getUserInfo().get("accountNumber"));
    } */

}
