package com.patitosoft.service.exception;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(String email) {
        super(String.format("Employee '%s' does not exist", email));
    }
}
