package com.minitwitter.servlet;

import com.minitwitter.service.FollowerService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@WebServlet("/follower")
public class FollowerServlet extends HttpServlet {
    FollowerService followerService =FollowerService.getFollowerService();
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String follow = request.getParameter("follow");
        String unfollow = request.getParameter("unfollow");
        System.out.println("Do get follow mapping: "+follow+" "+unfollow);

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


        if (follow!=null){
            followerService.followUser(userid, Integer.parseInt(follow));
            System.out.println(follow);
            request.setAttribute("success","user has been followed ");
        }
        if (unfollow != null){
            followerService.removeFollower(userid, Integer.parseInt(unfollow));
            request.setAttribute("success","user has been unfollowed ");
        }

        RequestDispatcher rd = request.getRequestDispatcher("tweet");
        rd.forward(request,response);


    }
}
