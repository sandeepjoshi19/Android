package com.example.sandeep.myevents;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class EventHome extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Button logout;
    TextView event,time,date;
    ImageButton eventlocation;
    public  static  final  String MYPREFERENCE="mypref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences=getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE) ;
        logout=(Button)findViewById(R.id.button6);
        event=(TextView)findViewById(R.id.event);
        time=(TextView)findViewById(R.id.time);
        date=(TextView)findViewById(R.id.date);
        eventlocation=(ImageButton)findViewById(R.id.imageButton2);

        final  int id=sharedPreferences.getInt("id",0);

        eventlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventSqlHelper eventSqlHelper=new EventSqlHelper(EventHome.this);
                Cursor c= eventSqlHelper.getData(id);
                if(c.moveToFirst()) {
                    Intent intent = new Intent(EventHome.this, EventLocation.class);
                    intent.putExtra("lattitude",c.getDouble(8));
                    intent.putExtra("longitude",c.getDouble(7));
                    startActivity(intent);
                }
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogout();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EventHome.this,AddEvent.class);
                intent.putExtra("id",sharedPreferences.getInt("id",0));
                startActivity(intent);
            }
        });


        EventSqlHelper eventSqlHelper=new EventSqlHelper(this);

        Calendar calendar=Calendar.getInstance();
       Cursor c=eventSqlHelper.getData(sharedPreferences.getInt("id", 0));
        StringBuilder sb=new StringBuilder();
        if(c.moveToFirst()){
            event.setText("Upcoming event :  "+ c.getString(1));
            date.setText("Date  :  " + c.getInt(4) + "/" + c.getInt(3) + "/" + c.getInt(2));
            time.setText("Time  :  " + c.getInt(5) + ":" + c.getInt(6));
        }
        if(c.moveToFirst())
        {
            final Intent intent=new Intent(EventHome.this,PushNotification.class);
            intent.setAction("event");
            startService(intent);
        }

        eventSqlHelper.close();
    }


    void userLogout()
    {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();
        stopService(new Intent(EventHome.this,PushNotification.class));
        Intent intent=new Intent(EventHome.this,EventLogin.class);
        startActivity(intent);
        finish();
    }

}
