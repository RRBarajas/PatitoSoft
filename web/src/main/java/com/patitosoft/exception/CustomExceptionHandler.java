package com.patitosoft.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.patitosoft.service.exception.EmployeeAlreadyExistsException;
import com.patitosoft.service.exception.EmployeeNotFoundException;
import com.patitosoft.service.exception.EmployeeNotInactiveException;
import com.patitosoft.service.exception.InvalidEmailException;
import com.patitosoft.service.exception.InvalidPositionException;
import com.patitosoft.service.exception.MultipleCurrentPositionsException;
import com.patitosoft.service.exception.PositionAlreadyExistsException;
import com.patitosoft.service.exception.PositionAlreadyMappedException;
import com.patitosoft.service.exception.PositionNotFoundException;

import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler({ EmployeeNotFoundException.class,
        PositionNotFoundException.class })
    public ResponseEntity<Object> handleNotFoundException(RuntimeException exception, WebRequest request, HttpServletRequest httpRequest) {
        log.warn(exception.getMessage());
        return buildResponse(exception, new HttpHeaders(), NOT_FOUND, request, httpRequest);
    }

    @ExceptionHandler({ EmployeeAlreadyExistsException.class,
        PositionAlreadyExistsException.class,
        PositionAlreadyMappedException.class })
    public ResponseEntity<Object> handleConflictException(RuntimeException exception, WebRequest request, HttpServletRequest httpRequest) {
        log.warn(exception.getMessage());
        return buildResponse(exception, new HttpHeaders(), CONFLICT, request, httpRequest);
    }

    @ExceptionHandler({ MultipleCurrentPositionsException.class,
        InvalidEmailException.class,
        InvalidPositionException.class,
        EmployeeNotInactiveException.class })
    public ResponseEntity<Object> handleBadRequestException(RuntimeException exception, WebRequest request,
        HttpServletRequest httpRequest) {
        log.warn(exception.getMessage());
        return buildResponse(exception, new HttpHeaders(), BAD_REQUEST, request, httpRequest);
    }

    // TODO: For some reason Spring is not throwing the exceptions during validation and this is not being reached
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
        HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.warn(errors.toString());
        return new ResponseEntity<>(errors, new HttpHeaders(), BAD_REQUEST);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request, HttpServletRequest httpRequest) {
        log.error(exception.getMessage(), exception);
        return buildResponse(exception, new HttpHeaders(), INTERNAL_SERVER_ERROR, request, httpRequest);
    }

    private ResponseEntity<Object> buildResponse(Exception exception,
        HttpHeaders headers, HttpStatus status,
        WebRequest request, HttpServletRequest httpRequest) {
        if (INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", exception, 0);
        }

        return new ResponseEntity<>(buildResponseBody(exception, httpRequest), headers, status);
    }

    private ExceptionResponse buildResponseBody(Exception exception, HttpServletRequest httpRequest) {
        return new ExceptionResponse(exception.getMessage(), httpRequest.getRequestURI());
    }
}