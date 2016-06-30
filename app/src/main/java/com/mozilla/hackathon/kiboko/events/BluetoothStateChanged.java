package com.mozilla.hackathon.kiboko.events;

/**
 * Created by mwadime on 6/11/2016.
 */
public class BluetoothStateChanged {
    public boolean mIsBluetoothStateEnabled;
    public BluetoothStateChanged(boolean mIsBluetoothStateEnabled)
    {
        this.mIsBluetoothStateEnabled = mIsBluetoothStateEnabled;
    }
    public boolean isBluetoothStateEnabled()
    {
        return this.mIsBluetoothStateEnabled;
    }
}
