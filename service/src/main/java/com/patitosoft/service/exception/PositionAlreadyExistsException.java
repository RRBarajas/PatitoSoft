package com.patitosoft.service.exception;

public class PositionAlreadyExistsException extends RuntimeException {

    public PositionAlreadyExistsException(Long id) {
        super(String.format("Position '%d' already exists", id));
    }
}
