package com.mozilla.hackathon.kiboko;

import android.app.Application;
import android.content.Context;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by Audrey on 06/06/2016.
 */
public class App extends Application {

    protected static Context context = null;

    private static Bus mEventBus;

    public static Bus getBus() {
        return mEventBus;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        mEventBus = new Bus(ThreadEnforcer.ANY);
    }

    public static Context getContext() {
        return context;
    }

}
