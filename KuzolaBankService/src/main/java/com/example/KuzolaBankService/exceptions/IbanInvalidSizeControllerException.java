package com.example.KuzolaBankService.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class IbanInvalidSizeControllerException {

    @ExceptionHandler(value = IbanInvalidSizeException.class)
    public ResponseEntity<Object> ibanInvalidSize(IbanInvalidSizeException iban)
    {
        return new ResponseEntity<>("Este IBAN é inválido. O IBAN deve ter apenas 17 dígitos!", HttpStatus.CONFLICT);
    }
}
