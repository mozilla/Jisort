package com.mozilla.hackathon.kiboko;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;

import com.mozilla.hackathon.kiboko.activities.TutorialSlideActivity;
import com.mozilla.hackathon.kiboko.recievers.DSOBatteryReceiver;

/**
 * Created by Brian Mwadime on 06/06/2016.
 */
public class App extends Application {
    public static String PACKAGE_NAME;
    protected static Context context = null;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        PACKAGE_NAME = context.getPackageName();
        // Battery receiver - Catches battery low system event
         context.registerReceiver(new DSOBatteryReceiver(), new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    public static Context getContext() {
        return context;
    }

    public static void createNotification(String msg, String tag) {
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(getContext(), TutorialSlideActivity.class);
//        intent.putExtra("title", topic.getName());
        intent.putExtra("topic", tag);
        PendingIntent pIntent = PendingIntent.getActivity(getContext(), (int) System.currentTimeMillis(), intent, 0);

        // Build notification
        // Actions are just fake
        Notification notification = new NotificationCompat.Builder(getContext())
                .setContentTitle(msg)
                .setContentText("Jisort").setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pIntent)
                .addAction(0, "Lear More", pIntent).build();
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, notification);

    }
}
