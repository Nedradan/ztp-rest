package com.example.lab2.requests;

public class TitlesRequest {
    protected String title;

    TitlesRequest (String title){
        this.title=title;
    }

    public String getTitle() {
        return title;
    }
}
