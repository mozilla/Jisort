package com.mozilla.hackathon.kiboko.recievers;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by mwadime on 6/7/2016.
 */
public class DSOChangedBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                -1);

        switch(state){
            case BluetoothAdapter.STATE_ON:

                break;
        }

    }
}
