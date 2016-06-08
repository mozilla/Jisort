package com.mozilla.hackathon.kiboko.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.mozilla.hackathon.kiboko.R;

/**
 * Created by Audrey on 06/06/2016.
 */
public class DashboardActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);
    }

    // Play current song
    public void findIconsClicked(View view){
        Intent dashboardIntent = new Intent(this, FindIconsActivity.class);
        startActivity(dashboardIntent);
    }

    public void screenshotsClicked(View view){

    }


}
