package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ItemClickSupport;
import com.example.myapplication.R;
import com.example.myapplication.VNCharacterUtils;
import com.example.myapplication.adapter.foodadapter;
import com.example.myapplication.data_model.MonAn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class search_result extends AppCompatActivity {

    TextView txt;
    ArrayList<MonAn> foodlist_result;
    RecyclerView list_result;
    EditText search_bar;
    ImageButton search_button;
    ArrayList<MonAn> foodlistdata;
    ImageButton home_button;
    com.example.myapplication.data_model.user user;

    public void search_event()
    {
        final String search_string= VNCharacterUtils.removeAccent(search_bar.getText().toString()).toLowerCase();
        ArrayList<MonAn>  search_result =new ArrayList<>();
        Intent intent_searchresult=new Intent(this,search_result.class);
        if (search_string.isEmpty())
        {
            Toast toast=Toast. makeText(this,"Nhập từ khóa tìm kiếm",Toast. LENGTH_LONG);
            toast.show();
        }
        else{
            int count=0;
            for(int i=0;i<foodlistdata.size();i++)
            {
                String name =VNCharacterUtils.removeAccent(foodlistdata.get(i).getTen()).toLowerCase();
                if(name.indexOf(search_string)>=0)
                {
                    intent_searchresult.putExtra(Integer.toString(count),foodlistdata.get(i));
                    count++;
                }
            }
            if(count==0)
            {
                Toast toast=Toast. makeText(this,"Không có kết quả tìm kiếm phù hợp",Toast. LENGTH_LONG);
                toast.show();
            }
            else {
                intent_searchresult.putExtra("count", count);
                intent_searchresult.putExtra("search_text",search_string);
                intent_searchresult.putExtra("user",user);
                startActivity(intent_searchresult);
            }
        }
    }
    public void home_event()
    {
        Intent intent=new Intent(this, MainActivity.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        foodlist_result=new ArrayList<>();
        list_result=(RecyclerView) findViewById(R.id.list_result);
        search_bar=(EditText)findViewById(R.id.search);
        search_button=(ImageButton)findViewById(R.id.button_search);
        foodlistdata=new ArrayList<>();
        home_button=(ImageButton)findViewById(R.id.home_button);
        user= (com.example.myapplication.data_model.user) getIntent().getSerializableExtra("user");


        search_bar.setText(getIntent().getStringExtra("search_text"));
        int count=getIntent().getIntExtra("count",0);
        for(int i=0;i<count;i++)
        {
            foodlist_result.add((MonAn) getIntent().getSerializableExtra(Integer.toString(i)));
        }
        foodadapter result_adapter=new foodadapter(this,foodlist_result);
        list_result.setAdapter(result_adapter);
        list_result.setLayoutManager(new GridLayoutManager(this,2));
        list_result.addItemDecoration(new foodadapter.GridSpacingItemDecoration(2,40,true));

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home_event();
            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference db= FirebaseDatabase.getInstance().getReference();
                db.child("mon_an").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            foodlistdata.add(postSnapshot.getValue(MonAn.class));
                        }
                        search_event();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
        search_bar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if((i== EditorInfo.IME_ACTION_DONE))
                {
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference();
                    db.child("mon_an").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                foodlistdata.add(postSnapshot.getValue(MonAn.class));
                            }
                            search_event();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                    return true;
                }
                return false;
            }
        });

        ItemClickSupport.addTo(list_result).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent=new Intent(search_result.this, Activity_rieng1.class);
                intent.putExtra("mon_an",foodlist_result.get(position));
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
    }
}
