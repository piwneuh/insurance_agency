package com.synechron.authservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class NotUniqueException extends Exception {
    @Getter
    private final HttpStatus httpStatus;
    public NotUniqueException(String message) {
        super(message);
        httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
    }
}