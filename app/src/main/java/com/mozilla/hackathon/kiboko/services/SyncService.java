package com.mozilla.hackathon.kiboko.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.mozilla.hackathon.kiboko.sync.SyncAdapter;

/**
 * Service that handles sync. It simply instantiates a SyncAdapter and returns its IBinder.
 */
public class SyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static SyncAdapter sSyncAdapter = null;

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null) {
                sSyncAdapter = new SyncAdapter(getApplicationContext(), false);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}