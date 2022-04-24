package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.data_model.MonAn;
import com.example.myapplication.R;

public class activityrieng2 extends AppCompatActivity {


    LinearLayout list_cac_buoc;
    MonAn mon_an;
    int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityrieng2);

        list_cac_buoc=(LinearLayout)findViewById(R.id.list_cac_buoc);
        mon_an=(MonAn) getIntent().getSerializableExtra("mon_an");

        for(int i=0;i<mon_an.list_cac_buoc.size();i++)
        {
            a=i;
            final Button hen_gio=new Button(this);
            TextView buoc_i=new TextView(this);
            buoc_i.setText("BƯỚC "+(i+1)+": "+mon_an.list_cac_buoc.get(i).getNoi_dung());
            buoc_i.setTextColor(Color.parseColor("#FFFFFF"));
            buoc_i.setTypeface( ResourcesCompat.getFont(this, R.font.darker_grotesque));
            buoc_i.setTextSize(18);
            hen_gio.setId(i);
            hen_gio.setText("Hẹn "+mon_an.list_cac_buoc.get(i).getTime()+" phút");
            hen_gio.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            hen_gio.setTextColor(Color.parseColor("#EC7196"));
            hen_gio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int time =60000*(Integer.parseInt(mon_an.list_cac_buoc.get(hen_gio.getId()).getTime()));
                    CountDownTimer Timer = new CountDownTimer(time, 1000) {
                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                                NotificationChannel channel = new NotificationChannel("CHANNEL_ID", "name", NotificationManager.IMPORTANCE_DEFAULT);
                                // Register the channel with the system; you can't change the importance
                                // or other notification behaviors after this
                                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                                notificationManager.createNotificationChannel(channel);
                            }
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(activityrieng2.this, "CHANNEL_ID")
                                    .setSmallIcon(R.mipmap.theloai_monbanh)
                                    .setContentTitle("Nhanh lên kẻo cháy!")
                                    .setContentText("Hoàn thành bước "+((Integer)hen_gio.getId()+1)+" món "+mon_an.getTen()+" rồi bạn ơi :D")
                                    .setPriority(NotificationCompat.PRIORITY_MAX)
                                    .setDefaults(NotificationCompat.DEFAULT_SOUND)
                                    ;
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(activityrieng2.this);
                            notificationManager.notify(1, builder.build());
                        }
                    };
                    Timer.start();
                }
            });
            list_cac_buoc.addView(buoc_i);
            if(Integer.parseInt(mon_an.list_cac_buoc.get(i).getTime())!=0 )
            {
                list_cac_buoc.addView(hen_gio);
            }

        }
    }
}
