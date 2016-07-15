package com.mozilla.hackathon.kiboko.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mozilla.hackathon.kiboko.R;

/**
 * Created by secretrobotron on 7/15/16.
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void dismissWelcome(View view){
        Intent intent = new Intent(WelcomeActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
