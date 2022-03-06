package com.patitosoft.service.exception;

public class MultipleCurrentPositionsException extends RuntimeException {

    public MultipleCurrentPositionsException(String email) {
        super(String.format("Employee '%s' cannot have multiple current positions", email));
    }
}
