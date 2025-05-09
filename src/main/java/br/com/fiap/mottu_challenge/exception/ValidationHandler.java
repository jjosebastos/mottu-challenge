package br.com.fiap.mottu_challenge.exception;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.NoSuchFileException;
import java.util.List;

@RestControllerAdvice
public class ValidationHandler {

   public record ValidationError(String fieldError, String message) {
        public ValidationError(FieldError fieldError) {
            this(fieldError.getDefaultMessage(), fieldError.getField());
        }
    }

    public record SimpleError(String message) {}


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SimpleError badRequest(IllegalArgumentException e) {
        return new SimpleError(e.getMessage() != null ? e.getMessage() : "Registro não encontrado");
    }


    @ExceptionHandler(NoSuchFileException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public SimpleError notFound(NoSuchFileException e) {
       return new SimpleError(e.getMessage() != null ? e.getMessage() : "Registro não encontrado");
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ValidationError> handle(MethodArgumentNotValidException ex) {
        return ex.getFieldErrors()
                .stream()
                .map(ValidationError::new)
                .toList();
    }

}
