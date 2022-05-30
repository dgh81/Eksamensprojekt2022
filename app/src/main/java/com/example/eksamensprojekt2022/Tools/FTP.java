package com.example.eksamensprojekt2022.Tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FTP extends AppCompatActivity {

    private MyFTPClientFunctions ftpclient = null;
    private static final String TAG = "MainActivity";
    private static final String TEMP_FILENAME = "sample.jpeg";
    private Context cntx = null;

    OutputStream outputStream;

    public FTP() {
        ftpclient = new MyFTPClientFunctions();
    }

    public void sendPicToFTP(Bitmap bitmap) {

        new Thread(new Runnable() {
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

                        //File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                        File filepath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                        File dir = new File(filepath.getAbsolutePath() + "/pics/");
                        dir.mkdir();
                        System.out.println(dir.toString());
                        File file = new File(dir, TEMP_FILENAME);
                        //System.out.println("created file on storage path " + file.getAbsolutePath());
                        try {
                            outputStream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                            //os.write(fileContent.getBytes());
                            //os.close();
                            //System.out.println("wrote the file to download folder");
                        } catch (IOException e) {
                            System.out.println("error writing the file to disk");
                            e.printStackTrace();
                        }

                        //Toast.makeText(getApplicationContext(), "Image saved to download folder.",Toast.LENGTH_LONG).show();

                        try {
                            outputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    ftpclient.ftpUpload(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                    + "/pics/" + TEMP_FILENAME, TEMP_FILENAME, "/", cntx);

                    System.out.println("created file: " + TEMP_FILENAME + " on " + " ftp:/" + "/linux309.unoeuro.com");
                } else {
                    Log.d(TAG, "Connection failed");
                }
            }
        }).start();
    }


    public void sendFileToFTP(File file) {

        new Thread(new Runnable() {
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

                    //Toast.makeText(getApplicationContext(), "Image saved to download folder.",Toast.LENGTH_LONG).show();

                    ftpclient.ftpUpload(file.getPath(), file.getName(), "/", cntx);

                    System.out.println("created file: " + file.getName() + " on " + " ftp:/" + "/linux309.unoeuro.com");
                } else {
                    Log.d(TAG, "Connection failed");
                }
            }
        }).start();
    }


/*        new Thread(new Runnable() {
        public void run() {
            ftpclient.ftpDisconnect();
        }
    }).start();*/

}

