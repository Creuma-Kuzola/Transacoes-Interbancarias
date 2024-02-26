package com.example.IntermediarioService.authoriazation;

import com.example.IntermediarioService.dto.JwtDto;
import com.example.IntermediarioService.dto.SignInDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class Authorizarization{

    public static JwtDto createHeader(String login, String password)
    {
        RestTemplate restTemplate1 = new RestTemplate();
        SignInDto signInDto = new SignInDto(login,password);
        JwtDto token = restTemplate1.postForObject("http://localhost:8082/api/intermediario/auth/signin", signInDto, JwtDto.class);
        return token;
    }

    public static HttpEntity createBody()
    {
        JwtDto token = Authorizarization.createHeader("admin","admin");
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
        headers.setBearerAuth(token.accessToken());

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        //body.add("body",jsonStr);
        HttpEntity<?> entity = new HttpEntity<>(body, headers);

        return entity;
    }
}
