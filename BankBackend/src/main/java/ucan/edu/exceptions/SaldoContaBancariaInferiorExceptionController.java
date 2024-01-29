/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author jussyleitecode
 *
 */
@ControllerAdvice
public class SaldoContaBancariaInferiorExceptionController
{

    @ExceptionHandler(value = SaldoContaBancariaInferiorException.class)
    public ResponseEntity<Object> exception(SaldoContaBancariaInferiorException exception)
    {
        return new ResponseEntity<>("O saldo da sua conta é muito inferior em relação o montante que pretende transferir!", HttpStatus.IM_USED);
    }

}
