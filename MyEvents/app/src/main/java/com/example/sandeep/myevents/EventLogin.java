package com.example.sandeep.myevents;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EventLogin extends AppCompatActivity {
    EventSqlHelper eventSqlHelper;
    Button login,register;
    EditText username,password;
    SharedPreferences sharedPreferences;
    public  static  final  String MYPREFERENCE="mypref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_login);
        sharedPreferences=getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE) ;
        login=(Button)findViewById(R.id.button3);
        eventSqlHelper=new EventSqlHelper(this);
        register=(Button)findViewById(R.id.button4);
        username=(EditText)findViewById(R.id.editText2);
        password=(EditText)findViewById(R.id.editText3);
        int id=sharedPreferences.getInt("id",0);
        if(id>0)
        {
            Intent intent=new Intent(EventLogin.this,EventHome.class);
            startActivity(intent);
            finish();
        }
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EventLogin.this,EventRegister.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }

    void checkLogin()
    {
        String email=username.getText().toString();
        String pword=password.getText().toString();
        if(email!=null && pword!=null) {
            Cursor c = eventSqlHelper.loginUser(username.getText().toString(), password.getText().toString());
            if (c.moveToFirst()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("id", c.getInt(0));
                editor.putString("email", c.getString(1));
                editor.putString("password", c.getString(2));
                editor.commit();
                Intent intent=new Intent(EventLogin.this,EventHome.class);
                startActivity(intent);
                finish();


            } else {
                TextView t = (TextView) findViewById(R.id.textView4);
                t.setText("Login UnSucessful");
                t.setVisibility(1);
            }
        }
        else
        {
            Toast.makeText(this,"fill all fields",Toast.LENGTH_SHORT).show();
        }
    }

}
