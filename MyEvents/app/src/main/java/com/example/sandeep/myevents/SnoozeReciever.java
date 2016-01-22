package com.example.sandeep.myevents;

        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.widget.Toast;

/**
 * Created by sandeep on 1/21/2016.
 */
public class SnoozeReciever extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent snooze=new Intent(context,PushNotification.class);
        snooze.setAction("snooze");
        snooze.putExtra("eventid", intent.getIntExtra("eventid", 0));
         context.startService(snooze);
    }
}
