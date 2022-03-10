package com.patitosoft.service.exception;

public class EmployeeNotInactiveException extends RuntimeException {

    public EmployeeNotInactiveException(String email) {
        super(String.format("Employee '%s' is already active or does not exist", email));
    }
}
