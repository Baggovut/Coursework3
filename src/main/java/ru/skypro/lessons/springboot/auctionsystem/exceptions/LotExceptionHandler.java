package ru.skypro.lessons.springboot.auctionsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LotExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<?> handleIdNotFoundException(IdNotFoundException idNotFoundException){
        return new ResponseEntity<>(idNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<?> handleWrongParamsException(WrongParamsException wrongParamsException){
        return new ResponseEntity<>(wrongParamsException.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
