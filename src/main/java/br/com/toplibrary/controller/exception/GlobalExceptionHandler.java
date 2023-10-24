package br.com.toplibrary.controller.exception;

import br.com.toplibrary.infra.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoContentException(NotFoundException ex) {
        return new ResponseEntity<>(ErrorMessage.send(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error ->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        var message = "Já cadastrado";
        Map<String, String> errors = new HashMap<>();

        if(ex.getMessage().contains("TB_USER")) {
            if(ex.getMessage().contains("EMAIL")) {
                errors.put("email", message);
            } else {
                errors.put("username", message);
            }
        }
        else if(ex.getMessage().contains("TB_BOOK")) {
            errors.put("isbn", message);
        }
        else if(ex.getMessage().contains("TB_GENRE")) {
            errors.put("name", message);
        } else {
            errors.put("unexpected", message);
        }

        return ResponseEntity.unprocessableEntity().body(errors);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessage.send("Nome de usuário ou senha incorretos"));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Map<String, String>> handleUnexpectedException(Throwable unexpectedException) {
        String message = "Ocorreu um erro inesperado do sistema.";
        LOGGER.error(message, unexpectedException);
        return new ResponseEntity<>(ErrorMessage.send(message), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
