package com.example.timetoeat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class ReminderBreakfast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ReminderBreakfast", "ReminderBreakfast");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //Intent to be created upon clicking on the notification
        Intent repeating_intent = new Intent(context, StartPage.class);

        //This will replace the specified intent if it is open already (i.e. if StartPage is open already)
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, repeating_intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyEat")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_access_time_black_24dp)
                .setContentTitle("Wakey wakey! Food time!")
                .setContentText("Click to get your day started with an exciting breakfast recipe.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true); //This allows the notification to be cancelled (i.e. swiped away)

        if (intent.getAction().equals("morning_notification")) {
            notificationManager.notify(100, builder.build());
        }
    }
}
