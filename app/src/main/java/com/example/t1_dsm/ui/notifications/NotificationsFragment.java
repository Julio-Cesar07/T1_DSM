package com.example.t1_dsm.ui.notifications;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.t1_dsm.R;
import com.example.t1_dsm.databinding.FragmentNotificationsBinding;
import com.example.t1_dsm.databinding.FragmentPostBinding;
import com.example.t1_dsm.ui.HomeViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class NotificationsFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FragmentNotificationsBinding binding;
    private HomeViewModel homeViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        binding.taskListRecyclerViewNotification.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.taskListRecyclerViewNotification.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        NotificationsAdapter notificationsAdapter = new NotificationsAdapter();

        binding.taskListRecyclerViewNotification.setAdapter(notificationsAdapter);

        homeViewModel.getNotificationsByEmail(userEmail).observe(getViewLifecycleOwner(), notificationsAdapter::submitList);



        return root;
    }
}