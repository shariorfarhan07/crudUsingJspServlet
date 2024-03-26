package com.minitwitter.dto;

public class Credentials {
    private String userName;
    private String password;

    public Boolean validParam(){
        return !(userName == null || userName.equals("")|| password == null || password.equals(""));
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Credentials() {
    }

    @Override
    public String toString() {
        return "Credentials{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }




}
