package com.minitwitter.service;

import com.minitwitter.dao.UserDao;
import com.minitwitter.dto.Credentials;
//import com.minitwitter.dto.user;
import com.minitwitter.dto.UserDto;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class AuthenticationService {
    public static void loginDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/userlogin.jsp");
        rd.forward(request,response);
    }

    public static void logoutDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        System.out.println("logging out as "+session.getAttribute("username"));
        session.invalidate();
        request.setAttribute("success","You have been logged out successfully");;
        RequestDispatcher rd = request.getRequestDispatcher("/userlogin.jsp");
        rd.forward(request,response);
    }

    public static void registerDoGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String contextPath = request.getContextPath();
        response.sendRedirect(contextPath+"/userregister.jsp");
    }

    public static void loginDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username=request.getParameter("username");
        String password=request.getParameter("password");
        Credentials user=new Credentials(username,password);
        System.out.println(user);
        if (!user.validParam()){
            request.setAttribute("error","Please provide valid user name and password!");
            RequestDispatcher rd = request.getRequestDispatcher("/userlogin.jsp");
            rd.forward(request,response);
        }
        System.out.println("login post method:"+user);
        AuthenticationService auth=new AuthenticationService();
        if (auth.isValidCredential(user)){
            auth.createSession(request,response,user);
        }else {
            request.setAttribute("error","The user name and password didn't matches with the one in our database");;
            RequestDispatcher rd = request.getRequestDispatcher("/userlogin.jsp");
            rd.forward(request,response);
        }
    }

    public static void registerUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String email=request.getParameter("email");

        UserDto authUser=new UserDto();
        authUser.setUserName(username);
        authUser.setEmail(email);
        authUser.setPassword(password);
        System.out.println(authUser);

        if (!authUser.validPayload()){
            request.setAttribute("error","Please provide valid payload!!!");;
            RequestDispatcher rd = request.getRequestDispatcher("userregister.jsp");
            rd.include(request,response);
            return;
        }

        try {
            if (UserDao.hasUser(username)){
                request.setAttribute("error","User already exist in our database.");;
                RequestDispatcher rd = request.getRequestDispatcher("userregister.jsp");
                rd.include(request,response);
                return;
            }
        } catch (SQLException | ServletException | IOException e) {
            throw new RuntimeException(e);
        }
        UserDao.createUser(username,password,email);

        request.setAttribute("success","User has been created please login!");
        RequestDispatcher rd = request.getRequestDispatcher("userlogin.jsp");
        rd.include(request,response);
    }

    public boolean isValidCredential(Credentials user) {
        UserDto userCred= UserDao.searchUser(user.getUserName());
        if (userCred == null ) return false;
        if (userCred.getUserName() == null) return false;
        if ( userCred.getPassword().equals( user.getPassword())){
            return true;
        }
        return false;

    }

    public void createSession(HttpServletRequest request, HttpServletResponse response,Credentials user) throws ServletException, IOException {
        System.out.println("create session");
        HttpSession session = request.getSession();
        UserDto userCred= UserDao.searchUser(user.getUserName());
        System.out.println("valid credential"+userCred);
        session.putValue("username",userCred.getUserName());
        session.putValue("id",userCred.getUserId());
        response.sendRedirect("/tweet");
    }
}
