package com.example.lab2.servlets;

import com.example.lab2.beans.Book;
import com.example.lab2.beans.Role;
import com.example.lab2.beans.User;
import com.example.lab2.requests.TitlesRequest;
import com.example.lab2.responses.*;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "DashboardServlet", value = "/DashboardServlet")
public class DashboardServlet extends HttpServlet {

    ArrayList<Book> books;
    ServletContext context;
    @Override
    public void init() throws ServletException {
        context = getServletContext();
        books = (ArrayList<Book>) context.getAttribute("books");
    }

    private boolean AdminCheck(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("userLogged");
        return user.getRole()== Role.ADMIN;
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        Response dashboardGetResponse;
        try {
            GetDashboardReponse res = new GetDashboardReponse(books, 200);
            gson.toJson(res, response.getWriter());
        } catch (Exception ex) {
            dashboardGetResponse = new ExceptionResponse((ex.getLocalizedMessage()));
            gson.toJson(dashboardGetResponse, response.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        Response dashboardPostResponse;
        try {
            if(AdminCheck(request))
            {
                Book newBook=gson.fromJson(request.getReader(),Book.class);
                books.add(newBook);
                dashboardPostResponse= new OkResponse("Book has been added!");
                gson.toJson(dashboardPostResponse, response.getWriter());
            } else {
                dashboardPostResponse = new UnauthorizeException("You need admin privileges to perform this operation!");
                gson.toJson(dashboardPostResponse, response.getWriter());
            }
            GetDashboardReponse res = new GetDashboardReponse(books, 200);
            context.setAttribute("books", books);
            gson.toJson(res, response.getWriter());
        } catch (Exception ex) {
            dashboardPostResponse = new ExceptionResponse((ex.getLocalizedMessage()));
            gson.toJson(dashboardPostResponse, response.getWriter());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        Response dashboardDeleteResponse;
        try {
            if(AdminCheck(request))
            {
                TitlesRequest title=gson.fromJson(request.getReader(),TitlesRequest.class);
                for(int i = 0; i<books.size(); i++) {
                    String pomTitle = books.get(i).getBookTitle();
                    if(pomTitle.equals(title.getTitle())){
                        books.remove(i);
                        break;
                    }
                }
                context.setAttribute("books", books);

                dashboardDeleteResponse= new OkResponse("Book has been deleted!");
                gson.toJson(dashboardDeleteResponse, response.getWriter());
            } else {
                dashboardDeleteResponse = new UnauthorizeException("You need admin privileges to perform this operation!");
                gson.toJson(dashboardDeleteResponse, response.getWriter());
            }
            GetDashboardReponse res = new GetDashboardReponse(books, 200);
            gson.toJson(res, response.getWriter());
        } catch (Exception ex) {
            dashboardDeleteResponse = new ExceptionResponse((ex.getLocalizedMessage()));
            gson.toJson(dashboardDeleteResponse, response.getWriter());
        }
    }

}
