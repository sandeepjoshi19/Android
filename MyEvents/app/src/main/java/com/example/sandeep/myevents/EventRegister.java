package com.example.sandeep.myevents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EventRegister extends AppCompatActivity {
   EditText eemail,epassword,erpassword;
    EventSqlHelper eventSqlHelper;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_register);
        eemail=(EditText)findViewById(R.id.editText4);
        epassword=(EditText)findViewById(R.id.editText5);
        erpassword=(EditText)findViewById(R.id.editText6);
        register=(Button)findViewById(R.id.button5);

        eventSqlHelper=new EventSqlHelper(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }
    void registerUser() {
        String email = eemail.getText().toString();
        String password = epassword.getText().toString();
        String rpassword = erpassword.getText().toString();
        TextView t = (TextView) findViewById(R.id.textView5);
        if ((email != null) && password !=null&& (rpassword != null))
        {
            if (password.equals(rpassword)) {
                long id = eventSqlHelper.registerUser(email, password);
                if (id > 0) {

                    t.setText("Regestration Sucessful");
                    eemail.setVisibility(0);
                    epassword.setVisibility(0);
                    erpassword.setVisibility(0);
                    t.setVisibility(1);

                } else {
                    t.setText("Registration unsucessful");
                    t.setVisibility(1);
                }
            }
        }
        else
        {
            if (email == null || password == null || rpassword == null) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
