package com.example.t1_dsm.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.t1_dsm.R;
import com.example.t1_dsm.ui.home.HomeActivity;

public class FormLogin extends AppCompatActivity {

    private TextView text_screen_signup;
    private TextView text_remember_pass;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);

        StartComponents();

        text_screen_signup.setOnClickListener(view -> {
            Intent intent = new Intent(FormLogin.this, FormSignup.class);
            startActivity(intent);
        });

        text_remember_pass.setOnClickListener(view -> {
            Intent intent = new Intent(FormLogin.this, FormRememberPass.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(view -> {
            Intent intent = new Intent(FormLogin.this, HomeActivity.class);
            startActivity(intent);
        });
    }

    private void StartComponents(){
        text_screen_signup = findViewById(R.id.linkSignup);
        text_remember_pass = findViewById(R.id.linkRememberPass);
        btnLogin = findViewById(R.id.buttonLogin);
    }
}