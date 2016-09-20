package com.mozilla.hackathon.kiboko.events;

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
