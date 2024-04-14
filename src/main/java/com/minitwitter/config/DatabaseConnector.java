package com.minitwitter.config;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
//    DatabaseConnector.getConnectionDB()
    private static DatabaseConnector databaseConnector;
    // JDBC URL, username, and password
    private  final String JDBC_URL ;
    private  final String USERNAME ;
    private  final String PASSWORD ;

    public static synchronized  Connection getConnectionDB() throws SQLException{
        ApplicationProperties ap= null;
        String JDBC_URL;
        String USERNAME;
        String PASSWORD;
        if (databaseConnector == null) {
            try {
                ap = ApplicationProperties.getProperties();
                JDBC_URL = ap.getProp("DB.JDBC_URL");
                USERNAME = ap.getProp("DB.USERNAME");
                PASSWORD = ap.getProp("DB.PASSWORD");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            System.out.println(JDBC_URL + " " + USERNAME + " " + PASSWORD);
            databaseConnector = new DatabaseConnector(JDBC_URL, USERNAME, PASSWORD);
        }

        try {
            Connection connection = databaseConnector.getConnection();
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





    private DatabaseConnector(String JDBC_URL,String USERNAME,String PASSWORD) {
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