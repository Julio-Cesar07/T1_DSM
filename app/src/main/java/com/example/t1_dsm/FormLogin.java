package com.example.t1_dsm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class FormLogin extends AppCompatActivity {

    private TextView text_screen_signup;
    private TextView text_remember_pass;

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
    }

    private void StartComponents(){
        text_screen_signup = findViewById(R.id.linkSignup);
        text_remember_pass = findViewById(R.id.linkRememberPass);
    }
}