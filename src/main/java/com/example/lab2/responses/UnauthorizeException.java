package com.example.lab2.responses;

public class UnauthorizeException extends Response{
    public UnauthorizeException(String message) {
        super(401, message);
    }
}
