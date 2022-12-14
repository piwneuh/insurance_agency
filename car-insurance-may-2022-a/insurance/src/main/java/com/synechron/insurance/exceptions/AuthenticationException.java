package com.synechron.insurance.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class AuthenticationException extends Exception {
    @Getter
    private HttpStatus httpStatus;
    public AuthenticationException(String message) {
        super(message);
        httpStatus = HttpStatus.UNAUTHORIZED;
    }
}
