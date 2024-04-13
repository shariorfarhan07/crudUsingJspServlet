package com.minitwitter.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DatabaseConnector {

    // JDBC URL, username, and password
    private  final String JDBC_URL ;
    private  final String USERNAME ;
    private  final String PASSWORD ;

    public DatabaseConnector(String JDBC_URL,String USERNAME,String PASSWORD) {
        this.JDBC_URL=JDBC_URL ;
        this.USERNAME= USERNAME;
        this.PASSWORD= PASSWORD;
    }


    // Method to get database connection
    public  Connection getConnection() throws SQLException {
        try {
            // Register the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish connection
            return DriverManager.getConnection(this.JDBC_URL, this.USERNAME,this.PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL JDBC driver not found!");
        }
    }
}