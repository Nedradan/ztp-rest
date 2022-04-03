package com.example.lab2.filters;

import com.example.lab2.beans.User;
import com.example.lab2.requests.LoginRequest;
import com.example.lab2.responses.UnauthorizeException;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Base64;

@WebFilter(filterName = "LoginFilter", urlPatterns = {"/DashboardServlet"})
public class LoginFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse
            response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        try {
            Object userObj = httpRequest.getSession().getAttribute("userLogged");
            if(userObj == null) {
                throw new Exception("Unauthorized user");
            }
            LoginRequest user = (LoginRequest) userObj;
            if(!checkForUserIdCookie(httpRequest.getCookies(), user)) {
                throw new Exception("No proper cookie");
            }
            chain.doFilter(request, response);
        } catch (Exception ex) {
            Gson gson = new Gson();
            response.setContentType("application/json;charset=UTF-8");
            UnauthorizeException unauthorizedResponse = new UnauthorizeException(ex.getLocalizedMessage());
            ((HttpServletResponse) response).setStatus(unauthorizedResponse.getStatus());
            gson.toJson(unauthorizedResponse, response.getWriter());
        }
    }
    private boolean checkForUserIdCookie(Cookie[] cookies, LoginRequest user) {
        for (Cookie cookie : cookies) {
            if ("userId".equals(cookie.getName())) {
                return new String(Base64.getDecoder()
                        .decode(cookie.getValue().getBytes()))
                        .equals(user.getUser());
            }
        }
        return false;
    }
}
