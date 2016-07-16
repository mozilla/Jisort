package com.mozilla.hackathon.kiboko.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.mozilla.hackathon.kiboko.Analytics;
import com.mozilla.hackathon.kiboko.R;
import com.mozilla.hackathon.kiboko.settings.SettingsUtils;

public class DashboardActivity extends DSOActivity {
    public static boolean active = false;
    public static FragmentActivity mDashboard;
    private String TAG = DashboardActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);
        mDashboard = DashboardActivity.this;
        Analytics.add("Dashboard", "create");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        active = true;

        if (!SettingsUtils.isWelcomeDone(this)) {
            // Mark Welcome screen as shown
            SettingsUtils.markWelcomeDone(this);
            Intent intent = new Intent(DashboardActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        active = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        active = false;
        Analytics.add("Dashboard", "destroy");
        Analytics.flush();
    }
}
