package com.patitosoft.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.patitosoft.service.exception.EmployeeAlreadyExistsException;
import com.patitosoft.service.exception.EmployeeNotFoundException;
import com.patitosoft.service.exception.EmployeeNotInactiveException;
import com.patitosoft.service.exception.InvalidEmailException;
import com.patitosoft.service.exception.InvalidPositionException;
import com.patitosoft.service.exception.MultipleCurrentPositionsException;

import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ EmployeeNotFoundException.class })
    public ResponseEntity<Object> handleNotFoundException(RuntimeException exception, WebRequest request, HttpServletRequest httpRequest) {
        log.warn(exception.getMessage());
        return handleExceptionInternal(exception, buildResponse(exception, httpRequest), new HttpHeaders(), NOT_FOUND, request);
    }

    @ExceptionHandler({ EmployeeAlreadyExistsException.class })
    public ResponseEntity<Object> handleConflictException(RuntimeException exception, WebRequest request, HttpServletRequest httpRequest) {
        log.warn(exception.getMessage());
        return handleExceptionInternal(exception, buildResponse(exception, httpRequest), new HttpHeaders(), CONFLICT, request);
    }

    @ExceptionHandler({ MultipleCurrentPositionsException.class,
        InvalidEmailException.class,
        InvalidPositionException.class,
        EmployeeNotInactiveException.class })
    public ResponseEntity<Object> handleBadRequestException(RuntimeException exception, WebRequest request,
        HttpServletRequest httpRequest) {
        log.warn(exception.getMessage());
        return handleExceptionInternal(exception, buildResponse(exception, httpRequest), new HttpHeaders(), BAD_REQUEST, request);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request, HttpServletRequest httpRequest) {
        log.error(exception.getMessage(), exception);
        return handleExceptionInternal(exception, buildResponse(exception, httpRequest), new HttpHeaders(), INTERNAL_SERVER_ERROR, request);
    }

    private ExceptionResponse buildResponse(Exception exception, HttpServletRequest httpRequest) {
        return new ExceptionResponse(exception.getMessage(), httpRequest.getRequestURI());
    }
}