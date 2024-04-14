package com.minitwitter.dao;

//import com.minitwitter.dto.user;
import com.minitwitter.config.DatabaseConnector;
import com.minitwitter.dto.UserDto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private  final String  get_user_with_userName="SELECT * FROM user WHERE username=?";
    private  final String  search_user_with_userName="SELECT * FROM user WHERE username = ?";
    private  final String  create_user="INSERT INTO user(username, password,email) VALUES (?,?,?)";
    private  final String  getFollowers="SELECT * FROM user WHERE id in (select follower From followers_mapping where user = ?)";
//    and id != ?
    private  final String  getUserToFollow="SELECT * FROM user WHERE id not in (select follower From followers_mapping where user = ?) and id != ?";
    private static Connection connection;
    private static UserDao userDao;

    private UserDao(){
    }
    public  synchronized static UserDao getInstance(){
        if (userDao== null){
            userDao = new UserDao();
        }
        return userDao;
    }




    public  UserDto search(String userName){
        try {
            connection = DatabaseConnector.getConnectionDB();
            PreparedStatement statement = connection.prepareStatement(search_user_with_userName);
            statement.setString(1,userName);
            ResultSet resultSet = statement.executeQuery();
            UserDto user=new UserDto();
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


    public  boolean create(String userName, String password, String email){
        try {
            connection = DatabaseConnector.getConnectionDB();
            PreparedStatement statement = connection.prepareStatement(create_user);
            statement.setString(1,userName);
            statement.setString(2,password);
            statement.setString(3,email);
            int a =statement.executeUpdate();
            connection.close();
            return a==1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




    public  boolean duplicate(String userName) throws SQLException {
        try {
            connection = DatabaseConnector.getConnectionDB();
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



    public  List<UserDto> getFollowers(int userid) {
        List<UserDto> users=new ArrayList<>();
        try {
            connection = DatabaseConnector.getConnectionDB();
            PreparedStatement statement = connection.prepareStatement(getFollowers);
            statement.setInt(1,userid);
            System.out.println(statement);
            ResultSet resultSet = statement.executeQuery();
            users = userMapper(resultSet);
            connection.close();
            return users;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


//    public List<UserDto> mapper

    public  List<UserDto> getUserToFollow(int userid) {
        List<UserDto>  users=new ArrayList<>();
        try {
            connection = DatabaseConnector.getConnectionDB();
            PreparedStatement statement = connection.prepareStatement(getUserToFollow);
            statement.setInt(1,userid);
            statement.setInt(2,userid);
            System.out.println(statement);
            ResultSet resultSet = statement.executeQuery();
            users = userMapper(resultSet);
            connection.close();
            return users;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }
   private List<UserDto> userMapper( ResultSet resultSet) throws SQLException {
       List<UserDto> users=new ArrayList<>();
       while (resultSet.next()){
           UserDto user =new UserDto();
           user.setUserId(resultSet.getString("id"));
           user.setUserName(resultSet.getString("username"));
           user.setEmail(resultSet.getString("email"));
           users.add(user);
       }


        return null;
   }




}
