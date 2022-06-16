package com.example.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.myapplication.R;
import com.example.myapplication.VNCharacterUtils;
import com.example.myapplication.data_model.MonAn;
import com.example.myapplication.data_model.Rate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Activity_rieng1 extends AppCompatActivity {
    ImageView favorite;
    LinearLayout nguyen_lieu_list;
    TextView name,ratetotal;
    ImageView image;
    Button nau_ngay,ghichu,dat_hang;
    ArrayList<MonAn> foodlistdata;
    RatingBar ratingBar;
    int position;
    FloatingActionButton delete_button,comment_button;
    com.example.myapplication.data_model.user user;

    public void dat_hang_event(MonAn mon_an)
    {
        Intent intent=new Intent(Activity_rieng1.this, activity_dathang.class);
        String dat_hang_list = "";
        for (int i=0;i<mon_an.nguyen_lieu.size();i++)
        {
            CheckBox checkbox=(CheckBox)findViewById(i);
            if(checkbox.isChecked())
            {
                dat_hang_list=dat_hang_list+checkbox.getText()+"\n";
            }
        }
        intent.putExtra("dat_hang_list",dat_hang_list);
        startActivity(intent);
    }


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rieng1);

        nau_ngay=(Button)findViewById(R.id.cook);
        favorite =(ImageView)findViewById(R.id.favorite);
        nguyen_lieu_list=(LinearLayout)findViewById(R.id.nguyenlieu_list2);
        name=(TextView)findViewById(R.id.name);
        ratetotal=(TextView) findViewById(R.id.ratetotal);
        image=(ImageView)findViewById(R.id.image);
        dat_hang=(Button)findViewById(R.id.dat_hang);
        ghichu=(Button)findViewById(R.id.note);
        ratingBar=(RatingBar) findViewById(R.id.ratingBar);
        DatabaseReference db= FirebaseDatabase.getInstance().getReference();
        foodlistdata=(ArrayList<MonAn>) getIntent().getSerializableExtra("foodlistdata");
        delete_button=(FloatingActionButton) findViewById(R.id.floatingActionButton3);
        comment_button=(FloatingActionButton) findViewById(R.id.commentbutton);

        final MonAn mon_an=(MonAn)getIntent().getSerializableExtra("mon_an");
        user= (com.example.myapplication.data_model.user) getIntent().getSerializableExtra("user");
        for(int i=0;i<foodlistdata.size();i++){
            if(foodlistdata.get(i).getTen().equals(mon_an.getTen()))
            {
                position = i;
                break;
            }
        }

        Picasso.get().load(mon_an.getHinhanh()).into(image);
        name.setText(mon_an.getTen());
        for(int i=0;i<mon_an.nguyen_lieu.size();i++)
        {
            CheckBox checkbox=new CheckBox(this);
            checkbox.setId(i);
            checkbox.setText(mon_an.nguyen_lieu.get(i));
            checkbox.setTextColor(Color.parseColor("#FFFFFF"));
            checkbox.setTypeface( ResourcesCompat.getFont(this, R.font.darker_grotesque));
            checkbox.setTextSize(18);
            nguyen_lieu_list.addView(checkbox);
        }
        dat_hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dat_hang_event(mon_an);
            }
        });

        db.child("favorite").child(user.getUsername()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue(String.class).contains(VNCharacterUtils.removeAccent(mon_an.getTen().toLowerCase())))
                {
                    favorite.setImageResource(R.mipmap.heart);
                    favorite.setTag(R.mipmap.heart);
                }
                else {
                    favorite.setImageResource(R.mipmap.unheart);
                    favorite.setTag(R.mipmap.unheart);
                }
                favorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if((Integer) favorite.getTag()==R.mipmap.unheart)
                        {
                            db.child("favorite").child(user.getUsername()).setValue(snapshot.getValue(String.class)+","+VNCharacterUtils.removeAccent(mon_an.getTen().toLowerCase()));
                            Toast.makeText(Activity_rieng1.this,"Món được thêm vào danh sách yêu thích",Toast.LENGTH_SHORT).show();
                            favorite.setImageResource(R.mipmap.heart);
                            favorite.setTag(R.mipmap.heart);}
                        else {
                            db.child("favorite").child(user.getUsername()).setValue(snapshot.getValue(String.class).replace(VNCharacterUtils.removeAccent(mon_an.getTen().toLowerCase()),""));
                            Toast.makeText(Activity_rieng1.this,"Món được xóa khỏi danh sách yêu thích",Toast.LENGTH_SHORT).show();
                            favorite.setImageResource(R.mipmap.unheart);
                            favorite.setTag(R.mipmap.unheart);
                        }
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Xử lý rating, tiếp tục tại onPause

        db.child("mon_an").child(String.valueOf(position)).child("rate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Rate> list_rate = new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    list_rate.add(postSnapshot.getValue(Rate.class));
                }
                db.child("mon_an").child(String.valueOf(position)).child("rate").removeEventListener(this);
                if (list_rate.size() > 0) {
                    float total_point=0;
                    for (int i = 0; i < list_rate.size(); i++) {
                        if (list_rate.get(i).getUsername().equals(user.getUsername())) {
                            ratingBar.setRating(list_rate.get(i).getPoint());
                        }
                        total_point+=list_rate.get(i).getPoint();
                    }
                    ratetotal.setText(String.valueOf(total_point/list_rate.size()));
                }
                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        Toast.makeText(Activity_rieng1.this,"Bạn đánh giá "+ String.valueOf(ratingBar.getRating())+" sao cho món này",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



        nau_ngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Activity_rieng1.this,activityrieng2.class);
                intent.putExtra("mon_an",mon_an);
                startActivity(intent);
            }
        });

        comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Activity_rieng1.this,activity_rieng1_comment.class);
                intent.putExtra("mon_an",mon_an);
                intent.putExtra("position",position);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });

        ghichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Activity_rieng1.this,activity_rieng1_note.class);
                intent.putExtra("name_of_food",mon_an.getTen());
                startActivity(intent);
            }
        });


        if(user.getUsername().toString().contains("admin"))
        {
            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int i=0;i<foodlistdata.size();i++)
                    {
                        if(foodlistdata.get(i).getTen().equals(mon_an.getTen()))
                        {
                            foodlistdata.remove(i);
                            break;
                        }
                    }
                    DatabaseReference dr=FirebaseDatabase.getInstance().getReference();
                    dr.child("mon_an").setValue(foodlistdata);
                    Intent intent=new Intent(Activity_rieng1.this,MainActivity.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
            });
        }
        else {
            delete_button.setVisibility(View.GONE);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();

        DatabaseReference db=FirebaseDatabase.getInstance().getReference();
        db.child("mon_an").child(String.valueOf(position)).child("rate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Rate> list_rate = new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    list_rate.add(postSnapshot.getValue(Rate.class));
                }
                db.child("mon_an").child(String.valueOf(position)).child("rate").removeEventListener(this);
                Rate new_rate = new Rate(user.getUsername(), ratingBar.getRating());
                if (list_rate.size() > 0) {
                    for (int i = 0; i < list_rate.size(); i++) {
                        if (list_rate.get(i).getUsername().equals(user.getUsername())) {
                            list_rate.remove(i);
                            break;
                        }
                    }
                }
                list_rate.add(new_rate);
                db.child("mon_an").child(String.valueOf(position)).child("rate").setValue(list_rate);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
