package com.example.t1_dsm.ui.home;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.t1_dsm.databinding.FragmentHomeBinding;
import com.example.t1_dsm.model.Post;
import com.example.t1_dsm.ui.HomeViewModel;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.taskListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.taskListRecyclerView.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        PostsAdapter postsAdapter = new PostsAdapter();

        binding.taskListRecyclerView.setAdapter(postsAdapter);
        postsAdapter.setClickListener((view, position) -> showDialog(position)); //abrir o post

        homeViewModel.getPosts().observe(getViewLifecycleOwner(), postsAdapter::submitList);

        return root;
    }

    public void showDialog(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeFragment.this.getContext());
        Post tPost = homeViewModel.getPost(position);
        String tMsg = "Name: " + tPost.getText();
        builder.setTitle("Aceitar?");
        builder.setMessage(tMsg);
        builder.setPositiveButton("Yes", (dialog, which) -> homeViewModel.createNotification(tPost));
        builder.setNegativeButton("No", null);
        builder.create().show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}