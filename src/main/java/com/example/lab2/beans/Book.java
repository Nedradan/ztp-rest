package com.example.lab2.beans;

public class Book {

    protected String title;
    protected String author;
    protected int year;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Book(int number){
        this.title = "Książka"+String.valueOf(number);
        this.author = "Autor"+String.valueOf(number);
        this.year = 1999+number;
    }

    public String getBookTitle(){
        return title;
    }

    @Override
    public String toString() {
        return "<tr>\n" +
                "<td><center>"+title+"</center></td>\n" +
                "<td><center>"+author+"</center></td>\n" +
                "<td><center>"+year+"</center></td>\n" +
                "</tr>\n";
    }
}
