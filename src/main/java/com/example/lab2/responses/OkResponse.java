package com.example.lab2.responses;

public class OkResponse extends Response{

    public OkResponse(String message) {
        super(200, message);
    }
}