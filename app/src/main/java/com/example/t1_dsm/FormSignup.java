package com.example.t1_dsm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FormSignup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_signup);

        getSupportActionBar().hide();
    }
}