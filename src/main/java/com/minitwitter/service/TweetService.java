package com.minitwitter.service;

import com.minitwitter.dao.TweetDao;
import com.minitwitter.dao.UserDao;
import com.minitwitter.dto.TweetDto;
import com.minitwitter.dto.UserDto;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class TweetService {




    public static void updateTweetPageShow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String fullPath = request.getPathInfo();
        System.out.println(fullPath+" updateTweetPageShow");
        String[] pathslices= fullPath.split("/");
        System.out.println("update tweet");
        request.setAttribute("tweet", TweetDao.searchSingleStweet(Integer.parseInt(pathslices[2].trim())).get(0));
        RequestDispatcher rd = request.getRequestDispatcher("/theupdatetweet.jsp");
        rd.forward(request, response);
    }

    public static void deletepost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        System.out.println("user id from tweet : "+userid);
        String contextPath = request.getContextPath();
        String pathInfo = request.getPathInfo();
        System.out.println(pathInfo);
        System.out.println(contextPath);
        String id = null;
        String task =null;
        String[] parts=pathInfo.split("/");
        System.out.println(Arrays.toString(parts)+"  farhan");
        id = parts[2].trim();
        task = parts[1].trim();
        System.out.println(id+"\t\t"+userid);
        TweetDao.deletetweet(Integer.parseInt(id),userid);
        response.sendRedirect(contextPath+"/tweet?success=Tweet has been deleted!");
    }

    public static void tweetHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    public static void tweetInsert(HttpServletRequest request, HttpServletResponse response) throws IOException {

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
            if (username == null) response.sendRedirect("userlogin.jsp");
            userid = "" + (int) session.getAttribute("id");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp");
        }

        System.out.println("\ncalling post method " + userid + " " + tweet);
        TweetDao.createTweet(Integer.parseInt(userid), tweet);
        response.sendRedirect("tweet");
    }

    public static void updateTweetPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
            RequestDispatcher rd = request.getRequestDispatcher("userlogin.jsp");
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
}
