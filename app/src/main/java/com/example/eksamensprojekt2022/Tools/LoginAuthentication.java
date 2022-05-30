package com.example.eksamensprojekt2022.Tools;

import com.example.eksamensprojekt2022.DBController.MySQL;
import com.example.eksamensprojekt2022.Enteties.User;
import com.example.eksamensprojekt2022.UI.Login.LoginSaveData;

import java.sql.SQLException;

public class LoginAuthentication  {

    public static boolean login (String username, String password) throws SQLException {

        MySQL mySQL = new MySQL();

        if (mySQL.logUserIn(username , password) != null ) {
            LoginSaveData.getInstance().saveLoginData(username , password);
            return  true;
        }
        return  false;


    }


    public static boolean attemptLoginWithSavedLoginData()  {

       String username =  LoginSaveData.getInstance().getSavedUsername();
       String password = LoginSaveData.getInstance().getSavedPassword();

       MySQL mySQL = new MySQL();

       User user = mySQL.logUserIn( username , password );




       if ( user != null ) {

           User.getInstance().setUserID(user.getUserID());
           User.getInstance().setName(user.getName());
           User.getInstance().setPassword(user.getPassword());

           return  true;
       }

       return  false;


    }

    public static void logOut() {

        User.setInstance(new User() );

        LoginSaveData.getInstance().saveLoginData("" , "");

        System.out.println(User.getInstance());



    }










}
