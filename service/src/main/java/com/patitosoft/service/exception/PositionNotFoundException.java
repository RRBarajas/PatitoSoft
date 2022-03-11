package com.patitosoft.service.exception;

public class PositionNotFoundException extends RuntimeException {

    public PositionNotFoundException(Long id) {
        super(String.format("Position '%d' does not exist", id));
    }
}
