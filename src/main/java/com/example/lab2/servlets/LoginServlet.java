package com.example.lab2.servlets;

import com.example.lab2.beans.Role;
import com.example.lab2.beans.User;
import com.example.lab2.requests.LoginRequest;
import com.example.lab2.responses.BadCredentialsException;
import com.example.lab2.responses.ExceptionResponse;
import com.example.lab2.responses.OkResponse;
import com.example.lab2.responses.Response;
import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {

    HashMap<String, String> Users=new HashMap<String, String>();

    @Override
    public void init() throws ServletException {
        super.init();
        Users.put("user1","pass1");
        Users.put("user2","pass2");
        Users.put("user3","pass3");
        Users.put("user4","pass4");
    }

    private boolean checkUser(String login, String password){
        try {
            String pass = Users.get(login);
            return pass.equals(password);
        }
        catch (Exception e) {
            return false;
        }
    }
    private String getBase64FromString(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = null;
        Response loginResponse= new ExceptionResponse("Wrong data!");
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            gson = new Gson();
            LoginRequest loginRequest =
                    gson.fromJson(request.getReader(), LoginRequest.class);
            String login = loginRequest.getUser();
            String password = loginRequest.getPass();
            HttpSession session = request.getSession();

            if ("admin".equals(login)) {
                if ("admin".equals(password)) {
                    User user = new User (login,password, Role.ADMIN);
                    session.setAttribute("userLogged", user);
                    response.addCookie(new Cookie("userId",getBase64FromString(login)));
                    loginResponse = new OkResponse();
                } else {
                    loginResponse=new BadCredentialsException();
                }
            } else {
                if (checkUser(login, password)) {
                    User user = new User (login,password);
                    session.setAttribute("userLogged", user);
                    response.addCookie(new Cookie("userId",getBase64FromString(login)));
                    loginResponse = new OkResponse();
                } else {
                    loginResponse=new BadCredentialsException();
                }
            }
            response.setStatus(loginResponse.getStatus());
            gson.toJson(loginResponse, response.getWriter());

        } catch (Exception ex) {
            loginResponse = new ExceptionResponse((ex.getLocalizedMessage()));
            gson.toJson(loginResponse, response.getWriter());
        }
    }
}
