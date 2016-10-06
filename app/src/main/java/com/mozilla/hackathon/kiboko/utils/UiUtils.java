package com.mozilla.hackathon.kiboko.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.mozilla.hackathon.kiboko.R;
import com.mozilla.hackathon.kiboko.activities.TutorialSlideActivity;

public class UiUtils {
    public static final int DEFAULT_NOTIFICATION_ID = 111;

    public static int showNotification(Context context, String msg, String tag, @Nullable Integer notificationId) {
        Integer id  = notificationId == null ? DEFAULT_NOTIFICATION_ID : notificationId;
        Intent intent = new Intent(context, TutorialSlideActivity.class);
        intent.putExtra("topic", tag);
        intent.putExtra("notification", id);
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);

        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText("Tap for more information.")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_stat_notification)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .addAction(0, context.getString(R.string.btn_more), pIntent)
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, notification);
        return id;
    }

    public static int showNotification(Context context, String msg, String tag) {
        return showNotification(context, msg, tag, DEFAULT_NOTIFICATION_ID);
    }

    public static void cancelNotification(Context context, int id) {
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(id);
    }
}
