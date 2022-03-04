package com.patitosoft.service.exception;

public class EmployeeAlreadyExistsException extends RuntimeException {

    public EmployeeAlreadyExistsException(String email) {
        super(String.format("Employee '%s' already exists", email));
    }
}
