package com.mozilla.hackathon.kiboko.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.mozilla.hackathon.kiboko.services.ChatHeadService;

/**
 * Created by mwadime on 6/7/2016.
 */
public class DSOInstalledBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = null;
        if (intent != null) {
            action = intent.getAction();
        }

        if (action != null && Intent.ACTION_PACKAGE_ADDED.equals(action)) {
            String dataString = intent.getDataString();
            if (dataString != null
                    && dataString.equals("com.mozilla.hackathon.kiboko")) {

                if(Build.VERSION.SDK_INT >= 23) {
                    if (!Settings.canDrawOverlays(context)) {
                        intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:" + context.getPackageName()));
                        context.getApplicationContext().startActivity(intent); //, 1234

                    }
                }
                else
                {
                    intent = new Intent(context, ChatHeadService.class);
                    context.startService(intent);
                }
                intent = new Intent(context, ChatHeadService.class);
                context.startService(intent);

            }
        }
    }
}
