package com.mozilla.hackathon.kiboko.activities;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends DSOActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
