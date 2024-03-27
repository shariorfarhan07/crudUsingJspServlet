package com.minitwitter.servlet;

import com.minitwitter.dao.tweetDao;
import com.minitwitter.dao.userDao;
import com.minitwitter.dto.TweetDto;
//import com.minitwitter.dto.user;
import com.minitwitter.dto.userDto;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/tweet/*")
public class Tweet extends HttpServlet {
    public boolean isValidNumber(String input) {
        try {
            Integer.parseInt(input); // Use Long.parseLong() for long, Float.parseFloat() for float, Double.parseDouble() for double
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        System.out.println("session username: "+username);
        int userid=0;
        if (username == null) {
            request.setAttribute("error","You are not logged in, please login!");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request,response);
            return;
        }
        userid= (int) session.getAttribute("id");
        System.out.println("user id from tweet"+userid);

        String contextPath = request.getContextPath();

        String pathInfo = request.getPathInfo();
        String id = null;
        String task =null;
        String[] parts=null;
        if (pathInfo != null) {
            parts = pathInfo.split("/");

            System.out.println(Arrays.toString(parts)+" "+ parts.length);
            if (parts.length ==2 && isValidNumber(parts[1]) ) {
                id = parts[1];
                try {
                    List<TweetDto> t=tweetDao.searchSingleStweet(Integer.parseInt(id));
                    if (t.size()!=0)session.setAttribute("tweet",(TweetDto)t.get(0));
                    if (t.size()!=0)request.setAttribute("tweet",(TweetDto)t.get(0));
                    response.sendRedirect(contextPath+"/updatetweet");

                    return;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (parts.length == 3 && isValidNumber(parts[1])  ){
                id = parts[1];
                task =parts[2];
                if (task.equals("delete")){
                    System.out.println(id+"\t\t"+userid);
                    tweetDao.deletetweet(Integer.parseInt(id),userid);
                }

                response.sendRedirect(contextPath+"/tweet?success=Tweet has been deleted!");
                return;


            }

        }




        List<TweetDto> tweets = null;
        List<userDto>  followers=null;
        List<userDto>  userTofollow=null;
        try {
            tweets = tweetDao.SearchFriendsAndMyTweet(userid);
            followers=userDao.getFollowers(userid);
            userTofollow=userDao.getUserToFollow(userid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("tweets",tweets);
        request.setAttribute("followers",followers);
        request.setAttribute("userTofollow",userTofollow);
        RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
        rd.forward(request,response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tweet=request.getParameter("tweet");
        if (tweet.equals("")) {
            HttpSession session = request.getSession();
            session.setAttribute("error","can not post empty tweet");
            response.sendRedirect("tweet");
            return;
        }
        HttpSession session = request.getSession();
        String username =null;
        String userid=null;
        try {
            username = (String) session.getAttribute("username");
            if (username==null)response.sendRedirect("login.jsp");
            userid = ""+(int)session.getAttribute("id");
        }catch (Exception e){
            e.printStackTrace();
            response.sendRedirect("login.jsp");
        }

        System.out.println("\ncalling post method " + userid+" "+tweet);
        tweetDao.createTweet(Integer.parseInt(userid),tweet);
        response.sendRedirect("tweet");
    }





}
