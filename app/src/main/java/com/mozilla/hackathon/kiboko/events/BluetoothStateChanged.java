package com.mozilla.hackathon.kiboko.events;

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
