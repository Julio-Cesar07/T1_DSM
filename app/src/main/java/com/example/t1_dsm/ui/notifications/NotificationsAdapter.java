package com.example.t1_dsm.ui.notifications;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t1_dsm.R;
import com.example.t1_dsm.model.Notifications;
import com.example.t1_dsm.model.Post;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

// ListAdapter is more convenient to handle a list of items that may change.
// https://github.com/codepath/android_guides/wiki/Using-the-RecyclerView#using-with-listadapter
// https://developer.android.com/reference/androidx/recyclerview/widget/ListAdapter
public class NotificationsAdapter extends ListAdapter<Notifications, NotificationsAdapter.ViewHolder> {

    private ItemClickListener mClickListener;
    private ItemLongClickListener mLongClickListener;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public NotificationsAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.recyclerview_notification, parent, false);
        Log.d("not", "ola");
        return new NotificationsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<String> label = new ArrayList<>();
        //getItem(position).getText(); //aqui ele coloca as informações no fragment
        label.add(getItem(position).getSolicitante());
        label.add(getItem(position).getText());
        label.add(getItem(position).getGame());

        holder.viewSolicitante.setText(label.get(0));
        holder.viewGame.setText(label.get(1));
        holder.viewText.setText(label.get(2));


    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView viewSolicitante; //aqui ele pega os componentes
        TextView viewText;
        TextView viewGame;

        ViewHolder(View itemView) {
            super(itemView);
            viewSolicitante = itemView.findViewById(R.id.emailSoli2);
            viewText = itemView.findViewById(R.id.textNot2);
            viewGame = itemView.findViewById(R.id.gameNoti2);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            Log.d("hmmm", "clickou" + view);
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

    public static final DiffUtil.ItemCallback<Notifications> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Notifications>() {
                // it decides if two objects are equal
                @Override
                public boolean areItemsTheSame(Notifications oldItem, Notifications newItem) {
                    return oldItem.getText().equals(newItem.getText());
                }
                // it decides if the object needs to redraw
                @Override
                public boolean areContentsTheSame(Notifications oldItem, Notifications newItem) {
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
