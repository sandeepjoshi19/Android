package com.example.sandeep.myevents;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class AddEvent extends AppCompatActivity {
    Button btime,bdate,addevent;
    int hours=-1,minutes=-1,days=-1,months=-1,years=-1;
    double latitudes=1,longitudes=1;
    String event;
    EventSqlHelper eventSqlHelper;
    Date date;
    EditText eventinfo;
    ImageButton curlocation;
    Calendar eventdatetime=Calendar.getInstance();;
    TextView tdate,ttime;
    int REQUEST_CODE=10;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        eventSqlHelper=new EventSqlHelper(this);
        curlocation=(ImageButton)findViewById(R.id.imageButton);
        eventinfo=(EditText)findViewById(R.id.editText);
        addevent=(Button)findViewById(R.id.button7);
        ttime=(TextView)findViewById(R.id.textView2);
        tdate=(TextView)findViewById(R.id.textView3);
        btime=(Button)findViewById(R.id.button);
        bdate=(Button)findViewById(R.id.button2);


        curlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(AddEvent.this,CurrentLocation.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addEvent();
            }
        });
        btime.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DialogFragment newFragment = new TimePickerFragment();
               newFragment.show(getSupportFragmentManager(), "timePicker");
            }
       });
        bdate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DialogFragment newFragment = new DatePickerFragment();
               newFragment.show(getSupportFragmentManager(), "datePicker");
            }
       });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(AddEvent.this,AddLocation.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
    }


    public void addEvent()
    {
        String event=eventinfo.getText().toString();

        if(!event.isEmpty() && hours!=-1 && minutes!=-1 && days!=-1 && months!=-1 && years!=-1 && latitudes!=-1 && longitudes!=-1)
        {   Intent intent=getIntent();
            long id= eventSqlHelper.addNewEvent(event,hours,minutes,days,months,years,latitudes,longitudes,intent.getIntExtra("id",0));
           if(id>0)
           {

               Toast.makeText(this,"Event added",Toast.LENGTH_SHORT).show();
               Intent intent1=new Intent(AddEvent.this,EventHome.class);

               startActivity(intent1);
               finish();
           }
        }

        else
        {
            Toast.makeText(this,"fill all data  "+String.valueOf(latitudes)+String.valueOf(longitudes)+ String.valueOf(minutes)+String.valueOf(hours)+String.valueOf(days)+String.valueOf(months)+String.valueOf(years),Toast.LENGTH_SHORT).show();

        }
    }

    public  void onActivityResult(int requestcode,int resultcode,Intent intent)
    {
        if(requestcode==REQUEST_CODE && resultcode==RESULT_OK)
        {
            latitudes=intent.getDoubleExtra("lattitude",0);
            longitudes=intent.getDoubleExtra("longitude",0);
            Toast.makeText(this,String.valueOf(latitudes)+String.valueOf(longitudes),Toast.LENGTH_SHORT).show();
        }
    }
    public  class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);


            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hours=hourOfDay;
            minutes=minute;

            ttime.setText("Selected time : "+String.valueOf(hourOfDay)+":"+String.valueOf(minute));

        }
    }

    public  class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            years=year;
            months=month;
            days=day;
           tdate.setText("Selected date : "+String.valueOf(day)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));
        }
    }

}
