package ru.skypro.lessons.springboot.auctionsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IdNotFoundException extends ResponseStatusException {

    public IdNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND,message);
        this.setTitle("ID не найден.");
    }
}
