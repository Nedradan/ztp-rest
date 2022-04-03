package com.example.lab2.responses;

public class BadCredentialsException extends Response{

    public BadCredentialsException() {
        super(400, "Wrong credentials!");
    }
}
