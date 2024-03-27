package com.minitwitter.dao;

import com.minitwitter.dto.TweetDto;
//import com.minitwitter.dto.user;
import com.minitwitter.dto.userDto;
import com.minitwitter.service.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class userDao {
    private static final String  get_user_with_userName="SELECT * FROM user WHERE username=?";
    private static final String  search_user_with_userName="SELECT * FROM user WHERE username = ?";
    private static final String  create_user="INSERT INTO user(username, password,email) VALUES (?,?,?)";
    private static final String  getFollowers="SELECT * FROM user WHERE id in (select follower From followers_mapping where user = ?)";
//    and id != ?
    private static final String  getUserToFollow="SELECT * FROM user WHERE id not in (select follower From followers_mapping where user = ?) and id != ?";
    static Connection connection;

    public static userDto searchUser(String userName){
        try {
            connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(search_user_with_userName);
            statement.setString(1,userName);
            ResultSet resultSet = statement.executeQuery();
            userDto user=new userDto();
            while (resultSet.next()){
                user.setUserId(resultSet.getString("id"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                return  user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean createUser(String userName, String password, String email){
        try {
            connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(create_user);
            statement.setString(1,userName);
            statement.setString(2,password);
            statement.setString(3,password);
            int a =statement.executeUpdate();
            connection.close();
            return a==1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




    public static boolean hasUser(String userName) throws SQLException {
        try {
            connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(get_user_with_userName);
            statement.setString(1,userName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()&&resultSet.getString("username").equals(userName)){
                connection.close();
                return  true;
            }else {
                connection.close();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



//    public static void main(String[] args) throws SQLException {
//        System.out.println(hasUser("farhan34"));
//        System.out.println(createUser("farhan3","password",""));
//        System.out.println(hasUser("farhan3"));
//    }

    public static List<userDto> getFollowers(int userid) {
        List<userDto> t=new ArrayList<>();
        try {
            connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(getFollowers);
            statement.setInt(1,userid);
//            statement.setInt(2,userid);
            System.out.println(statement);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                userDto u =new userDto();
                u.setUserId(resultSet.getString("id"));
                u.setUserName(resultSet.getString("username"));
                u.setEmail(resultSet.getString("email"));
                t.add(u);
            }
            connection.close();
            return t;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<userDto> getUserToFollow(int userid) {
        List<userDto> t=new ArrayList<>();
        try {
            connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(getUserToFollow);
            statement.setInt(1,userid);
            statement.setInt(2,userid);
            System.out.println(statement);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                userDto u =new userDto();
                u.setUserId(resultSet.getString("id"));
                u.setUserName(resultSet.getString("username"));
                u.setEmail(resultSet.getString("email"));
                t.add(u);
            }
            connection.close();
            return t;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
