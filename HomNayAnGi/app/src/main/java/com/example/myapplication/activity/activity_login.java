package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class activity_login extends AppCompatActivity {
    TextView regis;
    EditText username;
    EditText password;
    Button login;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        regis=(TextView)findViewById(R.id.regi);
        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);
        login=(Button) findViewById(R.id.button_login);
        img=(ImageView) findViewById(R.id.imageView);

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity_login.this,activity_dangky.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().isEmpty()||password.getText().toString().isEmpty())
                {
                    Toast.makeText(activity_login.this,"Vui lòng nhập tên đăng nhập,mật khẩu",Toast.LENGTH_LONG).show();
                }
                else {
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference();
                    db.child("account").child(username.getText().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try{
                            user user=snapshot.getValue(com.example.myapplication.data_model.user.class);
                            if(password.getText().toString().equals(user.getPassword()))
                            {
                                Intent intent=new Intent(activity_login.this,MainActivity.class);
                                intent.putExtra("user",user);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(activity_login.this,"Mặt khẩu hoặc tài khoản không hợp lệ",Toast.LENGTH_LONG).show();
                            }}
                            catch (NullPointerException e)
                            {
                                Toast.makeText(activity_login.this,"Mặt khẩu hoặc tài khoản không hợp lệ",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(activity_login.this,"Mặt khẩu hoặc tài khoản không hợp lệ",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }
}
