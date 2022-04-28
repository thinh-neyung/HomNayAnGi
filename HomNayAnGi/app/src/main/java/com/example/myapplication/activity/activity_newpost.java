package com.example.myapplication.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.data_model.MonAn;
import com.example.myapplication.data_model.cac_buoc;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class activity_newpost extends AppCompatActivity {
    ImageView imageview;
    Button select_button,add_nl_button,add_step_button,menu_theloai,menu_dokho;
    LinearLayout nguyenlieu_list,step_list;
    FloatingActionButton done_button;
    EditText name;
    Uri imageURI;
    String image_link;
    com.example.myapplication.data_model.user user;
    ArrayList<String> nguyenliau_list_data;
    ArrayList<cac_buoc> cacbuoc_list_data;
    ArrayList<MonAn> foodlistdata;
    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpost);

        imageview = (ImageView) findViewById(R.id.imageView5);
        select_button=(Button) findViewById(R.id.button_select);
        add_nl_button=(Button) findViewById(R.id.add_nl);
        nguyenlieu_list=(LinearLayout) findViewById(R.id.nguyenlieu_list);
        add_step_button=(Button) findViewById(R.id.add_step);
        step_list=(LinearLayout) findViewById(R.id.cacbuoc_list);
        menu_theloai=(Button) findViewById(R.id.button_menu_theloai);
        menu_dokho=(Button) findViewById(R.id.button_menu_dokho);
        done_button=(FloatingActionButton)findViewById(R.id.floatingActionButton2);
        name=(EditText) findViewById(R.id.editText_name);
        user= (com.example.myapplication.data_model.user) getIntent().getSerializableExtra("user");
        foodlistdata= (ArrayList<MonAn>) getIntent().getSerializableExtra("foodlistdata");
        nguyenliau_list_data=new ArrayList<String>();
        cacbuoc_list_data=new ArrayList<cac_buoc>();

        add_nl_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText=new EditText(activity_newpost.this);
                editText.setHint("Nguyên liệu "+String.valueOf(nguyenlieu_list.getChildCount()));
                editText.setId(nguyenlieu_list.getChildCount()+200+1);
                nguyenlieu_list.addView(editText);
            }
        });
        add_step_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText_mota=new EditText(activity_newpost.this);
                EditText editText_time=new EditText(activity_newpost.this);
                editText_mota.setHint("Nội dung");
                editText_time.setHint("Thời gian");
                editText_time.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText_mota.setId(step_list.getChildCount()+300+1);
                editText_time.setId(step_list.getChildCount()+300+2);
                step_list.addView(editText_mota);
                step_list.addView(editText_time);
            }
        });

        select_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        menu_dokho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(activity_newpost.this,menu_dokho);
                popupMenu.getMenuInflater().inflate(R.menu.menu_dokho,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.menuItem_de:
                                menu_dokho.setText("Dễ");
                                break;
                            case R.id.menuItem_trungbinh:
                                menu_dokho.setText("Trung bình");
                                break;
                            case R.id.menuItem_kho:
                                menu_dokho.setText("Khó");
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        menu_theloai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(activity_newpost.this,menu_theloai);
                popupMenu.getMenuInflater().inflate(R.menu.menu_theloai,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.menuItem_moncanh:
                                menu_theloai.setText("Món canh");
                                break;
                            case R.id.menuItem_anvat:
                                menu_theloai.setText("Ăn vặt");
                                break;
                            case R.id.menuItem_monbanh:
                                menu_theloai.setText("Món bánh");
                                break;
                            case R.id.menuItem_monchien:
                                menu_theloai.setText("Món chiên");
                                break;
                            case R.id.menuItem_monkho:
                                menu_theloai.setText("Món kho");
                                break;
                            case R.id.menuItem_thucuong:
                                menu_theloai.setText("Thức uống");
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri file = imageURI;
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();
                StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
                UploadTask uploadTask = riversRef.putFile(file);

// Register observers to listen for when the download is done or if it fails
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        riversRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    Uri downloadUri = task.getResult();
                                    image_link= downloadUri.toString();
                                    for(int i=0;i<nguyenlieu_list.getChildCount()-1;i++)
                                    {
                                        EditText nl=(EditText) findViewById(i+200+2);
                                        nguyenliau_list_data.add(nl.getText().toString());
                                    }
                                    for(int i=0;i<step_list.getChildCount()-1;i+=2)
                                    {
                                        EditText step=(EditText) findViewById(i+300+2);
                                        EditText time=(EditText) findViewById(i+300+3);
                                        cacbuoc_list_data.add(new cac_buoc(step.getText().toString(),time.getText().toString()));
                                    }
                                    MonAn new_monan = new MonAn(name.getText().toString(),menu_dokho.getText().toString(),image_link,menu_theloai.getText().toString(),nguyenliau_list_data,cacbuoc_list_data);
                                    foodlistdata.add(new_monan);
                                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                    mDatabase.child("mon_an").setValue(foodlistdata);
                                    Intent intent=new Intent(activity_newpost.this,MainActivity.class);
                                    intent.putExtra("user",user);
                                    startActivity(intent);
                                } else {
                                    // Handle failures
                                }
                            }
                        });;
                    }
                });

            }
        });
    }
    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    imageview.setImageURI(selectedImageUri);
                    imageURI=selectedImageUri;
                }
            }
        }
    }
}