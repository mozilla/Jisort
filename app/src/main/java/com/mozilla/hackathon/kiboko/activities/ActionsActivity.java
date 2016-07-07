package com.mozilla.hackathon.kiboko.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ActionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onNewIntent(getIntent());
        finish();
    }

    protected void onNewIntent(Intent intent) {
        String action = intent.getAction();
        String data = intent.getDataString();

        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            String route = data.substring(data.lastIndexOf("/") + 1);;
            startActivity(new Intent());
        }
    }
}
