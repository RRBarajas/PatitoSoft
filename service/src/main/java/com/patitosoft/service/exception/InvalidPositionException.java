package com.patitosoft.service.exception;

public class InvalidPositionException extends RuntimeException {

    public InvalidPositionException() {
        super("Position must be the same in the passed parameter and the object");
    }
}
