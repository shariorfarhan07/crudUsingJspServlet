package com.minitwitter.servlet;


import com.minitwitter.service.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/auth/*")
public class AuthenticationServlet extends  HttpServlet  {

    AuthenticationService authService= AuthenticationService.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullPath = request.getPathInfo();
        System.out.println(fullPath);

        switch (fullPath){
            case "/login":
                authService.loginDoGet(request,response);
                break;
            case "/logout":
                authService.logoutDoGet(request,response);
                break;
            case "/register":
                authService.registerDoGet(request,response);
                break;
            default:
                System.out.println("this is the one!!!!!!!!!!!!!!!!!!!!!!!");

        }

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullPath = request.getRequestURI().trim();
        String[] split = fullPath.split("/");
        String partialPath= split[split.length-1];
        System.out.println("auth doPost "+fullPath);
        System.out.println("auth doPost partial path: "+partialPath);
        switch (partialPath){
            case "login":
                System.out.println("doPost of auth login");
                authService.loginDoPost(request,response);
                break;
            case "register":
                System.out.println("doPost of auth register");
                authService.register(request,response);
                break;
        }
    }

}
