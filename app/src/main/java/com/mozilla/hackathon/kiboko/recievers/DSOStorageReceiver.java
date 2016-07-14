package com.mozilla.hackathon.kiboko.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by mwadime on 6/7/2016.
 */
public class DSOStorageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().matches(Intent.ACTION_DEVICE_STORAGE_LOW)){

        }
    }
}
