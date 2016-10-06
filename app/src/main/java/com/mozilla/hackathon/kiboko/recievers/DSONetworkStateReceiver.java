package com.mozilla.hackathon.kiboko.recievers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mozilla.hackathon.kiboko.utils.NetworkUtils;
import com.mozilla.hackathon.kiboko.utils.UiUtils;

public class DSONetworkStateReceiver extends BroadcastReceiver {
    private final static Integer NOTIFICATION_ID = 123;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (wifiConnected(context)) {
            UiUtils.showNotification(context, "Your device is now connected to Wi-Fi.", "wifi", NOTIFICATION_ID);
        } else {
            ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(NOTIFICATION_ID);
        }
    }

    private boolean wifiConnected(Context context) {
        NetworkInfo networkInfo = NetworkUtils.getActiveNetworkInfo(context);
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI && networkInfo.isConnected();
    }
}
