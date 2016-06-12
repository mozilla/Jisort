package com.mozilla.hackathon.kiboko.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mozilla.hackathon.kiboko.App;
import com.mozilla.hackathon.kiboko.events.BatteryStateChanged;

/**
 * Created by mwadime on 6/7/2016.
 */
public class DSOBatteryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        int level = intent.getIntExtra("level", 0);

        if(level == 0){
            App.getBus().post(new BatteryStateChanged(true) );
        }
    }
}
