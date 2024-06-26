package com.minitwitter.dto;

public class CredentialsDto {
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

    public CredentialsDto() {
    }

    public CredentialsDto(String userName, String password) {
        this.userName = userName;
        this.password = password;
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
