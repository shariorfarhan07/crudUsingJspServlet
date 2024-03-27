package com.minitwitter.dto;

public class TweetDto {
    private int id;
    private String username;
    private String text;


    public boolean isValid(){
        System.out.println(text+" is not valid");
        return !(username == null  || text == null || username.equals("")|| text.trim().equals(""));
    }

    public TweetDto(String id, String username, String text) {
        this.id = Integer.parseInt(id);
        this.username=username;
        this.text=text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "TweetDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
