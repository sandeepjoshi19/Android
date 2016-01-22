package com.example.sandeep.myevents;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by sandeep on 1/19/2016.
 */
public class EventTimeReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intent1=new Intent(context,PushNotification.class);
        intent1.setAction("notification");
        intent.putExtra("eventid",intent.getIntExtra("eventid",0));
        context.startService(intent1);

    }

}
