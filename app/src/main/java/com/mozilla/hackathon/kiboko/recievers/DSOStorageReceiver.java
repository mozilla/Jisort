package com.mozilla.hackathon.kiboko.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mozilla.hackathon.kiboko.utils.UiUtils;

public class DSOStorageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().matches(Intent.ACTION_DEVICE_STORAGE_LOW)) {
            UiUtils.showNotification(context, "Your device is running out of storage.", "storage");
        }
    }
}
