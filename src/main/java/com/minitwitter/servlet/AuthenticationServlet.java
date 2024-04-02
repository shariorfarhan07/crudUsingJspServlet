package com.minitwitter.servlet;


import com.minitwitter.dao.UserDao;
import com.minitwitter.dto.Credentials;
import com.minitwitter.dto.UserDto;
import com.minitwitter.service.AuthenticationService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/auth/*")
public class AuthenticationServlet extends  HttpServlet  {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullPath = request.getPathInfo();
        System.out.println(fullPath);
        switch (fullPath){
            case "/login":
                AuthenticationService.loginDoGet(request,response);
//                loginDoGet(request,response);
                break;
            case "/logout":
                AuthenticationService.logoutDoGet(request,response);
//                logoutDoGet(request,response);
                break;
            case "/register":
                AuthenticationService.registerDoGet(request,response);
//                registerDoGet(request,response);
                break;
            default:
                System.out.println("this is the one!!!!!!!!!!!!!!!!!!!!!!!");

        }


    }

//    private void registerDoGet(HttpServletRequest request, HttpServletResponse response) throws  IOException  {
//        String contextPath = request.getContextPath();
//        response.sendRedirect(contextPath+"/userregister.jsp");
//    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullPath = request.getRequestURI().trim();
        String[] split = fullPath.split("/");
        String partialPath= split[split.length-1];
        System.out.println("auth doPost "+fullPath);
        System.out.println("auth doPost "+partialPath);
        switch (partialPath){
            case "login":
                System.out.println("doPost of auth login");
                AuthenticationService.loginDoPost(request,response);
//                loginDoPost(request,response);
                break;
            case "register":
                System.out.println("doPost of auth register");
                AuthenticationService.registerUser(request,response);
                break;
        }
    }

//    private void registerUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String username=request.getParameter("username");
//        String password=request.getParameter("password");
//        String email=request.getParameter("email");
//
//        UserDto authUser=new UserDto();
//        authUser.setUserName(username);
//        authUser.setEmail(email);
//        authUser.setPassword(password);
//        System.out.println(authUser);
//
//        if (!authUser.validPayload()){
//            request.setAttribute("error","Please provide valid payload!!!");;
//            RequestDispatcher rd = request.getRequestDispatcher("userregister.jsp");
//            rd.include(request,response);
//            return;
//        }
//
//        try {
//            if (UserDao.hasUser(username)){
//                request.setAttribute("error","User already exist in our database.");;
//                RequestDispatcher rd = request.getRequestDispatcher("userregister.jsp");
//                rd.include(request,response);
//                return;
//            }
//        } catch (SQLException | ServletException | IOException e) {
//            throw new RuntimeException(e);
//        }
//        UserDao.createUser(username,password,email);
//
//        request.setAttribute("success","User has been created please login!");
//        RequestDispatcher rd = request.getRequestDispatcher("userlogin.jsp");
//        rd.include(request,response);
//    }
//
//
//    private void loginDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
//
//        String username=request.getParameter("username");
//        String password=request.getParameter("password");
//        Credentials user=new Credentials(username,password);
//        System.out.println(user);
//        if (!user.validParam()){
//            request.setAttribute("error","Please provide valid user name and password!");
//            RequestDispatcher rd = request.getRequestDispatcher("/userlogin.jsp");
//            rd.forward(request,response);
//        }
//        System.out.println("login post method:"+user);
//        AuthenticationService auth=new AuthenticationService();
//        if (auth.isValidCredential(user)){
//            auth.createSession(request,response,user);
//        }else {
//            request.setAttribute("error","The user name and password didn't matches with the one in our database");;
//            RequestDispatcher rd = request.getRequestDispatcher("/userlogin.jsp");
//            rd.forward(request,response);
//        }
//    }

//    private void logoutDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        System.out.println("logging out as "+session.getAttribute("username"));
//        session.invalidate();
//        request.setAttribute("success","You have been logged out successfully");;
//        RequestDispatcher rd = request.getRequestDispatcher("/userlogin.jsp");
//        rd.forward(request,response);
//    }

//    private void loginDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
//        RequestDispatcher rd = request.getRequestDispatcher("/userlogin.jsp");
//        rd.forward(request,response);
//    }

}
