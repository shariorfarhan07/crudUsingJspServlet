package com.minitwitter.servlet;


import com.minitwitter.dao.tweetDao;
import com.minitwitter.dto.TweetDto;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/updatetweet")
public class updatetweet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session= request.getSession();
//        System.out.println("test"+ session.getAttribute("tweet"));
        request.setAttribute( "tweet",session.getAttribute("tweet"));
        RequestDispatcher rd = request.getRequestDispatcher("updatetweet.jsp");
        rd.forward(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username=request.getParameter("username");
        String tweet=request.getParameter("tweet");
        String id=request.getParameter("id");
        HttpSession session = request.getSession();
        String contextPath = request.getContextPath();

        TweetDto tweetUpdate =new  TweetDto(id,username,tweet);
        if (!tweetUpdate.isValid()){
            request.setAttribute("error","please provide valid data!");
            response.sendRedirect(contextPath+"/tweet");
        }

        String sessionusername = (String) session.getAttribute("username");
        System.out.println("session username: "+sessionusername);
        int userid=0;
        if (username == null) {
            request.setAttribute("error","You are not logged in, please login!");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request,response);
            return;
        }

        userid= (int) session.getAttribute("id");
        System.out.println("user id from tweet "+userid);
        System.out.println(sessionusername.equalsIgnoreCase(username.trim()));
        System.out.println(sessionusername+"\t\t"+username);

        if ((""+userid).equalsIgnoreCase(username.trim())){
            System.out.println("update tweet!!!");
            tweetUpdate.setUsername(String.valueOf(userid));
            tweetDao.updateTweet(tweetUpdate);
        }


        request.setAttribute("success","tweet has been updated");
        response.sendRedirect(contextPath+"/tweet");


    }
}
