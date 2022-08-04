package com.example.t1_dsm.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.t1_dsm.R;
import com.example.t1_dsm.ui.home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

public class FormLogin extends AppCompatActivity {

    private TextView text_screen_signup, text_remember_pass;
    private EditText edit_email, edit_pass;
    private Button btn_login;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);

        StartComponents();

        btn_login.setOnClickListener(view -> {
            String email = edit_email.getText().toString();
            String pass = edit_pass.getText().toString();

            if (email.isEmpty() || pass.isEmpty()) {
                Snackbar.make(view, R.string.error_all_camps, Snackbar.LENGTH_LONG).show();
            } else {
                AuthUser(view);
            }
        });

        text_screen_signup.setOnClickListener(view -> {
            Intent intent = new Intent(FormLogin.this, FormSignup.class);
            startActivity(intent);
        });

        text_remember_pass.setOnClickListener(view -> {
            Intent intent = new Intent(FormLogin.this, FormRememberPass.class);
            startActivity(intent);
        });

    }

    private void AuthUser(View view){
        String email = edit_email.getText().toString();
        String pass = edit_pass.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressBar.setVisibility(view.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            HomeScreen();
                        }
                    }, 1500);
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        Snackbar.make(view, R.string.error_invalid_email_pass, Snackbar.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Log.d("signup_error", "Erro ao realizar cadastro!\nVerificar: " + e);
                        Snackbar.make(view, R.string.error_unknown, Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void HomeScreen(){
        Intent intent = new Intent(FormLogin.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void StartComponents(){
        edit_email = findViewById(R.id.textEmail);
        edit_pass = findViewById(R.id.textPassword);
        btn_login = findViewById(R.id.buttonLogin);
        text_screen_signup = findViewById(R.id.linkSignup);
        text_remember_pass = findViewById(R.id.linkRememberPass);
        progressBar = findViewById(R.id.progressBar);
    }
}