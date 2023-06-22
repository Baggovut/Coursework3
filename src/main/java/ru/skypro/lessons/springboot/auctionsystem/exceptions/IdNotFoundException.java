package ru.skypro.lessons.springboot.auctionsystem.exceptions;

public class IdNotFoundException extends RuntimeException{

    public IdNotFoundException(String message) {
        super(message);
    }
}
