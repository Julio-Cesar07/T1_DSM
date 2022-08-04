package com.example.t1_dsm.ui.post;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.t1_dsm.databinding.FragmentPostBinding;
import com.example.t1_dsm.ui.HomeViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class PostFragment extends Fragment {

    private FragmentPostBinding binding;
    private EditText edit_post;
    private EditText edit_game;
    private EditText edit_rank;
    private EditText edit_vagancies;
    private Button btn_publish;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userId;
    String userEmail;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        startComponents();

        HomeViewModel homeViewModel =
                new ViewModelProvider(requireActivity()).get(HomeViewModel.class);


        btn_publish.setOnClickListener(view -> {
            Map<String, Object> post = new HashMap<>();
            String textPost = edit_post.getText().toString();
            String textGame = edit_game.getText().toString();
            String textRank = edit_rank.getText().toString();
            String textVacancies = edit_vagancies.getText().toString();


            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

            post.put("id", userId);
            post.put("email", userEmail);
            post.put("text", textPost);
            post.put("game", textGame);
            post.put("rank", textRank);
            post.put("vacancies", textVacancies);

            DocumentReference documentReference = db.collection("post").document();
            documentReference.set(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d("signup_db", "Post " + homeViewModel.getPost(0) + " cadastrado no Banco de Dados");
                    homeViewModel.createPost(textPost, textGame, textRank, userEmail, textVacancies);
                    getActivity().onBackPressed();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("signup_db_error", "Falha ao salvar os dados no Banco de Dados: " + e.toString());
                }
            });

        });

        return root;

    }

    public void startComponents(){
        edit_post = binding.publishPost;
        edit_game = binding.publishGame;
        edit_rank = binding.publishRank;
        edit_vagancies = binding.publishVagas;
        btn_publish = binding.btnPublish;
    }
}