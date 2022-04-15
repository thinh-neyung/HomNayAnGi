package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.data_model.user;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class activity_dangky extends AppCompatActivity {

    TextView login;
    EditText username,password,name,address,phone;
    Button dangky;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);

        login=(TextView)findViewById(R.id.login);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        name=(EditText)findViewById(R.id.name);
        address=(EditText)findViewById(R.id.address);
        phone=(EditText)findViewById(R.id.phone);
        dangky=(Button) findViewById(R.id.button_dangky);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity_dangky.this,activity_login.class);
                startActivity(intent);
            }
        });
        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().replaceAll("\\s+", "").isEmpty() || password.getText().toString().replaceAll("\\s+", "").isEmpty() || name.getText().toString().replaceAll("\\s+", "").isEmpty() || address.getText().toString().replaceAll("\\s+", "").isEmpty() || phone.getText().toString().replaceAll("\\s+", "").isEmpty()) {
                    Toast.makeText(activity_dangky.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_LONG).show();
                } else {
                    if (username.getText().toString().contains(" ")) {
                        Toast.makeText(activity_dangky.this, "Tên đăng nhập không hợp lệ", Toast.LENGTH_LONG).show();
                    } else {
                        user user = new user(username.getText().toString(), password.getText().toString(), name.getText().toString(), address.getText().toString(), phone.getText().toString());
                        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                        ValueEventListener username_check=new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int count=0;
                                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                                    user user_check=postSnapshot.getValue(com.example.myapplication.data_model.user.class);
                                    if(user.getUsername().equals(user_check.getUsername()))
                                    {
                                        count++;
                                        break;
                                    }};
                                if(count==1) {
                                    Toast.makeText(activity_dangky.this, "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    db.child("account").child(username.getText().toString()).setValue(user);
                                    db.child("favorite").child(user.getUsername()).setValue("");
                                    Toast.makeText(activity_dangky.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(activity_dangky.this, activity_login.class);
                                    db.child("account").removeEventListener(this);
                                    startActivity(intent);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        };
                        db.child("account").addValueEventListener(username_check);

                    }
                }
            }
        });

    }
}
