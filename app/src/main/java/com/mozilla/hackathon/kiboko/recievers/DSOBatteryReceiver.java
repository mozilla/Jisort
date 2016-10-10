package com.mozilla.hackathon.kiboko.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.mozilla.hackathon.kiboko.utils.UiUtils;

public class DSOBatteryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.unregisterReceiver(this);
        int rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

        if (rawlevel <= 25) {
            UiUtils.showNotification(context, "Your phone battery is running low.", "battery");
        }
    }
}
