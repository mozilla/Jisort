package com.mozilla.hackathon.kiboko.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mozilla.hackathon.kiboko.utils.NetworkUtils;
import com.mozilla.hackathon.kiboko.utils.UiUtils;

public class DSOAirplaneModeReceiver extends BroadcastReceiver {
    private final static Integer NOTIFICATION_ID = 124;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (NetworkUtils.isAirplaneModeActive(context)) {
            UiUtils.showNotification(context, "You have enabled Airplane mode. Your device is now offline.", "airplane_mode", NOTIFICATION_ID);
        } else {
            UiUtils.cancelNotification(context, NOTIFICATION_ID);
        }
    }
}
