package com.patitosoft.service.exception;

public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException() {
        super("Email must not differ from the passed parameter and the object");
    }
}
