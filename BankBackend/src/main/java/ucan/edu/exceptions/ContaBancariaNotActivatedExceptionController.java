/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.exceptions;

import jakarta.servlet.annotation.HandlesTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ucan.edu.https.utils.ResponseBody;

/**
 *
 * @author jussyleitecode
 */
@ControllerAdvice
public class ContaBancariaNotActivatedExceptionController
{

    @ExceptionHandler(value = ContaBancariaNotActivatedException.class)
    public ResponseEntity<Object> contaBancariaNotActivated(ContaBancariaNotActivatedException contaNoActivated)
    {
        return new ResponseEntity<>("Conta bancaria não está activa!", HttpStatus.CONFLICT);
    }

}
