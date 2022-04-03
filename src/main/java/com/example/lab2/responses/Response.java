package com.example.lab2.responses;

public abstract class Response {
    private String message;
    private int status;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(int status) {
        this.status=status;
    }

    public void setMessage(String message) {
        this.message=message;
    }

    public Response(int status, String message) {
        this.status = status;
        this.message = message;
    }

}

