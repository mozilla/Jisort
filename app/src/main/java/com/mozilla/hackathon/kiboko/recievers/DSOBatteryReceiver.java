package com.mozilla.hackathon.kiboko.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

/**
 * Created by mwadime on 6/7/2016.
 */
public class DSOBatteryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // context.unregisterReceiver(this);
        int rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

        if(rawlevel <= 25){
        }
    }
}
