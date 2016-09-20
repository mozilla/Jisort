package com.mozilla.hackathon.kiboko.activities;

import android.support.v7.app.AppCompatActivity;

import com.mozilla.hackathon.kiboko.Analytics;
import com.mozilla.hackathon.kiboko.services.DataBootstrapService;

/**
 * Created by secretrobotron in July of 2016.
 */
public class DSOActivity extends AppCompatActivity {
    @Override
    protected void onResume() {
        DataBootstrapService.startDataBootstrapIfNecessary(this);
        super.onResume();
        Analytics.add("Resumed DSO Activity", this.getClass().getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
