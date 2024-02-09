/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author jussyleitecode
 */

@ControllerAdvice
public class IbanIvalidaControllerException
{
    
    @ExceptionHandler(value = IbanInvalidException.class)
    public ResponseEntity<Object> nifAlreadyRecorded(IbanInvalidException iban)
    {
        return new ResponseEntity<>("Iban inválido!", HttpStatus.CONFLICT);
    }
    
}
