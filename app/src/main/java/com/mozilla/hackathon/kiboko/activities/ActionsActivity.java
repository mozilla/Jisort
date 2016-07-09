package com.mozilla.hackathon.kiboko.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

public class ActionsActivity extends AppCompatActivity {
    private static final Uri BASE_APP_URI = Uri.parse("android-app://mozilladso.com/actions/");
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
            String route = data.substring(data.lastIndexOf("/") + 1);

            switch (route){
                case "airplane_mode":
                    startActivity(new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS));
                    break;
                case "connect_wifi":
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    break;
                case "data":
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    break;
            }

        }
    }
}
