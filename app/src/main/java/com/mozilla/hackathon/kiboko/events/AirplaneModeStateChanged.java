package com.mozilla.hackathon.kiboko.events;

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
