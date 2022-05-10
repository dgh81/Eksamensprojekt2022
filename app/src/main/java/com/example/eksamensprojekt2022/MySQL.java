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

/*        for (int i = 0; i < 5; i++) {
            System.out.println(i);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        try {
            //gammel driver: "com.mysql.jdbc.Driver"
            //ny     driver: "com.mysql.cj.jdbc.Driver"
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            //Fjern ssl: ?useSSL=false
            String url = "jdbc:mysql://mysql85.unoeuro.com:3306/danielguldberg_dk_db?useSSL=false";  // danielguldberg_dk_db
            connection = DriverManager.getConnection(url, "danielguldberg_dk", "280781");
            System.out.println("connection created is: " + (connection != null));
            //TEST:
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
/*

    private static Connection connection;
    private static volatile MySQL instance;

    public MySQL() {
    }

    //singleton implementation
    public static MySQL getInstance() {
        MySQL result = instance;
        if (result == null) {
            synchronized (MySQL.class) {
                result = instance;
                if (result == null) {
                    instance = result = new MySQL();
                }
            }
        }
        return result;
    }

    //---------------------------------------------------------------------------------------------------------------

    //Skal denne være en Singleton klasse for sig selv?
    public static Connection connectToMySQL() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String url = "jdbc:mysql://mysql85.unoeuro.com:3306/danielguldberg_dk_db";  // danielguldberg_dk_db
            Connection connectionInternal = DriverManager.getConnection(url, "danielguldberg_dk", "280781");
            return connectionInternal;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //---------------------------------------------------------------------------------------------------------------

    */
/**
     * FUNCTIONS
     *//*


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
*/