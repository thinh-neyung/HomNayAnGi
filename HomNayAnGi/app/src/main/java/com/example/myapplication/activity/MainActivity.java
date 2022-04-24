package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
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
import com.example.myapplication.adapter.TheLoai_adapter;
import com.example.myapplication.adapter.foodadapter;
import com.example.myapplication.data_model.MonAn;
import com.example.myapplication.data_model.TheLoai;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView foodlistview;
    public ArrayList<MonAn> foodlistdata;
    GridView theloai_listview;
    ArrayList<TheLoai> theloai_data;
    EditText search_bar;
    ImageButton search_button;
    ImageButton person_button;
    com.example.myapplication.data_model.user user;
    protected void add_data_to_theloai_data(ArrayList<TheLoai> theloai_data,ArrayList<MonAn> foodlistdata) {
        for (int a = 0; a < theloai_data.size(); a++) {
            ArrayList<MonAn> list = new ArrayList<>();
            for (int b = 0; b < foodlistdata.size(); b++) {
                if (a == 6) {

                } else {
                    String theloai = foodlistdata.get(b).getTheloai();
                    if (theloai.contains(VNCharacterUtils.removeAccent(theloai_data.get(a).getName().toLowerCase()))) {
                        list.add(foodlistdata.get(b));
                    }
                }
                theloai_data.get(a).setMonAn_list(list);
            }
        }
    }

    public void add_data_to_theloai_data()
    {
        theloai_data.add(new TheLoai("Món canh",R.mipmap.theloai_moncanh,new ArrayList<MonAn>()));
        theloai_data.add(new TheLoai("Ăn vặt",R.mipmap.theloai_anvat,new ArrayList<MonAn>()));
        theloai_data.add(new TheLoai("Món bánh",R.mipmap.theloai_monbanh,new ArrayList<MonAn>()));
        theloai_data.add(new TheLoai("Món chiên",R.mipmap.theloai_monchien,new ArrayList<MonAn>()));
        theloai_data.add(new TheLoai("Món kho",R.mipmap.theloai_monkho,new ArrayList<MonAn>()));
        theloai_data.add(new TheLoai("Thức uống",R.mipmap.theloai_thucuong,new ArrayList<MonAn>()));
        theloai_data.add(new TheLoai("Yêu thích",R.mipmap.theloai_yeuthich,new ArrayList<MonAn>()));
    }
    public void search_event()
    {
        final String search_string=VNCharacterUtils.removeAccent(search_bar.getText().toString()).toLowerCase();
        ArrayList<MonAn>  search_result =new ArrayList<>();
        Intent intent_searchresult=new Intent(this, com.example.myapplication.activity.search_result.class);
        if (search_string.isEmpty())
        {
            Toast toast=Toast. makeText(MainActivity.this,"Nhập từ khóa tìm kiếm",Toast. LENGTH_LONG);
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
                Toast toast=Toast. makeText(MainActivity.this,"Không có kết quả tìm kiếm phù hợp",Toast. LENGTH_LONG);
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
    public void set_time_for_all(ArrayList<MonAn> foodlistdata)
    {
        for(int i=0;i<foodlistdata.size();i++)
        {
            foodlistdata.get(i).setThoigian();
        }
    }
    public void set_food_data_database(ArrayList<MonAn> foodlistdata)
    {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("mon_an").setValue(foodlistdata);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foodlistview=(RecyclerView) findViewById(R.id.list_noibat);
        user= (com.example.myapplication.data_model.user) getIntent().getSerializableExtra("user");
        foodlistdata=new ArrayList<>();
        theloai_listview=(GridView)findViewById(R.id.list_danhsach);
        theloai_data=new ArrayList<>();
        search_bar=(EditText)findViewById(R.id.search);
        search_button=(ImageButton)findViewById(R.id.button_search);
        person_button=(ImageButton) findViewById(R.id.person_button);

        add_data_to_theloai_data();
        TheLoai_adapter theLoai_adapter=new TheLoai_adapter(this,R.layout.theloai_layout,theloai_data);
        theloai_listview.setAdapter(theLoai_adapter);

        DatabaseReference db=FirebaseDatabase.getInstance().getReference();
        db.child("mon_an").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    foodlistdata.add(postSnapshot.getValue(MonAn.class));
                }
                set_time_for_all(foodlistdata);
                final foodadapter foodadapter = new foodadapter(MainActivity.this,foodlistdata);
                foodlistview.setAdapter(foodadapter);
                foodlistview.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                foodlistview.addItemDecoration(new foodadapter.GridSpacingItemDecoration(2,40,true));
                add_data_to_theloai_data(theloai_data,foodlistdata);
                ItemClickSupport.addTo(foodlistview).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent intent=new Intent(MainActivity.this,Activity_rieng1.class);
                        intent.putExtra("mon_an",foodlistdata.get(position));
                        intent.putExtra("user",user);
                        startActivity(intent);
                    }
                });
                db.child("favorite").child(user.getUsername()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String favorite_list=snapshot.getValue(String.class);
                        theloai_data.get(6).MonAn_list.removeAll(theloai_data.get(6).MonAn_list);
                        for(int i=0;i<foodlistdata.size();i++)
                        {
                            if (favorite_list.contains(VNCharacterUtils.removeAccent(foodlistdata.get(i).getTen().toLowerCase())))
                            {
                                theloai_data.get(6).MonAn_list.add(foodlistdata.get(i));
                            }
                        }
                        theloai_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent_searchresult=new Intent(MainActivity.this,search_result.class);
                                int count=0;
                                for(int a=0;a<theloai_data.get(i).getMonAn_list().size();a++)
                                {
                                    intent_searchresult.putExtra(Integer.toString(a), theloai_data.get(i).getMonAn_list().get(a));
                                    count++;
                                }
                                intent_searchresult.putExtra("count",count);
                                intent_searchresult.putExtra("user",user);
                                startActivity(intent_searchresult);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        search_bar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if((i==EditorInfo.IME_ACTION_DONE))
                {
                    search_event();
                    return true;
                }
                return false;
            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                search_event();

            }
        });


        person_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,Person_activity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
    }
}
