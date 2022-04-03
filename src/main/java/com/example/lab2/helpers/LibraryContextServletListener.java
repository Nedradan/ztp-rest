package com.example.lab2.helpers;

import com.example.lab2.beans.Book;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.ArrayList;

@WebListener
public class LibraryContextServletListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ArrayList<Book> books = new ArrayList<Book>();
        for (int i=0;i<=2;i++){
            Book bookPom= new Book(i);
            books.add(bookPom);
        }
        sce.getServletContext().setAttribute("books", books);
    }

}
