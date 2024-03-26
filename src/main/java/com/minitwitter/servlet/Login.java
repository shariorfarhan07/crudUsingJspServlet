package com.minitwitter.servlet;

import com.minitwitter.dao.userDao;
import com.minitwitter.dto.Credentials;
import com.minitwitter.dto.user;
import com.minitwitter.service.AuthService;
import com.minitwitter.service.DB;
import com.mysql.cj.Session;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.invoke.StringConcatFactory;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/login")
public class Login extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
            System.out.println(userDao.hasUser("farhan1"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if (username!=null) response.sendRedirect("/");
        response.sendRedirect("login.jsp");

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        Credentials user=new Credentials();
        user.setUserName(username);
        user.setPassword(password);
        System.out.println(user);
        HttpSession session = request.getSession();
        AuthService auth=new AuthService();
        if (auth.isValidCredential(user)){
            session.putValue("userName",user.getUserName());
            response.sendRedirect("/");
        }
        response.setHeader("error","Invalid username or password");
        response.sendRedirect("login");

    }
}
