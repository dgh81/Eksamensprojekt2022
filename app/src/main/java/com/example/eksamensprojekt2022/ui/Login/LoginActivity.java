package com.example.eksamensprojekt2022.ui.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eksamensprojekt2022.LoginAuthentication;
import com.example.eksamensprojekt2022.MainActivity;
import com.example.eksamensprojekt2022.R;
import com.example.eksamensprojekt2022.ui.Document.SelectDocumentAndRoomActivityActivity;

import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);

        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean loginSuccess = false;
                try {
                    loginSuccess = LoginAuthentication.login(username.getText().toString() , password.getText().toString());
                    System.out.println(LoginAuthentication.login(username.getText().toString() , password.getText().toString()));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                if (loginSuccess) {
                    Intent intent = new Intent(  LoginActivity.this, SelectDocumentAndRoomActivityActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(LoginActivity.this, "Forkert brugernavn eller adgangskode", Toast.LENGTH_LONG).show();
                }

            }
        });





    }


}