package com.mozilla.hackathon.kiboko.activities;

import android.content.Intent;
import android.content.SharedPreferences;
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

    SharedPreferences prefs = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);
        mDashboard = DashboardActivity.this;
        prefs = getSharedPreferences("com.mycompany.myAppName", MODE_PRIVATE);
        Analytics.add("Dashboard", "create");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        active = true;

        if (prefs.getBoolean("firstrun", true)) {
            prefs.edit().putBoolean("firstrun", false).commit();
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
