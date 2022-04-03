package com.example.lab2.responses;

public class ExceptionResponse extends Response {

    public ExceptionResponse(String message) {
        super(500, message);
    }
}
