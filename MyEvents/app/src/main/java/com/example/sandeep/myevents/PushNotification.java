package com.example.sandeep.myevents;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.Calendar;


/**
 * Created by sandeep on 1/18/2016.
 */
public class PushNotification extends Service {
    public PushNotification() {
        super();
    }
    Calendar calendar;
    EventSqlHelper eventSqlHelper;
    SharedPreferences sharedPreferences;
    public  static  final  String MYPREFERENCE="mypref";
    NotificationActionReciever notificationActionReciever;
    String id;
    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        eventSqlHelper=new EventSqlHelper(this);
        id = new String();
        filter.addAction("snooze");
        filter.addAction("dismiss");
        notificationActionReciever=new NotificationActionReciever();
        registerReceiver(notificationActionReciever, filter);
        calendar=Calendar.getInstance();
        sharedPreferences=getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE) ;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent!=null) {
            id = intent.getAction();
            if (id.equals("notification")) {
                notification(sharedPreferences.getInt("id", 0));
            } else {
                Toast.makeText(this, intent.getAction(), Toast.LENGTH_SHORT).show();
            }
        }

        notificationDateTime(sharedPreferences.getInt("id",0));
        return START_STICKY;

    }


    void notificationDateTime(int id)
    {
        Cursor c=eventSqlHelper.getEvent(id);
        if(c.moveToFirst()) {
            calendar.set(Calendar.YEAR, c.getInt(2));
            calendar.set(Calendar.MONTH, c.getInt(3));
            calendar.set(Calendar.DAY_OF_MONTH, c.getInt(4));
            calendar.set(Calendar.HOUR_OF_DAY, c.getInt(5));
            calendar.set(Calendar.MINUTE, c.getInt(6));
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.AM_PM, Calendar.PM);
            calendar.add(Calendar.MINUTE, -15);

            Intent myIntent = new Intent(this, EventTimeReciever.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }


    }

    void notification(int id)
    {
        Intent intent=new Intent(PushNotification.this,EventHome.class);
        Intent snooze=new Intent();
        snooze.setAction("snooze");
        Intent dismiss = new Intent();
        dismiss.setAction("dismiss");

        dismiss.putExtra("id",1);

        PendingIntent pintent,psnooze,pdismiss;
        pintent= PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        psnooze = PendingIntent.getBroadcast(this, 0, snooze, PendingIntent.FLAG_UPDATE_CURRENT);
        pdismiss=PendingIntent.getBroadcast(this,0,dismiss,PendingIntent.FLAG_UPDATE_CURRENT);

        Cursor c=eventSqlHelper.getEvent(id);
        c.moveToFirst();


        NotificationManager mnotify=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder notify=new Notification.Builder(this)
                .setContentTitle("Upcoming Event")
                .setContentText(c.getString(1)+"\n in 15 minutes ")
                .setSmallIcon(R.drawable.ic_action_event)
                .setContentIntent(pintent)
                .addAction(R.drawable.ic_action_event, "snooze", psnooze)
                .addAction(R.drawable.ic_action_cancel, "dismiss", pdismiss);
        mnotify.notify(1,notify.build());
        eventSqlHelper.setNotified(c.getInt(0));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(sharedPreferences.getInt("id",0)!=0) {
            sendBroadcast(new Intent("Restart"));
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class NotificationActionReciever extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("snooze"))
            {
                Toast.makeText(context,"Snooze",Toast.LENGTH_SHORT).show();
            }
            else if(intent.getAction().equals("dismiss"));
            {
                Toast.makeText(context,"dismiss",Toast.LENGTH_SHORT).show();
            }

        }
    }


}
