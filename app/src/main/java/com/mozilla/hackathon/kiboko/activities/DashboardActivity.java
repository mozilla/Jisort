package com.mozilla.hackathon.kiboko.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.LinearLayout;

import com.mozilla.hackathon.kiboko.Analytics;
import com.mozilla.hackathon.kiboko.R;

public class DashboardActivity extends DSOActivity {
    public static boolean active = false;
    public static FragmentActivity mDashboard;
    private String TAG = DashboardActivity.class.getSimpleName();
    LinearLayout dashboard_summary;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);
        dashboard_summary = (LinearLayout) findViewById(R.id.dashboard_summary);
        mDashboard = DashboardActivity.this;
        Analytics.add("Dashboard", "create");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        active = true;
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
