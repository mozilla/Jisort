package com.mozilla.hackathon.kiboko.activities;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mozilla.hackathon.kiboko.App;
import com.mozilla.hackathon.kiboko.R;
import com.mozilla.hackathon.kiboko.events.BatteryStateChanged;
import com.mozilla.hackathon.kiboko.events.LocationStateChanged;
import com.mozilla.hackathon.kiboko.events.NetworkStateChanged;
import com.mozilla.hackathon.kiboko.services.ChatHeadService;
import com.mozilla.hackathon.kiboko.services.DataBootstrapService;
import com.mozilla.hackathon.kiboko.utilities.Utils;
import com.mozilla.hackathon.kiboko.widgets.MessageCardView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class DashboardActivity extends AppCompatActivity {
    public static int OVERLAY_PERMISSION_REQ_CODE_CHATHEAD = 1234;
    public static int OVERLAY_PERMISSION_REQ_CODE_CHATHEAD_MSG = 5678;
    public static boolean active = false;
    public static FragmentActivity mDashboard;
    private String TAG = MainActivity.class.getSimpleName();
    LinearLayout dashboard_summary;
    private static final int ANIM_DURATION = 250;
    private static final int CARD_DISMISS_ACTION_DELAY = MessageCardView.ANIM_DURATION - 50;
    Bus bus = App.getBus();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);
        dashboard_summary = (LinearLayout) findViewById(R.id.dashboard_summary);
        mDashboard = DashboardActivity.this;
        bus.post(new NetworkStateChanged(false) );

        if(isServiceRunning()){
            if(Utils.canDrawOverlays(this))
                startOverlayService();
            else{
                requestPermission(OVERLAY_PERMISSION_REQ_CODE_CHATHEAD);
            }
        }
    }

    // Play current song
    public void findIconsClicked(View view){
        Intent dashboardIntent = new Intent(this, FindIconsActivity.class);
        startActivity(dashboardIntent);
    }

    public void screenshotsClicked(View view){

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        DataBootstrapService.startDataBootstrapIfNecessary(this);
        bus.register(this);
        active = true;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        bus.unregister(this);
        active = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        active = false;
    }

    // method that will be called when the device posts an event NetworkStateChanged
    @Subscribe
    public void onNetworkEvent(NetworkStateChanged event)
    {
        // Clear previous suggested topic
        dashboard_summary.removeAllViews();

        if (event.isInternetConnected())
        {
            final MessageCardView wifiCard = new MessageCardView(this);
            wifiCard.overrideBackground(getResources().getColor(R.color.colorTextDisabled));
            wifiCard.setText("You just connected to WIFI.");
            wifiCard.setButton(0, "Learn More", "tutorial_wifi", false, 1);
            wifiCard.setButton(1, "Ok, Got it", "cancel", false, 1);
            wifiCard.setListener(new MessageCardView.OnMessageCardButtonClicked() {
                @Override
                public void onMessageCardButtonClicked(final String tag) {
                    Toast.makeText(getApplicationContext(), tag, Toast.LENGTH_SHORT).show();
                    wifiCard.dismiss(true);
                    dashboard_summary.removeView(wifiCard);

                    if(tag.equals("cancel")){
                        return;
                    }

                    Intent dashboardIntent = new Intent(DashboardActivity.this, TutorialSlideActivity.class);
                    dashboardIntent.putExtra("topic","wifi");
                    startActivity(dashboardIntent);
                }
            });
            dashboard_summary.addView(wifiCard);
        }
    }

    @Subscribe
    public void onBatteryEvent(BatteryStateChanged event)
    {
        // Clear previous suggested topic
        dashboard_summary.removeAllViews();

        if (event.isBatteryStateEnabled())
        {
            final MessageCardView batteryCard = new MessageCardView(this);
            batteryCard.overrideBackground(getResources().getColor(R.color.colorTextDisabled));
            batteryCard.setText("Your phone battery is low. Charge your phone soon!");
            batteryCard.setButton(0, "Learn More", "tutorial_battery", false, 1);
            batteryCard.setButton(1, "Ok, Got it", "cancel", false, 1);
            batteryCard.setListener(new MessageCardView.OnMessageCardButtonClicked() {
                @Override
                public void onMessageCardButtonClicked(final String tag) {
                    Toast.makeText(getApplicationContext(), tag, Toast.LENGTH_SHORT).show();
                    batteryCard.dismiss(true);
                    dashboard_summary.removeView(batteryCard);

                    if(tag.equals("cancel")){
                        return;
                    }

                    Intent dashboardIntent = new Intent(DashboardActivity.this, TutorialSlideActivity.class);
                    dashboardIntent.putExtra("topic","battery");
                    startActivity(dashboardIntent);
                }
            });
            dashboard_summary.addView(batteryCard);
        }
    }

    @Subscribe
    public void onLocationEvent(LocationStateChanged event)
    {
        // Clear previous suggested topic
        dashboard_summary.removeAllViews();

        if (event.isLocationEnabled())
        {
            final MessageCardView locationCard = new MessageCardView(this);
            locationCard.overrideBackground(getResources().getColor(R.color.colorTextDisabled));
            locationCard.setText("Your phone GPS is enabled.");
            locationCard.setButton(0, "Learn More", "tutorial_location", false, 1);
            locationCard.setButton(1, "Ok, Got it", "cancel", false, 1);
            locationCard.setListener(new MessageCardView.OnMessageCardButtonClicked() {
                @Override
                public void onMessageCardButtonClicked(final String tag) {
                    Toast.makeText(getApplicationContext(), tag, Toast.LENGTH_SHORT).show();
                    locationCard.dismiss(true);
                    dashboard_summary.removeView(locationCard);

                    if(tag.equals("cancel")){
                        return;
                    }

                    Intent dashboardIntent = new Intent(DashboardActivity.this, TutorialSlideActivity.class);
                    dashboardIntent.putExtra("topic","location");
                    startActivity(dashboardIntent);
                }
            });
            dashboard_summary.addView(locationCard);
        }
    }

    private void startOverlayService(){
        startService(new Intent(this, ChatHeadService.class));
    }

    private void needPermissionDialog(final int requestCode){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You need to allow permission");
        builder.setPositiveButton("OK",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermission(requestCode);
                    }
                });
        builder.setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void requestPermission(int requestCode){
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OVERLAY_PERMISSION_REQ_CODE_CHATHEAD) {
            if (!Utils.canDrawOverlays(this)) {
                needPermissionDialog(requestCode);
            }else{
                startOverlayService();
            }

        }else if(requestCode == OVERLAY_PERMISSION_REQ_CODE_CHATHEAD_MSG){
            if (!Utils.canDrawOverlays(this)) {
                needPermissionDialog(requestCode);
            }else{
//                showChatHeadMsg();

            }

        }

    }

    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if("com.mozilla.hackathon.kiboko.services.ChatHeadService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
