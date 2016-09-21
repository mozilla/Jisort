package com.mozilla.hackathon.kiboko.activities;

import android.support.v7.app.AppCompatActivity;

import com.mozilla.hackathon.kiboko.Analytics;
import com.mozilla.hackathon.kiboko.services.DataBootstrapService;

public class DSOActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        DataBootstrapService.startDataBootstrapIfNecessary(this);
        super.onResume();
        Analytics.add("Resumed DSO Activity", this.getClass().getSimpleName());
    }
}
