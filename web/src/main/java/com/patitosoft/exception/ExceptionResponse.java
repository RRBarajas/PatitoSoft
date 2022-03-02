package com.patitosoft.exception;

import java.time.Instant;

import lombok.Getter;

@Getter
public class ExceptionResponse {

    private Instant timestamp;

    private String message;

    private String path;

    public ExceptionResponse(String message, String path) {
        this.timestamp = Instant.now();
        this.message = message;
        this.path = path;
    }
}