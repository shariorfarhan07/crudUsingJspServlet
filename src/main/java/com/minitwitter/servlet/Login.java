package com.minitwitter.servlet;

import com.minitwitter.dao.userDao;
import com.minitwitter.dto.Credentials;
import com.minitwitter.dto.user;
import com.minitwitter.service.AuthService;
import com.minitwitter.service.DB;
import com.mysql.cj.Session;

import javax.servlet.RequestDispatcher;
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

        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
        rd.include(request,response);


    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        Credentials user=new Credentials();
        user.setUserName(username);
        user.setPassword(password);
        if (!user.validParam()){
            request.setAttribute("error","Please provide valid user name and password!");;
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.include(request,response);
        }



        System.out.println("from login:"+user);
        HttpSession session = request.getSession();
        AuthService auth=new AuthService();
        if (auth.isValidCredential(user)){
            user userCred=userDao.searchUser(user.getUserName());
            System.out.println("valid credential"+userCred);
            session.putValue("userName",userCred.getUserName());
            session.putValue("id",userCred.getUserId());
            response.sendRedirect("tweet");


        }else {
            request.setAttribute("error","The user name and password didn't matches with the one in our database");;
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.include(request,response);
        }





    }
}
