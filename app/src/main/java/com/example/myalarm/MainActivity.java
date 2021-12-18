package com.example.myalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import android.content.Context;



public class MainActivity extends AppCompatActivity {
    TextView lblTime;
    int h,m;

    SimpleDateFormat sdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblTime=findViewById(R.id.lblTime);
        Calendar c= Calendar.getInstance();
        c.setTime(new Date());
        sdf=new SimpleDateFormat("HH:mm");
        lblTime.setText(sdf.format(c.getTime()));
        h=c.get(Calendar.HOUR_OF_DAY);
        m=c.get(Calendar.MINUTE);

        Button btnChanger=findViewById(R.id.btnChanger);
        btnChanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener listener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        h=hourOfDay;m=minute;
                        Calendar c=Calendar.getInstance();
                        c.set(0,0,0,h,m);
                        lblTime.setText(sdf.format(c.getTime()));
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,listener,h,m,true);
                timePickerDialog.updateTime(h,m);
                timePickerDialog.show();
            }
        });

        Button btnValider=findViewById(R.id.btnValider);
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent( MainActivity.this,AlarmService.class);
                AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
                PendingIntent pendingIntent=PendingIntent.getService(MainActivity.this,0,it,0);
                Calendar c=Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY,h);
                c.set(Calendar.MINUTE,m);
                c.set(Calendar.SECOND,0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),0,pendingIntent);
            }
        });
        Button btnStop=findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it =new Intent(MainActivity.this,AlarmService.class);

                stopService(it);

            }
        });



    }

}