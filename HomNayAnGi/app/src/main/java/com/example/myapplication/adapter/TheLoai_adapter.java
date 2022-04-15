package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.data_model.TheLoai;

import java.util.List;

public class TheLoai_adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<TheLoai> theloai_list;

    public TheLoai_adapter(Context context, int layout, List<TheLoai> foodlist) {
        this.context = context;
        this.layout = layout;
        this.theloai_list = foodlist;
    }

    @Override
    public int getCount() {
        return theloai_list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        TextView name=(TextView) view.findViewById(R.id.name);
        ImageView img=(ImageView) view.findViewById(R.id.image);
        TheLoai theloai=theloai_list.get(i);
        name.setText(theloai.getName());
        img.setImageResource(theloai.getImg());
        return view;
    }
}
