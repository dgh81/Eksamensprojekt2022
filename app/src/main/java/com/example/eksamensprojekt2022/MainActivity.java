package com.example.eksamensprojekt2022;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import android.content.Intent;
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

import com.example.eksamensprojekt2022.Objeckts.Question;
import com.example.eksamensprojekt2022.Objeckts.User;
import com.example.eksamensprojekt2022.ui.Document.SelectDocumentAndRoomActivityActivity;
import com.example.eksamensprojekt2022.ui.Login.LoginActivity;
import com.example.eksamensprojekt2022.ui.Login.LoginSaveData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;
import static com.example.eksamensprojekt2022.ui.Document.PicFragment.picFragmentImageView;

import android.util.Base64;
import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    Button idBtnGeneratePDF;

    //Camera
    ImageView imageViewThumbnail;
    ImageView fragmentImageView;
    Bitmap bitmapFromTempFile;
    public static int CAMERA_CODE = 100;
    Intent cameraIntent;
    String currentImagePath;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

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
                imageFile=getImageFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            if(imageFile!=null){
                Uri imageUri = FileProvider.getUriForFile(MainActivity.this,"com.example.eksamensprojekt2022.fileprovider",imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(cameraIntent, CAMERA_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void savePictureButtonClick(View view) {
        String encodedImageString = bitmapEncodeToBaseString(bitmapFromTempFile);
        MySQL mysql = new MySQL();
        mysql.createBitmapString(encodedImageString, 1, "sagnr + rumnavn + username: + comment?");
    }

    public File getImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "jpg_"+timeStamp+"_";
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(imageName,".jpg",storageDir);
        currentImagePath = imageFile.getAbsolutePath();
        return imageFile;
    }
    public String bitmapEncodeToBaseString(Bitmap bitmapToDecode) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //OBS 50% quality giver ca 10 gange mindre data i db!!:
        bitmapToDecode.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encodedImageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encodedImageString;
    }
    public Bitmap bitmapDecodeFromBaseString(String encodedImageString) {
        byte[] decodedString = Base64.decode(encodedImageString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return  decodedByte;
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
    }
}