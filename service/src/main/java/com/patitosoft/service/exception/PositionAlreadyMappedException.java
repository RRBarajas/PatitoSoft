package com.patitosoft.service.exception;

public class PositionAlreadyMappedException extends RuntimeException {

    public PositionAlreadyMappedException(Long id) {
        super(String.format("Cannot delete position '%d' due to historic purposes", id));
    }
}
