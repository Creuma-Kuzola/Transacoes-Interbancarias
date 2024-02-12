package com.example.KuzolaBankService.controllers;


import com.example.KuzolaBankService.dto.JwtDto;
import com.example.KuzolaBankService.dto.SignInDto;
import com.example.KuzolaBankService.utils.jsonUtils.CustomJsonPojos;
import com.example.KuzolaBankService.utils.pojos.TransferenciaResponse;
import com.google.gson.Gson;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


@RestController
@RequestMapping("/deep")
public class TesteController {

    private final RestTemplate restTemplate;

    public TesteController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public JwtDto createHeader(String login, String password)
    {
        RestTemplate restTemplate1 = new RestTemplate();
        SignInDto signInDto = new SignInDto(login,password);
       JwtDto token = restTemplate1.postForObject("http://localhost:8081/api/v1/auth/signin", signInDto, JwtDto.class);
       return token;
    }
    @GetMapping
    public String testando()
    {
        TransferenciaResponse transferenciaResponse = new TransferenciaResponse();
        transferenciaResponse.setDescricao("Conta disponivel");
        transferenciaResponse.setStatus(true);

       String jsonStr =  CustomJsonPojos.TransferenciaResponse(transferenciaResponse);
       JwtDto token = createHeader("admin","admin");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
        headers.setBearerAuth(token.accessToken());
        headers.add("body",jsonStr);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("body",jsonStr);

        HttpEntity<?> entity = new HttpEntity<>(body, headers);

        HttpEntity entityResponse = restTemplate.exchange("http://localhost:8081/transferencia/response", HttpMethod.POST,entity, String.class);

        System.out.println("entity: headers " +entity.getHeaders());
        System.out.println("entity: body " +entity.getBody());
        System.out.println("entity: headers " +body.get("body"));


        System.out.println( "token: " +token);
        System.out.println("header:" +headers);
        System.out.println("entityResponse: "+ entityResponse.getBody());
        System.out.println("entityResponse: "+ entityResponse.getHeaders());


        //restTemplate.exchange()

        /*String available =  restTemplate.postForObject("http://localhost:8081//transferencia/response", transferenciaResponse,String.class);
        System.out.println("Response: "+available); */

        return "Testando a life : token" +token;
    }
}
