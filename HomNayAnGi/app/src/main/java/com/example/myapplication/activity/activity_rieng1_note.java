package com.example.myapplication.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class activity_rieng1_note extends AppCompatActivity {

    Button save_button_in_note;
    String ten_mon;
    EditText note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rieng1_note);

        save_button_in_note=(Button) findViewById(R.id.save_button_in_note);
        ten_mon=getIntent().getStringExtra("name_of_food");
        note=(EditText) findViewById(R.id.noi_dung);


        SharedPreferences sharedPref = activity_rieng1_note.this.getSharedPreferences("note_Preferences", Context.MODE_PRIVATE);
        String noi_dung = sharedPref.getString(ten_mon,"");
        note.setText(noi_dung);
        if(noi_dung.isEmpty())
        {
            note.setHint("Thêm ghi chú của bạn tại đây");
            note.setHintTextColor(Color.parseColor("#FF934A5B"));
        }

        save_button_in_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_button_in_note_onclick();
            }
        });

    }
    public void save_button_in_note_onclick(){
        SharedPreferences sharedPref = activity_rieng1_note.this.getSharedPreferences("note_Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ten_mon,note.getText().toString());
        editor.commit();
        finish();
    };
}