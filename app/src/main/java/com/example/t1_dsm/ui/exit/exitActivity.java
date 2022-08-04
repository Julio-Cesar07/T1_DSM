package com.example.t1_dsm.ui.exit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.t1_dsm.R;
import com.example.t1_dsm.ui.login.FormLogin;
import com.example.t1_dsm.ui.login.FormProfile;
import com.google.firebase.auth.FirebaseAuth;

public class exitActivity extends AppCompatActivity {

    private ImageView img_back;
    private Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);

        img_back = findViewById(R.id.back_arrow2);
        btn_logout = findViewById(R.id.btn_exit);

        btn_logout.setOnClickListener(view -> {

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(exitActivity.this, FormLogin.class);
                startActivity(intent);
                finish();

            });

        img_back.setOnClickListener(view -> {
            finish();
        });
    }
}