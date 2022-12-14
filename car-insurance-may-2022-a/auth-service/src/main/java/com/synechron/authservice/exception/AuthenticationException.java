package com.synechron.authservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class AuthenticationException extends Exception {
    @Getter
    private final HttpStatus httpStatus;
    public AuthenticationException(String message) {
        super(message);
        httpStatus = HttpStatus.UNAUTHORIZED;
    }
}