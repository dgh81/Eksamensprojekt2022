package com.example.eksamensprojekt2022.ui.Login;

import android.content.Context;
import android.os.Environment;
import android.provider.ContactsContract;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class LoginSaveData  extends AppCompatActivity  {

    private static LoginSaveData loginSaveDataInstance = null;

    public Context context;

    public static LoginSaveData getInstance() {
        if (loginSaveDataInstance == null)
            loginSaveDataInstance = new LoginSaveData();

        return loginSaveDataInstance;
    }

    public void saveLoginData(String username, String password) {

        try {



            File dir = new File(context.getFilesDir() , "loginInfo");

            if (!dir.exists()) {
                dir.mkdir();
            }

            File fileUsername = new File(dir , "Username");


            FileOutputStream fileOutputStream = new FileOutputStream(fileUsername);
            fileOutputStream.write(username.getBytes());
            fileOutputStream.close();

            File filePassword = new File(dir , "Password");

            FileOutputStream fileOutputStream1 = new FileOutputStream(filePassword);
            fileOutputStream1.write(password.getBytes());
            fileOutputStream1.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSavedUsername() {
        try {


            File dir = new File(context.getFilesDir() , "loginInfo");

            File file = new File(dir, "Username");

            FileInputStream fileInputStream = new FileInputStream(file);

        int c;
        String temp="";
        while( (c = fileInputStream.read()) != -1){
            temp = temp + Character.toString((char)c);
        }

        return  temp;

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public String getSavedPassword() {
        try {


            File dir = new File(context.getFilesDir() , "loginInfo");

            File file = new File(dir, "Password");

            FileInputStream fileInputStream = new FileInputStream(file);


            int c;
            String temp="";
            while( (c = fileInputStream.read()) != -1){
                temp = temp + Character.toString((char)c);
            }

            return  temp;

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }








}
