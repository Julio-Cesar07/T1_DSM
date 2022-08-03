package com.example.t1_dsm.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.t1_dsm.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FormSignup extends AppCompatActivity {

    private EditText edit_name, edit_email, edit_pass;
    private Button btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_signup);

        StartComponents();

        btn_signup.setOnClickListener(view -> {
            String name = edit_name.getText().toString();
            String email = edit_email.getText().toString();
            String pass = edit_pass.getText().toString();

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty()){
                // TOAST Version
                /*Toast.makeText(getApplicationContext(), R.string.error_all_camps, Toast.LENGTH_LONG).show();*/

                //SNACKBAR Version (Estilo configurado no styles.xml)
                Snackbar.make(view, R.string.error_all_camps, Snackbar.LENGTH_LONG).show();
            } else {
                SignupUser(view);
            }
        });
    }

    private void SignupUser(View view){
        String email = edit_email.getText().toString();
        String pass = edit_pass.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()){

                SaveDataDB();

                Log.d("signup", "Cadastro " + email + " realizado com Sucesso!");
                Snackbar.make(view, R.string.success_signup, Snackbar.LENGTH_LONG).show();
            } else {
                try {
                    throw task.getException();
                } catch (FirebaseAuthWeakPasswordException e) {
                    Snackbar.make(view, R.string.error_weak_password, Snackbar.LENGTH_LONG).show();
                } catch (FirebaseAuthInvalidCredentialsException e) {
                    Snackbar.make(view, R.string.error_invalid_email, Snackbar.LENGTH_LONG).show();
                } catch (FirebaseAuthUserCollisionException e) {
                    Snackbar.make(view, R.string.error_duplicate_email, Snackbar.LENGTH_LONG).show();
                } catch (Exception e) {
                    Log.d("signup_error", "Erro ao realizar cadastro!\nVerificar: " + e);
                    Snackbar.make(view, R.string.error_unknown, Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void SaveDataDB(){
        String name = edit_name.getText().toString();

        // Inicializando Banco de Dados
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Criando a entrada no BD
        Map<String, Object> usuarios = new HashMap<>();
        usuarios.put("name", name);

        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("signup_db", "Usuário " + name + " cadastrado no Banco de Dados");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("signup_db_error", "Falha ao salvar os dados no Banco de Dados: " + e.toString());
            }
        });
    }

    private void StartComponents(){
        edit_name = findViewById(R.id.textName);
        edit_email = findViewById(R.id.textEmail);
        edit_pass = findViewById(R.id.textPassword);
        btn_signup = findViewById(R.id.buttonSignup);
    }
}