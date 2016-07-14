package com.mozilla.hackathon.kiboko.activities;

import android.support.v7.app.AppCompatActivity;

import com.mozilla.hackathon.kiboko.Analytics;
import com.mozilla.hackathon.kiboko.App;
import com.mozilla.hackathon.kiboko.events.ApplicationStateChanged;
import com.mozilla.hackathon.kiboko.services.DataBootstrapService;
import com.squareup.otto.Bus;

/**
 * Created by secretrobotron in July of 2016.
 */
public class DSOActivity extends AppCompatActivity {

    Bus bus = App.getBus();

    @Override
    protected void onResume()
    {
        DataBootstrapService.startDataBootstrapIfNecessary(this);
        bus.register(this);

        App.getBus().post(new ApplicationStateChanged(true));
        super.onResume();

        Analytics.add("Resumed DSO Activity", this.getClass().getSimpleName());
    }

    @Override
    protected void onPause()
    {
        App.getBus().post(new ApplicationStateChanged(false));
        bus.unregister(this);
        super.onPause();
    }
}
