package br.com.fiap.mottu_challenge.exception;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ValidationHandler {

   public record ValidationError(String fieldError, String message) {
        public ValidationError(FieldError fieldError) {
            this(fieldError.getDefaultMessage(), fieldError.getField());
        }
    }

    public record SimpleError(String message) {}

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SimpleError fallback(Exception e) {
        // faça log de e.getClass().getName()
        return new SimpleError("CAIU NO FALLBACK: " + e.getClass().getName());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SimpleError badRequest(NoResourceFoundException e) {
       return new SimpleError("Requisição inválida. Verifique os dados enviados: " + e.getMessage());
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SimpleError badRequest(IllegalArgumentException e) {
        return new SimpleError("Erro de validação. Parâmetros obrigatórios ausentes ou incorretos");
    }


    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public SimpleError notFound(NoSuchElementException e) {
       return new SimpleError("Entidade com o ID especificado não foi localizada.");
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
