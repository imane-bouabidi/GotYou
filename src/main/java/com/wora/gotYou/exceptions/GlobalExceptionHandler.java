package com.wora.gotYou.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoDataFoundException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDeniedException(NoDataFoundException ex) {
        return "Accès refusé : " + ex.getMessage();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
