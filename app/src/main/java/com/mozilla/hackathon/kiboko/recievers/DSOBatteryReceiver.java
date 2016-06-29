package com.mozilla.hackathon.kiboko.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.mozilla.hackathon.kiboko.App;
import com.mozilla.hackathon.kiboko.events.BatteryStateChanged;

/**
 * Created by mwadime on 6/7/2016.
 */
public class DSOBatteryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.unregisterReceiver(this);
        int rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int level = -1;
        if (rawlevel >= 0 && scale > 0) {
            level = (rawlevel * 100) / scale;
        }

        if(level <= 20){
            App.getBus().post(new BatteryStateChanged(true) );
        }
    }
}
