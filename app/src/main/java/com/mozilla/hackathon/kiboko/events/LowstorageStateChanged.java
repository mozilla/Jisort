package com.mozilla.hackathon.kiboko.events;

/**
 * Created by mwadime on 6/11/2016.
 */
public class LowstorageStateChanged {
    public boolean mIsLowstorageStateEnabled;
    public LowstorageStateChanged(boolean mIsLowstorageStateEnabled)
    {
        this.mIsLowstorageStateEnabled = mIsLowstorageStateEnabled;
    }
    public boolean isLowstorageStateChanged()
    {
        return this.mIsLowstorageStateEnabled;
    }
}
