package com.example.myapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.commentAdapter;
import com.example.myapplication.data_model.MonAn;
import com.example.myapplication.data_model.comment;
import com.example.myapplication.data_model.user;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class activity_rieng1_comment extends AppCompatActivity {

    RecyclerView comment_view;
    EditText my_cmt;
    FloatingActionButton up_button;
    MonAn monAn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rieng1_comment);

        comment_view=(RecyclerView) findViewById(R.id.comment_view);
        my_cmt=(EditText) findViewById(R.id.mycomment);
        up_button=(FloatingActionButton) findViewById(R.id.upcomment);
        monAn= (MonAn) getIntent().getSerializableExtra("mon_an");
        int position= (int) getIntent().getSerializableExtra("position");
        DatabaseReference db= FirebaseDatabase.getInstance().getReference();
        user user= (com.example.myapplication.data_model.user) getIntent().getSerializableExtra("user");

        db.child("mon_an").child(String.valueOf(position)).child("comment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<comment> list_comment = new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    list_comment.add(postSnapshot.getValue(comment.class));
                }
                db.child("mon_an").child(String.valueOf(position)).child("comment").removeEventListener(this);
                if(list_comment.size()>0)
                {
                    commentAdapter commentAdapter=new commentAdapter(activity_rieng1_comment.this,list_comment);
                    comment_view.setAdapter(commentAdapter);
                    comment_view.setLayoutManager(new LinearLayoutManager(activity_rieng1_comment.this));
                }
                up_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        comment mycmt=new comment(user.getUsername(),my_cmt.getText().toString());
                        list_comment.add(mycmt);
                        db.child("mon_an").child(String.valueOf(position)).child("comment").setValue(list_comment);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}