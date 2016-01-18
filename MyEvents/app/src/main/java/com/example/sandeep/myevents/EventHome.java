package com.example.sandeep.myevents;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EventHome extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Button logout;
    TextView e;
    public  static  final  String MYPREFERENCE="mypref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences=getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE) ;
        logout=(Button)findViewById(R.id.button6);
        e=(TextView)findViewById(R.id.textView6);
        int id=sharedPreferences.getInt("id",0);
        String email=sharedPreferences.getString("email", null);
        e.setText(String.valueOf(id) + "  "+email);

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
    }

    void userLogout()
    {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Intent intent=new Intent(EventHome.this,EventLogin.class);
        startActivity(intent);
        finish();
    }

}
