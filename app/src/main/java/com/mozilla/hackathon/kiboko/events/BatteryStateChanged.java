package com.mozilla.hackathon.kiboko.events;

public class BatteryStateChanged {
    public boolean mIsBatteryStateEnabled;
    public BatteryStateChanged(boolean mIsBatteryStateEnabled)
    {
        this.mIsBatteryStateEnabled = mIsBatteryStateEnabled;
    }
    public boolean isBatteryStateEnabled()
    {
        return this.mIsBatteryStateEnabled;
    }
}
