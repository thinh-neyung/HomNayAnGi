package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data_model.MonAn;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class foodadapter extends RecyclerView.Adapter<foodadapter.ViewHolder> {
    private Context mContext;
    private ArrayList<MonAn> food_list;

    public foodadapter(Context mContext, ArrayList<MonAn> food_list) {
        this.mContext = mContext;
        this.food_list = food_list;
    }
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View foodView = inflater.inflate(R.layout.monanlistlayout, parent, false);
        ViewHolder viewHolder = new ViewHolder(foodView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        MonAn monan = food_list.get(position);
        Picasso.get().load(monan.getHinhanh()).into(holder.picture);
        holder.name.setText(monan.getTen());
        holder.level.setText(monan.getDokho());
        holder.time.setText(monan.getThoigian());
    }

    @Override
    public int getItemCount() {
        return food_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView picture;
        private TextView name;
        private TextView time;
        TextView level;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            picture = (ImageView) itemView.findViewById(R.id.picture);
            name =(TextView) itemView.findViewById(R.id.name);
            time=(TextView)itemView.findViewById(R.id.time);
            level=(TextView) itemView.findViewById(R.id.level);
        }
    }
    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

}
