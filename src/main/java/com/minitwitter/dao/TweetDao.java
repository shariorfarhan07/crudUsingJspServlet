package com.minitwitter.dao;

import com.minitwitter.dto.TweetDto;
import com.minitwitter.conf.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TweetDao {
    private static final String create_tweet="INSERT INTO tweet(username, text) VALUES (?,?)";
    private static final String update_tweet="UPDATE tweet SET text=? WHERE username = ? and id = ?";
    private static final String delete_tweet="DELETE FROM tweet WHERE username = ? and id = ?";
    private static final String search_user_tweet="SELECT * FROM tweet WHERE username = ?";
    private static final String search_single_tweet="SELECT id, username, text FROM tweet WHERE id = ?";
    private static final String search_friends_tweet="SELECT t.id as id , u.username as username , t.text as text  FROM tweet t join user u on t.username = u.id  WHERE t.username in (SELECT follower FROM followers_mapping WHERE user = ?)";


    public static List<TweetDto> SearchUserTweet(int userId) throws SQLException {
        return SearchTweet(search_user_tweet,userId);
    }
    public static List<TweetDto> searchSingleStweet(int tweetId) throws SQLException {
        return SearchTweet(search_single_tweet,tweetId);
    }
    public static List<TweetDto> SearchFriendsAndMyTweet(int userId) throws SQLException {
        return SearchTweet(search_friends_tweet+"or t.username="+userId+" order by id DESC",userId);
    }

    private static List<TweetDto> SearchTweet(String sql, int userId) throws SQLException {
        ResultSet resultSet=null;
        List<TweetDto> list = null;
        try {
            Connection connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            System.out.println(statement);
            resultSet = statement.executeQuery(); // need to test
            list = new LinkedList<>();
            while (resultSet.next()){
                TweetDto t=new TweetDto(resultSet.getString("id"),resultSet.getString("username"),resultSet.getString("text"));
//                System.out.println(t);
                list.add(t);
            }
            connection.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
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
    public static boolean updateTweet(int tweetId, int userId,String text ){
        try {
            Connection connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(update_tweet);
            statement.setString(1, text);
            statement.setInt(2, userId);
            statement.setInt(3, tweetId);
            System.out.println("Update query"+statement);
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
            statement.setInt(1, userId);
            statement.setInt(2, tweetId);
            System.out.println(statement);
            int a =statement.executeUpdate();
            connection.close();
            return a==1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static void updateTweet(TweetDto tweetUpdate) {
        updateTweet(tweetUpdate.getId(), Integer.parseInt(tweetUpdate.getUsername()),tweetUpdate.getText());
    }


}
