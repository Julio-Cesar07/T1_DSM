package com.example.t1_dsm.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.t1_dsm.R;

public class FormRememberPass extends AppCompatActivity {

    private ImageView img_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_remember_pass);

        StartComponents();

        img_back.setOnClickListener(view -> {
            finish();
        });
    }

    private void StartComponents(){
        img_back = findViewById(R.id.back_arrow);
    }

}