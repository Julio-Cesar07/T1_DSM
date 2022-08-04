package com.example.t1_dsm.ui.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t1_dsm.R;
import com.example.t1_dsm.model.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// ListAdapter is more convenient to handle a list of items that may change.
// https://github.com/codepath/android_guides/wiki/Using-the-RecyclerView#using-with-listadapter
// https://developer.android.com/reference/androidx/recyclerview/widget/ListAdapter
public class PostsAdapter extends ListAdapter<Post, PostsAdapter.ViewHolder> {

    private ItemClickListener mClickListener;
    private ItemLongClickListener mLongClickListener;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public PostsAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new PostsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<String> label = new ArrayList<>();
        //getItem(position).getText(); //aqui ele coloca as informações no fragment
        label.add(getItem(position).getIdProp());
        label.add(getItem(position).getText());
        label.add(getItem(position).getGame());
        label.add(getItem(position).getVacancies());
        label.add(getItem(position).getRank());

        holder.viewPostName.setText(label.get(0));
        holder.viewPostDescription.setText(label.get(1));
        holder.viewPostGame.setText(label.get(2));
        holder.viewPostVagancies.setText("Vagas: "+label.get(3));
        holder.viewPostRank.setText("Rank: "+label.get(4));


    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView viewPostName; //aqui ele pega os componentes
        TextView viewPostDescription;
        TextView viewPostGame;
        TextView viewPostVagancies;
        TextView viewPostRank;

        ViewHolder(View itemView) {
            super(itemView);
            viewPostName = itemView.findViewById(R.id.itemName);
            viewPostDescription = itemView.findViewById(R.id.itemDescription);
            viewPostGame = itemView.findViewById(R.id.itemGame);
            viewPostVagancies = itemView.findViewById(R.id.itemVagancies);
            viewPostRank = itemView.findViewById(R.id.itemRank);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            if (mLongClickListener != null) {
                mLongClickListener.onItemLongClick(view, getAdapterPosition());
                return true;
            }
            return false;
        }
    }

    public static final DiffUtil.ItemCallback<Post> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Post>() {
                // it decides if two objects are equal
                @Override
                public boolean areItemsTheSame(Post oldItem, Post newItem) {
                    return oldItem.getText().equals(newItem.getText());
                }
                // it decides if the object needs to redraw
                @Override
                public boolean areContentsTheSame(Post oldItem, Post newItem) {
                    return (oldItem.getText().equals(newItem.getText()) &&
                            oldItem.getGame().equals(newItem.getGame()));
                }
            };

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // allows long clicks events
    void setLongClickListener(ItemLongClickListener itemLongClickListener) {
        this.mLongClickListener = itemLongClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface ItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
}
