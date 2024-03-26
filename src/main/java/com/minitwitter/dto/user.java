package com.minitwitter.dto;

public class user {
    private int userId;
    private String userName;
    private String password;
    private String email;


    public  boolean validPayload(){
        return !(userName == null || password == null || password.equals("") || userName.equals(""));
    }
    @Override
    public String toString() {
        return "user{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = Integer.parseInt(userId);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public user() {
    }

    public user(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
