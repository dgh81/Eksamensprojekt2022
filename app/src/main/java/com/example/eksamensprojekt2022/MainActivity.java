package com.example.eksamensprojekt2022;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.eksamensprojekt2022.Objeckts.FileHandler;
import com.example.eksamensprojekt2022.Objeckts.InspectionInformation;
import com.example.eksamensprojekt2022.Objeckts.Question;
import com.example.eksamensprojekt2022.Objeckts.User;
import com.example.eksamensprojekt2022.PostCode.PostNumberToCity;
import com.example.eksamensprojekt2022.ui.Document.SelectDocumentAndRoomActivityActivity;
import com.example.eksamensprojekt2022.ui.Login.LoginActivity;
import com.example.eksamensprojekt2022.ui.Login.LoginSaveData;
import com.itextpdf.kernel.xmp.impl.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Intent.EXTRA_STREAM;
import static android.os.Environment.getExternalStoragePublicDirectory;
//import static com.example.eksamensprojekt2022.CreatePDF.pdfUri;
import static com.example.eksamensprojekt2022.CreatePDF.pdfFile;
import static com.example.eksamensprojekt2022.Objeckts.FileHandler.currentImagePath;
import static com.example.eksamensprojekt2022.ui.Document.PicFragment.picFragmentImageView;

import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button idBtnGeneratePDF;

    //Camera
    ImageView imageViewThumbnail;
    ImageView fragmentImageView;
    Bitmap bitmapFromTempFile;
    public static int CAMERA_CODE = 100;
    Intent cameraIntent;

    Uri imageUri;
    public static Uri pdfUri;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);*/



        LoginSaveData.getInstance().context = this;

        julieTest();
        idBtnGeneratePDF = findViewById(R.id.idBtnGeneratePDF);

        idBtnGeneratePDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CreatePDF pdf = new CreatePDF();
                    pdf.createPdf(MainActivity.this);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        MySQL mysql = new MySQL();
        Thread mysqlConnection = new Thread(mysql);
        mysqlConnection.run();
        try {
            mysqlConnection.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (mysql.connection == null ) {
            System.out.println("Start connection is null");
            //TODO: Show that there is no connection to the server
            // TODO: Go to login page, insert login info into the text fields and disable the login button, and add a refresh button
        }

        if (!LoginAuthentication.attemptLoginWithSavedLoginData() ) {
            Intent intent = new Intent( MainActivity.this,  LoginActivity.class);
            startActivity(intent);
        } else {

            Intent intent = new Intent( MainActivity.this,  SelectDocumentAndRoomActivityActivity.class);
            startActivity(intent);

        }

        Toolbar myToolbar =  findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();

        assert ab != null;
        ab.setTitle("\t Zealand");

        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

         if (getParentActivityIntent() != null)
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(true);
        ab.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yellow)));

        //TODO CAMERA:
        imageViewThumbnail = findViewById(R.id.imageViewThumbnail);
    }

    public void takePictureButtonClick(View view) {
        try {
            cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            File imageFile = null;
            try{
                imageFile= new FileHandler().getImageFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            if(imageFile!=null){
                imageUri = FileProvider.getUriForFile(MainActivity.this,"com.example.eksamensprojekt2022.fileprovider",imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(cameraIntent, CAMERA_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void savePictureButtonClick(View view) {
        String encodedImageString = new FileHandler().bitmapEncodeToBaseString(bitmapFromTempFile);
        MySQL mysql = new MySQL();
        mysql.createBitmapString(encodedImageString, 1, "sagnr + rumnavn + username: + comment?");
    }


    //Camera listener:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CODE && resultCode == RESULT_OK) {
            File imgFile = new File(currentImagePath);
            String path = imgFile.getAbsolutePath();
            bitmapFromTempFile = BitmapFactory.decodeFile(path);
            fragmentImageView = picFragmentImageView;
            fragmentImageView.setImageBitmap(bitmapFromTempFile);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu ,menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)  {

        return true;
    }

    public void julieTest() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        System.out.println("Klik");

        MySQL mysql = new MySQL();
        Thread mysqlConnection = new Thread(mysql);
        mysqlConnection.run();
        try {
            mysqlConnection.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        User loggedInUser = mysql.logUserIn("Julie", "123");
    }
    //Test FTP
    //ftpclient = new MyFTPClientFunctions();


    // TEST Login to database



    public void test(View view) throws InterruptedException, SQLException, IOException {


        Thread thread = new Thread("My thread") {

            @Override
            public void run() {
                System.out.println("run by: " + getName());
                Uri pdfUri = FileProvider.getUriForFile(MainActivity.this,"com.example.eksamensprojekt2022.fileprovider",pdfFile);
                //pdfUri = Uri.fromFile(pdfFile);

                final Intent emailIntent = new Intent( Intent.ACTION_SEND);

                List<ResolveInfo> resInfoList = MainActivity.this.getPackageManager().queryIntentActivities(emailIntent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    MainActivity.this.grantUriPermission(packageName, pdfUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }

                emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                emailIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                //emailIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);

                emailIntent.setType("plain/text");

                emailIntent.putExtra(Intent.EXTRA_EMAIL,
                        new String[] { "danielguldberg@gmail.com" });

                emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                        "Email Subject");

                emailIntent.putExtra(Intent.EXTRA_TEXT,
                        "Email Body");

                emailIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                System.out.println("pdfUri: " + pdfUri);
                emailIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);

                emailIntent.setDataAndType(pdfUri, getContentResolver().getType(pdfUri));

                startActivity(Intent.createChooser(
                        emailIntent, "Send mail..."));

            }
        };

        thread.start();

        thread.join();




    }

        //sendEmail();


        //new FileHandler().getPDFFile()



        /*final Intent emailIntent = new Intent( android.content.Intent.ACTION_SEND);

        emailIntent.setType("plain/text");

        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[] { "abc@gmail.com" });

        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                "Email Subject");

        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                "Email Body");

        //emailIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        emailIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

        startActivity(Intent.createChooser(
                emailIntent, "Send mail..."));*/
        /*
        Thread t = new Thread(mysql);
        // t.run(): kør den ene tråd først
        // t.start(): kør tråde synkront
        t.start();
        // join venter på threads er færdige inden resten af koden køres
        t.join();*/

        //Skal køres hver gang en klasse kalder på runnable MySQL:
/*        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
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
        }*/
}

