package com.example.eksamensprojekt2022.Enteties;

public class User {

    private static User userInstance = null;

    public static User getInstance() {
        if (userInstance == null)
            userInstance = new User();

        return userInstance;
    }


    public static void setInstance(User user ) {
        userInstance = user;

    }



    int userID = 0;

    String name = "";
    String password = "";



    public User(int userID, String name, String password) {
        this.userID = userID;
        this.name = name;
        this.password = password;
    }

    public User() {
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
