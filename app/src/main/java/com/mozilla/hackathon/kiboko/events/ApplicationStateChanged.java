package com.mozilla.hackathon.kiboko.events;

public class ApplicationStateChanged {
    public boolean mIsOpen;
    public ApplicationStateChanged(boolean isOpen)
    {
        mIsOpen = isOpen;
    }
    public boolean isOpen()
    {
        return mIsOpen;
    }
}
