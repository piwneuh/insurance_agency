package com.synechron.insurance.controller;

import com.synechron.insurance.exceptions.ApiError;
import com.synechron.insurance.exceptions.AuthenticationException;
import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.exceptions.ValidationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleCountryNotFoundException(HttpServletRequest request, NotFoundException exception) {
        return new ResponseEntity<>(ApiError.builder()
                .timestamp(new Date())
                .status(exception.getHttpStatus().value())
                .error(exception.getHttpStatus().getReasonPhrase())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build(), exception.getHttpStatus());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(HttpServletRequest request, ValidationException exception) {
        return new ResponseEntity<>(ApiError.builder()
                .timestamp(new Date())
                .status(exception.getHttpStatus().value())
                .error(exception.getHttpStatus().getReasonPhrase())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build(), exception.getHttpStatus());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(HttpServletRequest request, AuthenticationException exception) {
        return new ResponseEntity<>(ApiError.builder()
                .timestamp(new Date())
                .status(exception.getHttpStatus().value())
                .error(exception.getHttpStatus().getReasonPhrase())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build(), exception.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(ApiError.builder()
                .timestamp(new Date())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining("; ")))
                .path(request.getRequestURI())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
