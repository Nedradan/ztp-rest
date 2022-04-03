package com.example.lab2.servlets;

import com.example.lab2.beans.Book;
import com.example.lab2.responses.ExceptionResponse;
import com.example.lab2.responses.GetDashboardReponse;
import com.example.lab2.responses.OkResponse;
import com.example.lab2.responses.Response;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "DashboardServlet", value = "/DashboardServlet")
public class DashboardServlet extends HttpServlet {

    ArrayList<Book> books;
    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        books = (ArrayList<Book>) context.getAttribute("books");
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("In DashboardServlet GET");
        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new Gson();
        Response dashboardResponse= new OkResponse("");
        try {
            GetDashboardReponse res = new GetDashboardReponse(books, 200);
            gson.toJson(res, response.getWriter());
        } catch (Exception ex) {
            dashboardResponse = new ExceptionResponse((ex.getLocalizedMessage()));
            gson.toJson(dashboardResponse, response.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
