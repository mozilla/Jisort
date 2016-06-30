package com.mozilla.hackathon.kiboko.events;

/**
 * Created by mwadime on 6/11/2016.
 */
public class AirplaneModeStateChanged {
    public boolean mIsAirplaneModeStateEnabled;
    public AirplaneModeStateChanged(boolean mIsAirplaneModeStateEnabled)
    {
        this.mIsAirplaneModeStateEnabled = mIsAirplaneModeStateEnabled;
    }
    public boolean isAirplaneModeStateEnabled()
    {
        return this.mIsAirplaneModeStateEnabled;
    }
}
