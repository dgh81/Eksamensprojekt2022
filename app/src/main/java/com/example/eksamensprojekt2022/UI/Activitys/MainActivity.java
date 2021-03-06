package com.example.eksamensprojekt2022.UI.Activitys;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.eksamensprojekt2022.DBController.MySQL;
import com.example.eksamensprojekt2022.Enteties.EmailThread;
import com.example.eksamensprojekt2022.Enteties.User;
import com.example.eksamensprojekt2022.R;
import com.example.eksamensprojekt2022.Tools.CreatePDF;
import com.example.eksamensprojekt2022.Tools.LoginAuthentication;
import com.example.eksamensprojekt2022.Tools.PostNumberToCity;
import com.example.eksamensprojekt2022.UI.Login.LoginActivity;
import com.example.eksamensprojekt2022.UI.Login.LoginSaveData;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity {
    Button idBtnGeneratePDF;
    Button testBtn;
    public static Uri pdfUri;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);*/

        PostNumberToCity.createPostCodeHasMap();

        LoginSaveData.getInstance().context = this;

        julieTest();
        idBtnGeneratePDF = findViewById(R.id.idBtnGeneratePDF);

        idBtnGeneratePDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CreatePDF pdf = new CreatePDF(MainActivity.this , 28 );
                    pdf.createPdf(MainActivity.this , 28);
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
       // try {
         //   mysqlConnection.join();
       // } catch (InterruptedException e) {
         //   e.printStackTrace();
       // }

        if (mysql.connection == null ) {
            System.out.println("Start connection is null");
            //TODO: Show that there is no connection to the server
            // TODO: Go to login page, insert login info into the text fields and disable the login button, and add a refresh button
        }

        if (!LoginAuthentication.attemptLoginWithSavedLoginData() ) {
            Intent intent = new Intent( MainActivity.this,  LoginActivity.class);

            finish();
            startActivity(intent);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack();


        } else {




            Intent intent = new Intent( MainActivity.this,  SelectDocumentAndRoomActivityActivity.class);
            finish();
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

        /*testBtn = findViewById(R.id.btnTest);

        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Picture> pics = new ArrayList<>();
                pics = mysql.getPicturesFromProjectInformationID()
            }
        });*/

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

    //TODO Flyt til UserCase
    public void userSendsPdfAsEmail(View view) {
        //view.getContext()
        EmailThread emailIntent = new EmailThread(MainActivity.this);
        emailIntent.start();
    }
}

