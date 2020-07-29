package br.com.rhribeiro25.manageLabs.error.handler;

import br.com.rhribeiro25.manageLabs.error.ErrorDetails;
import br.com.rhribeiro25.manageLabs.error.ValidationErrorDetails;
import br.com.rhribeiro25.manageLabs.error.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Renan Ribeiro
 * @date 15/07/2020.
 */
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        //Argument not valid by field
        Map<String, ValidationErrorDetails> mapValErrors = new HashMap<>();
        List<ValidationErrorDetails> listVal = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> mapValErrors.put("Field " + error.getField(), ValidationErrorDetails.builder().message(error.getDefaultMessage()).parameter(error.getRejectedValue()).build()))
                .collect(Collectors.toList());

        ErrorDetails errorDetails = ErrorDetails.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Arguments are not valid")
                .timesTamp(new Date().getTime())
                .objectName(ex.getClass().getName())
                .errors(mapValErrors)
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handlerNotFoundException(NotFoundException ex) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .status(HttpStatus.NOT_FOUND.name())
                .code(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timesTamp(new Date().getTime())
                .objectName(ex.getClass().getName())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

}