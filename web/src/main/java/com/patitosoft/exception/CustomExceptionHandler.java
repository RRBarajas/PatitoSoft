package com.patitosoft.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.patitosoft.service.exception.EmployeeNotFoundException;

import lombok.extern.slf4j.Slf4j;

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

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request, HttpServletRequest httpRequest) {
        log.error(exception.getMessage(), exception);
        return handleExceptionInternal(exception, buildResponse(exception, httpRequest), new HttpHeaders(), INTERNAL_SERVER_ERROR, request);
    }

    private ExceptionResponse buildResponse(Exception exception, HttpServletRequest httpRequest) {
        return new ExceptionResponse(exception.getMessage(), httpRequest.getRequestURI());
    }
}