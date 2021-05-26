package main.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;


@ControllerAdvice
public class BlogExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(NotFoundOrBadRequestResponse.class)
    protected ResponseEntity<AwesomeException> handleNoSuchPostException(){
        return new ResponseEntity<>(new AwesomeException("No such post"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<?> handleWrongPasswordException(){
        return ResponseEntity.ok(Map.of("result", false));
    }

    @Data
    @AllArgsConstructor
    private static class AwesomeException {

        private String message;

    }
}
