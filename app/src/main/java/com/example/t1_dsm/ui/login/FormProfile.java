package com.example.t1_dsm.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.t1_dsm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class FormProfile extends AppCompatActivity {

    private TextView text_name, text_email;
    private Button btn_logout, btn_change_pass;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String userID, email;
    private EditText old_pass, new_pass;
    private ImageView img_back, img_edit_name;

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

        img_edit_name.setOnClickListener(view -> {
            userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

            AlertDialog.Builder changeName = new AlertDialog.Builder(FormProfile.this);
            changeName.setTitle(R.string.change_name);
            changeName.setMessage(R.string.change_name_confirmation);
            changeName.setCancelable(true);

            final EditText changeNameInput = new EditText(FormProfile.this);
            //changeNameInput.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
            changeName.setView(changeNameInput);

            changeName.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    db.collection("Usuarios").document(userID)
                            .update("name", changeNameInput.getText().toString());
                    recreate();
                }
            });

            changeName.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog dialog = changeName.create();
            dialog.show();
        });

        btn_change_pass.setOnClickListener(view -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), old_pass.getText().toString());

            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updatePassword(new_pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Snackbar.make(view, R.string.sucess_change_pass, Snackbar.LENGTH_SHORT).show();
                                            FirebaseAuth.getInstance().signOut();
                                            Intent intent = new Intent(FormProfile.this, FormLogin.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Snackbar.make(view, R.string.failed_change_pass, Snackbar.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Snackbar.make(view, R.string.failed_change_pass, Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
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

        old_pass = findViewById(R.id.old_pass);
        new_pass = findViewById(R.id.new_pass);
        btn_change_pass = findViewById(R.id.buttonChangePass);
    }
}