package com.minitwitter.dao;

import com.minitwitter.service.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class tweetDao {
    private static final String create_tweet="INSERT INTO tweet(username, text) VALUES (?,?)";
    private static final String update_tweet="UPDATE tweet SET text=? WHERE username = ? and id = ?";
    private static final String delete_tweet="DELETE FROM tweet WHERE username = ? and id = ?";
    private static final String search_user_tweet="SELECT * FROM `tweet` WHERE username = ?";
    private static final String search_friends_tweet="SELECT * FROM `tweet` WHERE username in (SELECT follower FROM followers_mapping WHERE id = ?)";


    public static ResultSet SearchUserTweet(int userId) throws SQLException {
        return SearchTweet(search_user_tweet,userId);
    }
    public static ResultSet SearchFriendsTweet(int userId) throws SQLException {
        return SearchTweet(search_friends_tweet,userId);
    }


    private static ResultSet SearchTweet(String sql, int userId) throws SQLException {
        ResultSet resultSet=null;
        try {
            Connection connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, String.valueOf(userId));
            resultSet = statement.executeQuery(); // need to test
           connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }




    public static boolean createTweet(int userId ,String tweet){
        try {
            Connection connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(create_tweet);
            statement.setString(1, String.valueOf(userId));
            statement.setString(2,tweet);
            int a =statement.executeUpdate();
            connection.close();
            return a==1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean updateTweet(int tweetId, int userId ){
        try {
            Connection connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(delete_tweet);
            statement.setString(1, String.valueOf(userId));
            statement.setString(2, String.valueOf(tweetId));

            int a =statement.executeUpdate();
            connection.close();
            return a==1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean deletetweet(int tweetId, int userId ){
        try {
            Connection connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(delete_tweet);
            statement.setString(1, String.valueOf(userId));
            statement.setString(2, String.valueOf(tweetId));
            int a =statement.executeUpdate();
            connection.close();
            return a==1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

//    public static void main(String[] args) {
//
//            System.out.println(deletetweet(1,1));
//
//
//    }


}
