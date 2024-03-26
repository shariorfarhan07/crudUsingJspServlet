package com.minitwitter.servlet;

import com.minitwitter.dao.userDao;
import com.minitwitter.dto.user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/tweet")
public class Tweet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        System.out.println(username+" farhan login");
        System.out.println();
        if (username == null) response.sendRedirect("login");
        userDao userdb=new userDao();
        user  u=userdb.searchUser(username);



    }
}
