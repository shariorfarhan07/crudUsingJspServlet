package com.minitwitter.dao;

import com.minitwitter.config.DatabaseConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FollowerMappingDao {
    private  final String insertFollower="INSERT INTO followers_mapping(user, follower) VALUES (?,?)";
    private  final String deleteFollower="DELETE FROM followers_mapping WHERE user = ? and follower = ?";
    private  final String seeFollower="SELECT * FROM user WHERE follower in (SELECT follower FROM followers_mapping WHERE user = ?)";



    private static FollowerMappingDao followerMappingDao;

    private  FollowerMappingDao(){}

    public static FollowerMappingDao getInstance(){
        if (followerMappingDao == null){
            followerMappingDao =new FollowerMappingDao();
        }
        return followerMappingDao;
    }





    private  ResultSet CheckFollowes(int userId) throws SQLException {
        ResultSet resultSet=null;
        try {
            Connection connection = DatabaseConnector.getConnectionDB();
            PreparedStatement statement = connection.prepareStatement(seeFollower);
            statement.setString(1, String.valueOf(userId));
            resultSet = statement.executeQuery(); // need to test
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public  boolean removeFollower(int userId ,int follower){
        try {
            Connection connection = DatabaseConnector.getConnectionDB();
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

    public  boolean insertFollower(int userId ,int follower){
        try {
            Connection connection = DatabaseConnector.getConnectionDB();
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
