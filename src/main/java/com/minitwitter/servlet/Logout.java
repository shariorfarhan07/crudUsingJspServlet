package com.minitwitter.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class Logout extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        System.out.println("logging out as "+session.getAttribute("username"));
        session.invalidate();
        request.setAttribute("success","You have been logged out successfully");;
        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
        rd.include(request,response);
    }
}
