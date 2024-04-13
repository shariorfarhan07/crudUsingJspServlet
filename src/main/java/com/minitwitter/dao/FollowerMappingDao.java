package com.minitwitter.dao;

import com.minitwitter.config.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FollowerMappingDao {
    private static final String insertFollower="INSERT INTO followers_mapping(user, follower) VALUES (?,?)";
    private static final String deleteFollower="DELETE FROM followers_mapping WHERE user = ? and follower = ?";
//    private static final String seeFollowe="SELECT * FROM followers_mapping WHERE user = ?";
    private static final String seeFollower="SELECT * FROM user WHERE follower in (SELECT follower FROM followers_mapping WHERE user = ?)";


    private static ResultSet CheckFollowes(int userId) throws SQLException {
        ResultSet resultSet=null;
        try {
            Connection connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(seeFollower);
            statement.setString(1, String.valueOf(userId));
            resultSet = statement.executeQuery(); // need to test
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static boolean removeFollower(int userId ,int follower){
        try {
            Connection connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(deleteFollower);
            statement.setString(1, String.valueOf(userId));
            statement.setString(2, String.valueOf(follower));
            int a =statement.executeUpdate();
            connection.close();
            return a==1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean insertFollower(int userId ,int follower){
        try {
            Connection connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(insertFollower);
            statement.setString(1, String.valueOf(userId));
            statement.setString(2, String.valueOf(follower));
            int a =statement.executeUpdate();
            connection.close();
            return a==1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
