package com.minitwitter.conf;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

public class DB {
    public static DatabaseConnector connector;
    private static DB instance;
    private DB() throws FileNotFoundException {
        ApplicationProperties ap=ApplicationProperties.getProperties();
        String JDBC_URL = ap.getProp("DB.JDBC_URL");
        String USERNAME = ap.getProp("DB.USERNAME");
        String PASSWORD = ap.getProp("DB.PASSWORD");

        System.out.println(JDBC_URL+" "+USERNAME+" "+PASSWORD);
//        String JDBC_URL = "jdbc:mysql://localhost:3306/tweeter";
//        String USERNAME ="root1";
//        String PASSWORD ="root1";
        connector=new DatabaseConnector(JDBC_URL,USERNAME,PASSWORD);
    }
    public  static Connection getConnection() throws SQLException {
      if (instance==null){
          try {
              instance=new DB();
          } catch (FileNotFoundException e) {
              throw new RuntimeException(e);
          }
      }
      return instance.connector.getConnection();
    }
}
