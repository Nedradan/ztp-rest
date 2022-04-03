package com.example.lab2.requests;

public class LoginRequest {
    private String username;
    private String password;

    public void setUser(String username) {
        this.username = username;
    }

    public void setPass(String password) {
        this.password = password;
    }

    public String getUser() {
        return username;
    }

    public String getPass() {
        return password;
    }
}