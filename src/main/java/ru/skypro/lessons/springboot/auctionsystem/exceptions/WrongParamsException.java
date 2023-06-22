package ru.skypro.lessons.springboot.auctionsystem.exceptions;

public class WrongParamsException extends RuntimeException{

    public WrongParamsException(String message) {
        super(message);
    }
}
