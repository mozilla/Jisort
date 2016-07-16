package com.mozilla.hackathon.kiboko.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mozilla.hackathon.kiboko.App;

public class DSOAirplaneModeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isAirplaneModeOn = intent.getBooleanExtra("state", false);
        if(isAirplaneModeOn){
            App.createNotification("You have enabled Airplane mode. Your device is now offline.", "airplane_mode");
        }
    }
}
