package com.synechron.insurance.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ValidationException extends Exception {
    @Getter
    private HttpStatus httpStatus;
    public ValidationException(String message) {
        super(message);
        httpStatus = HttpStatus.BAD_REQUEST;
    }
}
