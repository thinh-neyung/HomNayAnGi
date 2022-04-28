package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Person_activity extends AppCompatActivity {

    Button save_button,logout_button;
    EditText name,address,phone;

    com.example.myapplication.data_model.user user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_activity2);

        save_button=(Button)findViewById(R.id.save_button);
        logout_button=(Button)findViewById(R.id.button_logout);
        name=(EditText)findViewById(R.id.editText_name);
        address=(EditText)findViewById(R.id.editText_address);
        phone=(EditText)findViewById(R.id.editText_phone);
        user= (com.example.myapplication.data_model.user) getIntent().getSerializableExtra("user");


        name.setText(user.getName());
        address.setText(user.getAddress());
        phone.setText(user.getPhone());

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().replaceAll("\\s+", "").isEmpty()||address.getText().toString().replaceAll("\\s+", "").isEmpty()||phone.getText().toString().replaceAll("\\s+", "").isEmpty())
                {
                    Toast.makeText(Person_activity.this,"Vui lòng điền đầy đủ thông tin",Toast.LENGTH_LONG).show();
                }
                else {
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference();
                    user.setName(name.getText().toString());
                    user.setAddress(address.getText().toString());
                    user.setPhone(phone.getText().toString());
                    db.child("account").child(user.getUsername()).setValue(user);
                    Toast.makeText(Person_activity.this,"Cập nhật thông tin thành công",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Person_activity.this,MainActivity.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }

            }
        });
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Person_activity.this,activity_login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
