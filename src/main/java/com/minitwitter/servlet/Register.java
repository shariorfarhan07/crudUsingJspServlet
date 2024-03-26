package com.minitwitter.servlet;

import com.minitwitter.dao.userDao;
import com.minitwitter.dto.user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class Register extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.sendRedirect("register.jsp");

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String email=request.getParameter("email");

        user authUser=new user();
        authUser.setUserName(username);
        authUser.setEmail(email);
        authUser.setPassword(password);
        System.out.println(authUser);

        try {
            if (userDao.hasUser(username)){
                response.setHeader("error","User already exist in our database.");
                response.sendRedirect("login");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        userDao.createUser(username,password,email);
        response.setHeader("success","User has been created please login! please login.");
        response.sendRedirect("login");



    }
}
