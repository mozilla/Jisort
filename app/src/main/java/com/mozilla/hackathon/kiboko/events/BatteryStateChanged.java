package com.mozilla.hackathon.kiboko.events;

/**
 * Created by mwadime on 6/11/2016.
 */
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
