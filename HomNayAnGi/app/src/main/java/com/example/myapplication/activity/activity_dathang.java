package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;


import com.example.myapplication.R;

public class activity_dathang extends AppCompatActivity {
    EditText dat_hang_list;
    ImageButton dat_hang_messenger;
    ImageButton dat_hang_call;
    ImageButton dat_hang_sms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dathang);

        dat_hang_list=(EditText) findViewById(R.id.dat_hang_list);
        dat_hang_messenger=(ImageButton)findViewById(R.id.messenger_button);
        dat_hang_call=(ImageButton)findViewById(R.id.call_button);
        dat_hang_sms=(ImageButton)findViewById(R.id.sms_button);

        final String dat_hang_list_data= getIntent().getStringExtra("dat_hang_list");
        dat_hang_list.setText(dat_hang_list_data);
        dat_hang_list.setMovementMethod(new ScrollingMovementMethod());

        dat_hang_messenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://m.me/1621823854551677"));
                startActivity(intent);
            }
        });
        dat_hang_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("sms:012345678"));
                intent.putExtra("sms_body","Đơn hàng nguyên liệu:\n"+dat_hang_list_data);
                startActivity(intent);
            }
        });
        dat_hang_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:012345678"));
                startActivity(intent);
            }
        });
    }
}
