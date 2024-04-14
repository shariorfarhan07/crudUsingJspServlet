package com.minitwitter.dao;



import com.minitwitter.config.DatabaseConnector;
import com.minitwitter.dto.TweetDto;

import java.security.PublicKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TweetDao {
    private  final String create_tweet="INSERT INTO tweet(username, text) VALUES (?,?)";
    private  final String update_tweet="UPDATE tweet SET text=? WHERE username = ? and id = ?";
    private  final String delete_tweet="DELETE FROM tweet WHERE username = ? and id = ?";
    private  final String search_user_tweet="SELECT * FROM tweet WHERE username = ?";
    private  final String search_single_tweet="SELECT id, username, text FROM tweet WHERE id = ?";
    private  final String search_friends_tweet="SELECT t.id as id , u.username as username , t.text as text  FROM tweet t join user u on t.username = u.id  WHERE t.username in (SELECT follower FROM followers_mapping WHERE user = ?)";

    private static TweetDao tweetDao;
    private TweetDao(){}

    public synchronized static TweetDao getInstance(){
        if (tweetDao == null){
            tweetDao = new TweetDao();
        }
        return tweetDao;
    }
    public  List<TweetDto> SearchUserTweet(int userId) throws SQLException {
        return Search(search_user_tweet,userId);
    }
    public  List<TweetDto> searchSingleStweet(int tweetId) throws SQLException {
        return Search(search_single_tweet,tweetId);
    }
    public  List<TweetDto> SearchFriendsAndMyTweet(int userId) throws SQLException {
        return Search(search_friends_tweet+"or t.username="+userId+" order by id DESC",userId);
    }

    private  List<TweetDto> Search(String sql, int userId) throws SQLException {
        ResultSet resultSet=null;
        List<TweetDto> tweets = null;
        try {
            Connection connection = DatabaseConnector.getConnectionDB();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            System.out.println(statement);
            resultSet = statement.executeQuery();
            tweets = tweetMapper(resultSet);
            connection.close();
            return  tweets;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  boolean create(int userId ,String tweet){
        try {
            Connection connection = DatabaseConnector.getConnectionDB();
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
    public  boolean update(int tweetId, int userId,String text ){
        try {
            Connection connection = DatabaseConnector.getConnectionDB();
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

    public  boolean delete(int tweetId, int userId ){
        try {
            Connection connection = DatabaseConnector.getConnectionDB();
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

    public  void update(TweetDto tweetUpdate) {
        update(tweetUpdate.getId(), Integer.parseInt(tweetUpdate.getUsername()),tweetUpdate.getText());
    }


    private List<TweetDto> tweetMapper(ResultSet resultSet) throws SQLException {
        List<TweetDto> tweets = new LinkedList<>();
        while (resultSet.next()){
            TweetDto tweet=new TweetDto(resultSet.getString("id"),resultSet.getString("username"),resultSet.getString("text"));
            tweets.add(tweet);
        }
        return tweets;
    }


}
