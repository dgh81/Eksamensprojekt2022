package com.example.eksamensprojekt2022;

import com.example.eksamensprojekt2022.ui.Login.LoginSaveData;

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

       return  (  mySQL.logUserIn(username , password) != null );

    }

    public static void logOut() {



    }










}
