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
public class ContaExceptionController {

    @ExceptionHandler(value = ContaUsernameExistsException.class)
    public ResponseEntity<Object> exception(ContaUsernameExistsException exception)
    {
        return new ResponseEntity<>("Username existente no sistema!", HttpStatus.IM_USED);
    }
}
