package com.minitwitter.service;

import com.minitwitter.dao.UserDao;
import com.minitwitter.dto.CredentialsDto;
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


    private static AuthenticationService instance;
    
    private AuthenticationService(){}

    public static synchronized AuthenticationService getInstance(){
        if (instance == null){
               instance = new AuthenticationService();
        }
        return instance;
    }



    public  void loginDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/userlogin.jsp");
        rd.forward(request,response);
    }

    public  void logoutDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        System.out.println("logging out as "+session.getAttribute("username"));

        if (session.getAttribute("username") == null){
            request.setAttribute("warning","Please login first to use logout");
        }else {
            session.invalidate();
            request.setAttribute("success","You have been logged out successfully");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/userlogin.jsp");
        rd.forward(request,response);
    }

    public  void registerDoGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String contextPath = request.getContextPath();
        response.sendRedirect(contextPath+"/userregister.jsp");
    }

    public  void loginDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username=request.getParameter("username");
        String password=request.getParameter("password");
        CredentialsDto user=new CredentialsDto(username,password);
        System.out.println(user);
        if (!user.validParam()){
            request.setAttribute("error","Please provide valid user name and password!");
            RequestDispatcher rd = request.getRequestDispatcher("userlogin.jsp");
            rd.forward(request,response);
        }
        System.out.println("login post method:"+user);
        AuthenticationService auth=new AuthenticationService();
        if (auth.isValidCredential(user)){
            auth.createSession(request,response,user);
        }else {
            request.setAttribute("error","The user name and password didn't matches with the one in our database");;
            RequestDispatcher rd = request.getRequestDispatcher("userlogin.jsp");
            rd.forward(request,response);
        }
    }

    public  void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao userDao =UserDao.getInstance();
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String email=request.getParameter("email");

        UserDto authUser=new UserDto();
        authUser.setUserName(username);
        authUser.setEmail(email);
        authUser.setPassword(password);
        System.out.println(authUser+"\t1234");

        if (!authUser.validPayload()){
            request.setAttribute("error","Please provide valid payload!!!");;
            System.out.println("\tThis is ifelse 1");
            RequestDispatcher rd = request.getRequestDispatcher("/userregister.jsp");
            rd.forward(request,response);
            return;
        }

        try {
            if (userDao.duplicate(username)){
                request.setAttribute("error","User already exist in our database.");;
                System.out.println("\tThis is ifelse 2");
                RequestDispatcher rd = request.getRequestDispatcher("/userregister.jsp");
                rd.forward(request,response);
                return;
            }
        } catch (SQLException | ServletException | IOException e) {
            throw new RuntimeException(e);
        }
        userDao.create(username,password,email);

        request.setAttribute("success","User has been created please login!");
        System.out.println("\tThis is end");
        RequestDispatcher rd = request.getRequestDispatcher("/userlogin.jsp");
        rd.forward(request,response);
    }

    public boolean isValidCredential(CredentialsDto user) {
        UserDao userDao =UserDao.getInstance();
        UserDto userCred= userDao.search(user.getUserName());
        if (userCred == null ) return false;
        if (userCred.getUserName() == null) return false;
        if ( userCred.getPassword().equals( user.getPassword())){
            return true;
        }
        return false;

    }

    public void createSession(HttpServletRequest request, HttpServletResponse response, CredentialsDto user) throws ServletException, IOException {
        UserDao userDao =UserDao.getInstance();
        System.out.println("create session");
        HttpSession session = request.getSession();
        UserDto userCred= userDao.search(user.getUserName());
        System.out.println("valid credential"+userCred);
        session.putValue("username",userCred.getUserName());
        session.putValue("id",userCred.getUserId());
        response.sendRedirect("/tweet");
    }
}
