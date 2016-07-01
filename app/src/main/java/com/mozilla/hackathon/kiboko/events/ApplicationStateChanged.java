package com.mozilla.hackathon.kiboko.events;

/**
 * Created by secretrobotron on 7/1/2016.
 */
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
