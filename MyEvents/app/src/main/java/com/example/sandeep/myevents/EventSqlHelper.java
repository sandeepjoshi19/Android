package com.example.sandeep.myevents;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sandeep on 1/17/2016.
 */
public class EventSqlHelper extends SQLiteOpenHelper {
    public EventSqlHelper(Context context) {
        super(context, "EventDatabase.dp", null, 26);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE EVENTLOGIN( _id INTEGER PRIMARY KEY AUTOINCREMENT ,EMAIL TEXT UNIQUE,PASSWORD TEXT)";
        db.execSQL(query);
        query="CREATE TABLE EVENTS(_id INTEGER  PRIMARY KEY AUTOINCREMENT,EVENT TEXT NOT NULL,YEAR INTEGER NOT NULL,MONTH INTEGER NOT NULL," +
                "DAY INTEGER NOT NULL,HOUR INTEGER NOT NULL,MINUTE INTEGER NOT NULL ,"+
                "LONGITUDE INTEGER,LATITUDE INTEGER,ACCOUNT INTEGER,SNOOZE INTEGER DEFAULT 0," +
                "NOTIFIED INTEGER DEFAULT 0,TIMEDATE INTEGER NOT NULL,FOREIGN KEY(ACCOUNT) REFERENCES EVENTLOGIN(_id))";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int nwversion,int oldversion) {
        String query="DROP TABLE EVENTS";
        db.execSQL(query);
        query="DROP TABLE EVENTLOGIN";
        db.execSQL(query);
        onCreate(db);
    }

    long registerUser(String email,String password)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("EMAIL",email);
        cv.put("PASSWORD", password);
        long i= db.insert("EVENTLOGIN",null,cv);
        return  i;

    }

    Cursor loginUser(String username,String password)
    {
        SQLiteDatabase db=getReadableDatabase();
        String query="SELECT * FROM EVENTLOGIN WHERE EMAIL='"+username+"' AND PASSWORD='"+password+"'";
        return  db.rawQuery(query,null);
    }

    long addNewEvent(String event,int hour,int minute,int day,int month,int year,int latitude,int longitude,int id)
    {
        String eventdatetime= String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(day)+ " "+String.valueOf(hour)+":"+String.valueOf(minute);
        String datetime=String.valueOf(year)+String.valueOf(month+1)+String.valueOf(day)+String.valueOf(hour)+String.valueOf(minute);
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("EVENT",event);
        cv.put("HOUR",hour);
        cv.put("MINUTE",minute);
        cv.put("YEAR",year);
        cv.put("MONTH",month);
        cv.put("DAY",day);
        cv.put("LATITUDE",latitude);
        cv.put("LONGITUDE",longitude);
        cv.put("TIMEDATE",String.valueOf(datetime));
        cv.put("ACCOUNT",id);
        return db.insert("EVENTS",null,cv);
    }

    Cursor getEvent(int id)
    {
        SQLiteDatabase db=getReadableDatabase();
        String query="SELECT * FROM EVENTS WHERE ACCOUNT="+id+" AND NOTIFIED=0 ORDER BY TIMEDATE ";
        return db.rawQuery(query,null);

    }

    void setNotified(int id)
    {
        SQLiteDatabase db= getWritableDatabase();
        String query="UPDATE EVENTS SET NOTIFIED=1 WHERE _id="+id;
         db.execSQL(query);
    }

    long nextEvent(int id)
    {
        SQLiteDatabase db=getReadableDatabase();
        String query="SELECT * FROM EVENTS WHERE ACCOUNT="+id;
        Cursor c=db.rawQuery(query,null);
        return c.getCount();
    }

}
