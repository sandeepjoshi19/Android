package com.example.sandeep.myevents;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by sandeep on 1/17/2016.
 */
public class ForceStopStarter extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent restart=new Intent(context,PushNotification.class);
        restart.setAction("Restart");
        context.startService(restart);
    }
}
