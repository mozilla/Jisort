package com.mozilla.hackathon.kiboko.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mozilla.hackathon.kiboko.App;
import com.mozilla.hackathon.kiboko.events.AirplaneModeStateChanged;

public class DSOAirplaneModeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isAirplaneModeOn = intent.getBooleanExtra("state", false);
        if(isAirplaneModeOn){
            App.getBus().post(new AirplaneModeStateChanged(true));
        }
    }
}
