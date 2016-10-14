package com.mozilla.hackathon.kiboko;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.mozilla.hackathon.kiboko.activities.TutorialSlideActivity;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
