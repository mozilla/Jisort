package com.mozilla.hackathon.kiboko.events;

/**
 * Created by mwadime on 6/11/2016.
 */
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
