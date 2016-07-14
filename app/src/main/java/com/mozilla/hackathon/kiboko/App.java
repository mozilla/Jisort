package com.mozilla.hackathon.kiboko;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

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
}
