package com.mozilla.hackathon.kiboko.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.mozilla.hackathon.kiboko.utilities.NetworkUtils;
import com.mozilla.hackathon.kiboko.App;

import com.mozilla.hackathon.kiboko.events.NetworkStateChanged;

/**
 * Created by mwadime on 6/7/2016.
 */
    public class DSONetworkStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String status = NetworkUtils.getConnectivityStatusString(context);

        NetworkInfo ni=(NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
        if(ni!=null && ni.getState()==NetworkInfo.State.CONNECTED)
        {
            App.getBus().post(new NetworkStateChanged(true) );
            // there is Internet connection
        } else

            App.getBus().post(new NetworkStateChanged(false) );
        if(intent .getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,Boolean.FALSE))
        {
            // no Internet connection, send network state changed
            App.getBus().post(new NetworkStateChanged(false) );
        }
    }
}
