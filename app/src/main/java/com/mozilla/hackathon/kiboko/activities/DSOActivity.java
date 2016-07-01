package com.mozilla.hackathon.kiboko.activities;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.mozilla.hackathon.kiboko.R;

import com.squareup.otto.Bus;

import com.mozilla.hackathon.kiboko.App;
import com.mozilla.hackathon.kiboko.events.ApplicationStateChanged;

/**
 * Created by Brian Mwadime on 06/06/2016.
 */
public class DSOActivity extends AppCompatActivity {

    Bus bus = App.getBus();

    @Override
    protected void onResume()
    {
        App.getBus().post(new ApplicationStateChanged(true));
        super.onResume();
        System.out.println("Resume");
    }

    @Override
    protected void onPause()
    {
        App.getBus().post(new ApplicationStateChanged(false));
        super.onPause();
        System.out.println("Pause");
    }
}
