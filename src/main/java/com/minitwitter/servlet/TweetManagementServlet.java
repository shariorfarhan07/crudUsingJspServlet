package com.minitwitter.servlet;

import com.minitwitter.dao.TweetDao;
import com.minitwitter.dao.UserDao;
import com.minitwitter.dto.TweetDto;
import com.minitwitter.dto.UserDto;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import com.minitwitter.service.TweetMaganementService;

@WebServlet("/tweet/*")
public class TweetManagementServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullPath = request.getPathInfo();
        if (fullPath == null ) fullPath="/ ";
        System.out.println(fullPath);
        String[] pathslices= fullPath.split("/");
        System.out.println(Arrays.toString(pathslices));
        switch (pathslices[1].trim()) {
            case "update":
                try {
                    TweetMaganementService.updateTweetPageShow(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "delete":
                TweetMaganementService.deletepost(request, response);
                break;

            default:
                System.out.println("TweetManagementServlet: Going to default home page!!!");
                TweetMaganementService.tweetHome(request, response);

        }
    }





    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String fullPath = request.getPathInfo();
        System.out.println(fullPath);
        if (fullPath == null ) fullPath="/";
        switch (fullPath) {
            case "/update":
                TweetMaganementService.updateTweetPost(request, response);

                break;

            default:
                System.out.println(" TweetManagementServlet: insert post!!!");
                TweetMaganementService.tweetInsert(request, response);

        }


    }

    private void updateTweetPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String tweet = request.getParameter("tweet");
        String id = request.getParameter("id");
        HttpSession session = request.getSession();
        String contextPath = request.getContextPath();

        TweetDto tweetUpdate = new TweetDto(id, username, tweet);
        if (!tweetUpdate.isValid()) {
            request.setAttribute("error", "please provide valid data!");
            response.sendRedirect(contextPath + "/tweet");
        }

        String sessionusername = (String) session.getAttribute("username");
        System.out.println("session username: " + sessionusername);
        int userid = 0;
        if (username == null) {
            request.setAttribute("error", "You are not logged in, please login!");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
            return;
        }

        userid = (int) session.getAttribute("id");
        System.out.println("user id from tweet " + userid);
        System.out.println(sessionusername.equalsIgnoreCase(username.trim()));
        System.out.println(sessionusername + "\t\t" + username);

        if (("" + userid).equalsIgnoreCase(username.trim())) {
            System.out.println("update tweet!!!");
            tweetUpdate.setUsername(String.valueOf(userid));
            TweetDao.updateTweet(tweetUpdate);
        }


        request.setAttribute("success", "tweet has been updated");
        response.sendRedirect(contextPath + "/tweet");
    }

    private void tweetInsert(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String tweet = request.getParameter("tweet");
        if (tweet.equals("")) {
            HttpSession session = request.getSession();
            session.setAttribute("error", "can not post empty tweet");
            response.sendRedirect("tweet");
            return;
        }
        HttpSession session = request.getSession();
        String username = null;
        String userid = null;
        try {
            username = (String) session.getAttribute("username");
            if (username == null) response.sendRedirect("login.jsp");
            userid = "" + (int) session.getAttribute("id");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp");
        }

        System.out.println("\ncalling post method " + userid + " " + tweet);
        TweetDao.createTweet(Integer.parseInt(userid), tweet);
        response.sendRedirect("tweet");
    }


    private void tweetHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        System.out.println("session username: "+username);
        int userid=0;
        if (username == null) {
            request.setAttribute("error","You are not logged in, please login!");
            RequestDispatcher rd = request.getRequestDispatcher("userlogin.jsp");
            rd.forward(request,response);
            return;
        }
        userid= (int) session.getAttribute("id");
        System.out.println("user id from tweet"+userid);

        String contextPath = request.getContextPath();
        List<TweetDto> tweets = null;
        List<UserDto>  followers=null;
        List<UserDto>  userTofollow=null;
        try {
            tweets = TweetDao.SearchFriendsAndMyTweet(userid);
            followers= UserDao.getFollowers(userid);
            userTofollow= UserDao.getUserToFollow(userid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("tweets",tweets);
        request.setAttribute("followers",followers);
        request.setAttribute("userTofollow",userTofollow);
        RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
        rd.forward(request,response);
    }


    public boolean isValidNumber(String input) {
        try {
            Integer.parseInt(input); // Use Long.parseLong() for long, Float.parseFloat() for float, Double.parseDouble() for double
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}








