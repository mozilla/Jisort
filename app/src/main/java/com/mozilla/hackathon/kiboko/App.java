package com.mozilla.hackathon.kiboko;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by Brian Mwadime on 06/06/2016.
 */
public class App extends Application {
    public static String PACKAGE_NAME;
    protected class MainThreadBus extends Bus {
        private final Handler mHandler = new Handler(Looper.getMainLooper());

        public MainThreadBus() {
            super(ThreadEnforcer.ANY);
        }

        @Override
        public void post(final Object event) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                super.post(event);
            } else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        MainThreadBus.super.post(event);
                    }
                });
            }
        }
    }

    protected static Context context = null;

    private static MainThreadBus mEventBus;

    public static Bus getBus() {
        return mEventBus;
    }

    public static boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager)getContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if("com.mozilla.hackathon.kiboko.services.ChatHeadService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        PACKAGE_NAME = context.getPackageName();
        mEventBus = new MainThreadBus();
        // Battery receiver - Catches battery low system event
        // context.registerReceiver(new DSOBatteryReceiver(), new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    public static Context getContext() {
        return context;
    }
}
