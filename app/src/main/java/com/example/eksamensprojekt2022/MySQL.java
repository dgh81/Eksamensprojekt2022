package com.example.eksamensprojekt2022;

import java.sql.*;

public class MySQL implements Runnable {

    public MySQL() {
    }

    // public static?
    public Connection connection;

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void run() {

        try {
            //gammel driver: "com.mysql.jdbc.Driver"
            //ny     driver: "com.mysql.cj.jdbc.Driver"
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            //Fjern ssl: ?useSSL=false
            String url = "jdbc:mysql://mysql85.unoeuro.com:3306/danielguldberg_dk_db?useSSL=false";
            connection = DriverManager.getConnection(url, "danielguldberg_dk", "280781");

            System.out.println("connection created is: " + (connection != null));
            System.out.println(userType("a@a.dk"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String userType(String email) {
        String result = "";
        try {
            PreparedStatement userType = connection.
                    prepareStatement("SELECT Type FROM PasswordTable WHERE Email = '" + email + "'");
            ResultSet rs = userType.executeQuery();
            while (rs.next()) {
                result = rs.getString("Type");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}