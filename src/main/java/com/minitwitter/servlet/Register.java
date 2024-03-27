package com.minitwitter.servlet;

import com.minitwitter.dao.userDao;
//import com.minitwitter.dto.user;
import com.minitwitter.dto.userDto;

import javax.servlet.RequestDispatcher;
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

        userDto authUser=new userDto();
        authUser.setUserName(username);
        authUser.setEmail(email);
        authUser.setPassword(password);
        System.out.println(authUser);

        if (!authUser.validPayload()){
            request.setAttribute("error","Please provide valid payload!!!");;
            RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
            rd.include(request,response);
            return;
        }

        try {
            if (userDao.hasUser(username)){
                request.setAttribute("error","User already exist in our database.");;
                RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
                rd.include(request,response);
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        userDao.createUser(username,password,email);

        request.setAttribute("success","User has been created please login!");
        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
        rd.include(request,response);
    }
}
