package com.synechron.insurance.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class NotFoundException extends Exception {
    @Getter
    private HttpStatus httpStatus;
    public NotFoundException(String message) {
        super(message);
        httpStatus = HttpStatus.NOT_FOUND;
    }
}
