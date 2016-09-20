package com.mozilla.hackathon.kiboko.events;

public class LocationStateChanged {
    public boolean mIsLocationEnabled;
    public LocationStateChanged(boolean mIsLocationEnabled)
    {
        this.mIsLocationEnabled = mIsLocationEnabled;
    }
    public boolean isLocationEnabled()
    {
        return this.mIsLocationEnabled;
    }
}
