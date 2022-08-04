package com.example.t1_dsm.ui.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.t1_dsm.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class FormProfile extends AppCompatActivity {

    private TextView text_name, text_email;
    private Button btn_logout;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String userID, email;
    private ImageView img_back, img_edit_name, img_edit_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_profile);


        StartComponents();

        btn_logout.setOnClickListener(view -> {
            // Aviso antes de deslogar
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FormProfile.this);
            alertDialogBuilder.setTitle(R.string.logout);
            alertDialogBuilder.setMessage(R.string.logout_confirmation);
            alertDialogBuilder.setCancelable(false);

            // Opção SIM
            alertDialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Deslogar FireBase
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(FormProfile.this, FormLogin.class);
                    startActivity(intent);
                    finish();
                }
            });

            // Opção NÃO
            alertDialogBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog dialog = alertDialogBuilder.create();
            dialog.show();
        });

        img_back.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        DocumentReference documentReference = db.collection("Usuarios").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null){
                    text_name.setText(documentSnapshot.getString("name"));
                    text_email.setText(email);
                }
            }
        });
    }

    private void StartComponents(){
        text_name = findViewById(R.id.textProfileName);
        text_email = findViewById(R.id.textProfileEmail);
        btn_logout = findViewById(R.id.buttonLogout);
        img_back = findViewById(R.id.back_arrow);

        img_edit_name = findViewById(R.id.imgEditName);
        img_edit_email = findViewById(R.id.imgEditEmail);
    }
}