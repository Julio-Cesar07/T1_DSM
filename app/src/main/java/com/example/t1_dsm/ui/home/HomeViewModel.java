package com.example.t1_dsm.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.t1_dsm.model.Post;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private MutableLiveData<List<Post>> post;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home 2 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Post>> getPosts() {
        if(post == null){ //iniciar valores padrões
            post = new MutableLiveData<List<Post>>();
            post.setValue(Arrays.asList(
                    new Post("Olá, estou convidando o pessoal para jogar Rocket League.", "Rocket League",
                            "Champion II", 0, 2),
                    new Post("Oie, estou convidando alguém para jogar It takes two comigo.", "It takes two",
                            "Não tem", 0, 1),
                    new Post("Oie, estou convidando alguém para jogar League of Legends.", "League of Legends",
                            "Qualquer", 0, 1)
            ));
        }
        return post;
    }

    public void createPost(String text, String game, String rank, Integer idProp, Integer vacancies){
        List<Post> newListPost = new ArrayList<Post>(post.getValue());
        newListPost.add(new Post(text, game, rank, idProp, vacancies));
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