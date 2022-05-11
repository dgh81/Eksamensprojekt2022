package com.example.eksamensprojekt2022;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Test FTP
/*    private MyFTPClientFunctions ftpclient = null;
    private static final String TAG = "MainActivity";
    private static final String TEMP_FILENAME = "sample.txt";
    private Context cntx = null;*/

    //Connection connection = null;

    ImageView imageView;
    Button btOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Test Camera
        imageView = findViewById(R.id.imageView);
        btOpen = findViewById(R.id.btnCamera);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[] {
                          Manifest.permission.CAMERA
                        },
                        100);
            }
        }

        btOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });
        //Test FTP
        //ftpclient = new MyFTPClientFunctions();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(captureImage);


            FTP ftp = new FTP();
            ftp.sendPicToFTP(captureImage);
        }
    }

    public void test(View view) throws InterruptedException, SQLException {


        /*
        Thread t = new Thread(mysql);
        // t.run(): kør den ene tråd først
        // t.start(): kør tråde synkront
        t.start();
        // join venter på threads er færdige inden resten af koden køres
        t.join();*/

        //Skal køres hver gang en klasse kalder på runnable MySQL:
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        System.out.println("Klik");

        MySQL mysql = new MySQL();
        Thread mysqlConnection = new Thread(mysql);
        mysqlConnection.run();
        mysqlConnection.join();
        User loggedInUser = mysql.logUserIn("David", "123");

        if (loggedInUser == null) {
            MySQL.connection.close();
            MySQL.connection = null;
            System.out.println("set connection thread isAlive?: " + mysqlConnection.isAlive());
            System.out.println("loggedInUser: null");
            System.out.println("getConnection: " + mysql.connection);

        } else {
            System.out.println("set connection thread isAlive?: " + mysqlConnection.isAlive());
            System.out.println("loggedInUser: " + loggedInUser);
            System.out.println("getConnection: " + mysql.connection);
            ArrayList<String> questionGroups = mysql.getQuestionGroups();

            //Print alle questiongroups og deres tilhørende questions
            for (int i = 0; i < questionGroups.size(); i++) {
                System.out.println(questionGroups.get(i) + ":");
                ArrayList<Question> questions = mysql.getQuestionsFromGroupTitle(questionGroups.get(i));
                for (int j = 0; j < questions.size(); j++) {
                    System.out.println(questions.get(j).getQuestion());
                }
            }
            mysql.getAnswerInfo();
        }







        //FTP ftp = new FTP();
        //ftp.sendPicToFTP();

        //pt. ingen forskel på store og små bogstaver
        //System.out.println(mysql.userIsLoggedIn("david", "123"));




       /* new Thread(new Runnable() {
            public void run() {
                boolean status = false;
                // host – your FTP address
                // username & password – for your secured login
                // 21 default gateway for FTP
                String host = "linux309.unoeuro.com";
                String username = "danielguldberg.dk";
                String password = "280781";
                int port = 21;

                status = ftpclient.ftpConnect(host, username, password, port);
                if (status == true) {
                    Log.d(TAG, "Connection Success");

                    String state = Environment.getExternalStorageState();
                    if (Environment.MEDIA_MOUNTED.equals(state)) {
                        //if (Build.VERSION.SDK_INT >= 23) {

                        //File sdcard = Environment.getExternalStorageDirectory();
                        File sdcard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                        File dir = new File(sdcard.getAbsolutePath() + "/text/");
                        dir.mkdir();
                        System.out.println(dir.toString());
                        File file = new File(dir, "sample.txt");
                        System.out.println("created file on storage path " + file.getAbsolutePath());
                        String fileContent = "test";
                        FileOutputStream os;
                        try {
                            os = new FileOutputStream(file);
                            System.out.println("created file output stream");
                            os.write(fileContent.getBytes());
                            os.close();
                            System.out.println("wrote the file to disk");
                        } catch (IOException e) {
                            System.out.println("error writing the file to disk");
                            e.printStackTrace();
                        }

                        //}
                    }

                    ftpclient.ftpUpload(
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        + "/text/" + TEMP_FILENAME, TEMP_FILENAME, "/", cntx);
                } else {
                    Log.d(TAG, "Connection failed");
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                ftpclient.ftpDisconnect();
            }
        }).start();*/

    }

    public void goToDocument(View view) {
        Intent intent = new Intent(this, documentActivity.class);
        startActivity(intent);
    }
}