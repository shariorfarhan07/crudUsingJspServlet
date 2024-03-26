package com.minitwitter.dao;

import com.minitwitter.dto.user;
import com.minitwitter.service.DB;

import java.sql.*;

public class userDao {
    private static final String  get_user_with_userName="SELECT * FROM user WHERE username=?";
    private static final String  search_user_with_userName="SELECT * FROM user WHERE username = ?";
    private static final String  create_user="INSERT INTO user(username, password,email) VALUES (?,?,?)";
    static Connection connection;

    public static user searchUser(String userName){
        try {
            connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(search_user_with_userName+"limit 1");
            statement.setString(1,userName);
            ResultSet resultSet = statement.executeQuery();
            user user=new user();
            while (resultSet.next()){
                user.setUserId(resultSet.getString("id"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
            }
            return  user;
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



    public static void main(String[] args) throws SQLException {
        System.out.println(hasUser("farhan34"));
        System.out.println(createUser("farhan3","password",""));
        System.out.println(hasUser("farhan3"));
    }

}
