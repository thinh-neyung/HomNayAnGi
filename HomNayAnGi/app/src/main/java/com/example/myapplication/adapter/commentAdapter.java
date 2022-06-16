package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data_model.comment;

import java.util.ArrayList;

public class commentAdapter extends
        RecyclerView.Adapter<commentAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<comment> list_comment;

    public commentAdapter(Context mContext, ArrayList<comment> list_comment) {
        this.mContext = mContext;
        this.list_comment = list_comment;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView username;
        private TextView user_comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username_comment);
            user_comment = itemView.findViewById(R.id.user_comment);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View heroView = inflater.inflate(R.layout.comment_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(heroView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        comment comment=list_comment.get(position);
        holder.username.setText(comment.getUsername());
        holder.user_comment.setText(comment.getComment());
    }

    @Override
    public int getItemCount() {
        return list_comment.size();
    }
}
