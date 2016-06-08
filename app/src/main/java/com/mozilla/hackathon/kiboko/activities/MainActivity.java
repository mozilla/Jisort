package com.mozilla.hackathon.kiboko.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import com.mozilla.hackathon.kiboko.R;
import com.mozilla.hackathon.kiboko.services.ChatHeadService;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 1234);
            }
        }
        else
        {
            Intent intent = new Intent(this, ChatHeadService.class);
            startService(intent);
        }
        Intent intent = new Intent(this, ChatHeadService.class);
        startService(intent);
        finish();

    }
}
