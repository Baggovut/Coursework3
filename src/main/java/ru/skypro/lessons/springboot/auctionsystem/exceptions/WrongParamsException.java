package ru.skypro.lessons.springboot.auctionsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WrongParamsException extends ResponseStatusException {

    public WrongParamsException(String message) {
        super(HttpStatus.BAD_REQUEST,message);
    }
}
