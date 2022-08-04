package com.example.t1_dsm.ui;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.t1_dsm.model.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private MutableLiveData<List<Post>> post;
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
                                document.getString("name"), document.getString("vacancies"));
                    }
                } else {
                    Log.d("ve isso", "Error getting documents: ", task.getException());
                }
            }
        });
//        if(post == null){ //iniciar valores padrões
//            ,
//                    new Post("Oie, estou convidando alguém para jogar It takes two comigo.", "It takes two",
//                            "Não tem", "0", "1"),
//                    new Post("Oie, estou convidando alguém para jogar League of Legends.", "League of Legends",
//                            "Qualquer", "0", "1")
//            ));
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



}