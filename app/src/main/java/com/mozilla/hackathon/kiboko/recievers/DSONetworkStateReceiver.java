package com.mozilla.hackathon.kiboko.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.mozilla.hackathon.kiboko.utilities.NetworkUtils;

/**
 * Created by mwadime on 6/7/2016.
 */
    public class DSONetworkStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String status = NetworkUtils.getConnectivityStatusString(context);

        Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
    }
}
