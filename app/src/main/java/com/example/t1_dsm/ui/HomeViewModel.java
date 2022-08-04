package com.example.t1_dsm.ui;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.t1_dsm.model.Notifications;
import com.example.t1_dsm.model.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private MutableLiveData<List<Post>> post;
    private MutableLiveData<List<Notifications>> notification;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home 2 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Post>> getPosts() {
        if(post == null){
            post = new MutableLiveData<List<Post>>();
               post.setValue(Arrays.asList(
                        new Post("Olá, estou convidando o pessoal para jogar Rocket League.", "Rocket League",
                                "Champion II", "0", "2")));

        db.collection("post").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        createPost(document.getString("text"),
                                document.getString("game"), document.getString("rank"),
                                document.getString("email"), document.getString("vacancies"));
                    }
                } else {
                    Log.d("ve isso", "Error getting documents: ", task.getException());
                }
            }
        });

        }
        return post;
    }


    public void createPost(String text, String game, String rank, String idProp, String vacancies){
        List<Post> newListPost = new ArrayList<Post>(post.getValue());
        Collections.reverse(newListPost);
        newListPost.add(new Post(text, game, rank, idProp, vacancies));
        Collections.reverse(newListPost);
        post.setValue(newListPost);


    }

    public Post getPost(int ind){
        return post.getValue().get(ind);
    }

    public void removePost(int ind){
        List<Post> newListPost = new ArrayList<Post>(post.getValue());
        newListPost.remove(ind);
        post.setValue(newListPost);
    }

    public void createNotification(Post tPost){
        Map<String, Object> notification = new HashMap<>();
        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        notification.put("proprietario", tPost.getIdProp());
        notification.put("solicitante", userEmail);
        notification.put("texto_post", tPost.getText());
        notification.put("game", tPost.getGame());
        DocumentReference documentReference = db.collection("notifications").document();

        documentReference.set(notification).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("signup_db_error", "Falha ao salvar os dados no Banco de Dados: " + e.toString());
            }
        });



    }

    public LiveData<List<Notifications>> getNotificationsByEmail(String email){
        if(notification == null) {
            notification = new MutableLiveData<List<Notifications>>();
            notification.setValue(Arrays.asList(
                    new Notifications("Teste", "Teste",
                            "Teste", "Teste")));

            CollectionReference notRef = db.collection("notifications");
            Query query = notRef.whereEqualTo("proprietario", email);


            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("not", document.getString("proprietario") + " => " + document.getString("solicitante"));
                            addNotification(document.getString("texto_post"), document.getString("game"),
                                    document.getString("proprietario"), document.getString("solicitante"));

                        }
                    }
                }
            });
        }
        Log.d("not", " " + notification);
        return notification;
    }

    public void addNotification(String text, String game, String idProp, String solicitante){
        List<Notifications> newListNotifications = new ArrayList<Notifications>(notification.getValue());
        newListNotifications.add(new Notifications(text, game, idProp, solicitante));
        notification.setValue(newListNotifications);
    }



}