package com.example.t1_dsm.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.t1_dsm.R;
import com.example.t1_dsm.ui.home.HomeActivity;
import com.example.t1_dsm.ui.home.HomeViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

import java.util.Locale;

public class FormLogin extends AppCompatActivity {

    private TextView text_screen_signup, text_remember_pass;
    private EditText edit_email, edit_pass;
    private ImageView img_language;
    private Button btn_login;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Carregar idioma salvo
        Locale currentLocale = LoadSharedPreferences();

        Locale.setDefault(currentLocale);
        Resources resources = this.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = currentLocale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

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

        img_language.setOnClickListener(view -> {
            //Locale currentLocale = Locale.getDefault();
            Locale selectedLocale;

            Locale en_us = new Locale("en", "US");
            Locale pt_br = new Locale("pt", "BR");

            // Lógica booleana, para apenas duas linguagens
            if (currentLocale.equals(en_us)){
                selectedLocale = (Locale)pt_br.clone();
            } else {
                selectedLocale = (Locale)en_us.clone();
            }

            // Alterar o padrão da localização
            Locale.setDefault(selectedLocale);
            Resources resourcesEdit = this.getResources();
            Configuration configurationEdit = resourcesEdit.getConfiguration();
            configurationEdit.locale = selectedLocale;
            resourcesEdit.updateConfiguration(configurationEdit, resourcesEdit.getDisplayMetrics());

            // Salvar as opções em SharedPreferences
            SharedPreferences preferences = getSharedPreferences("localeFile", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("localeLanguage", selectedLocale.getLanguage());
            editor.putString("localeCountry", selectedLocale.getCountry());
            editor.commit();

            // Aplicar as Configs
            recreate();

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

                    new Handler().postDelayed(() -> HomeScreen(), 1500);
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
        img_language = findViewById(R.id.language);
        text_screen_signup = findViewById(R.id.linkSignup);
        text_remember_pass = findViewById(R.id.linkRememberPass);
        progressBar = findViewById(R.id.progressBar);
    }

    private Locale LoadSharedPreferences(){
        SharedPreferences preferences = getSharedPreferences("localeFile", MODE_PRIVATE);
        return new Locale(preferences.getString("localeLanguage", ""),
                preferences.getString("localeCountry", ""));
    }
}