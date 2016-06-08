package com.mozilla.hackathon.kiboko.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by mwadime on 6/7/2016.
 */
public class DSOLocationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
            Toast.makeText(context, "in android.location.PROVIDERS_CHANGED",
                    Toast.LENGTH_SHORT).show();
//            Intent pushIntent = new Intent(context, LocalService.class);
//            context.startService(pushIntent);
        }
    }
}
