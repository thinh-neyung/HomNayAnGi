package com.example.myapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.myapplication.R;
import com.example.myapplication.VNCharacterUtils;
import com.example.myapplication.data_model.MonAn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Activity_rieng1 extends AppCompatActivity {
    ImageView favorite;
    LinearLayout nguyen_lieu_list;
    TextView name;
    ImageView image;
    Button nau_ngay;
    Button dat_hang;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rieng1);

        nau_ngay=(Button)findViewById(R.id.cook);
        favorite =(ImageView)findViewById(R.id.favorite);
        nguyen_lieu_list=(LinearLayout)findViewById(R.id.nguyenlieu_list2);
        name=(TextView)findViewById(R.id.name);
        image=(ImageView)findViewById(R.id.image);
        dat_hang=(Button)findViewById(R.id.dat_hang);
        DatabaseReference db= FirebaseDatabase.getInstance().getReference();

        final MonAn mon_an=(MonAn)getIntent().getSerializableExtra("mon_an");
        user= (com.example.myapplication.data_model.user) getIntent().getSerializableExtra("user");

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


        nau_ngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Activity_rieng1.this,activityrieng2.class);
                intent.putExtra("mon_an",mon_an);
                startActivity(intent);
            }
        });
    }
}
