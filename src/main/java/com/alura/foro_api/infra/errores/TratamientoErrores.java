package com.alura.foro_api.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class TratamientoErrores {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<?>> handleValidationError(MethodArgumentNotValidException e) {
        var errors = e.getFieldErrors().stream()
                .map(ValidacionErroresDatos::new)
                .toList();
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Void> handleEntityNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IntegrityValidation.class)
    public ResponseEntity<String> handleIntegrityValidation(IntegrityValidation e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleBusinessValidation(ValidationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidBody(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body("El cuerpo de la solicitud no tiene el formato correcto o contiene datos inválidos.");


    }

    private record ValidacionErroresDatos(String field, String error) {
        public ValidacionErroresDatos(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
