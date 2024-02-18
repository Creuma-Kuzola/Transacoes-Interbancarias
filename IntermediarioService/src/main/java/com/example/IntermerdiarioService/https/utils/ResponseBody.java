package com.example.IntermerdiarioService.https.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class ResponseBody
{
    private HttpStatus status;
    private String mensagem;
    private Object data;
    private LocalDateTime timestamp;
}
