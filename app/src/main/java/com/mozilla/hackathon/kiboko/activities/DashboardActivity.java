package com.mozilla.hackathon.kiboko.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mozilla.hackathon.kiboko.App;
import com.mozilla.hackathon.kiboko.R;
import com.mozilla.hackathon.kiboko.events.BatteryStateChanged;
import com.mozilla.hackathon.kiboko.events.LocationStateChanged;
import com.mozilla.hackathon.kiboko.events.NetworkStateChanged;
import com.mozilla.hackathon.kiboko.widgets.MessageCardView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * Created by Brian Mwadime on 06/06/2016.
 */
public class DashboardActivity extends FragmentActivity  {
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
        bus.post(new NetworkStateChanged(false) );
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
        bus.register(this);
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        bus.unregister(this);
    }

    // method that will be called when the device posts an event NetworkStateChanged
    @Subscribe
    public void onNetworkEvent(NetworkStateChanged event)
    {
        if (event.isInternetConnected())
        {
            final MessageCardView wifiCard = new MessageCardView(this);
            wifiCard.setText("You just connected to WIFI.");
            wifiCard.setButton(0, "Learn More", "tutorial_wifi", false, 1);
            wifiCard.setButton(1, "Ok, Got it", "quiz_wifi", false, 1);
            wifiCard.setListener(new MessageCardView.OnMessageCardButtonClicked() {
                @Override
                public void onMessageCardButtonClicked(final String tag) {
                    Toast.makeText(getApplicationContext(), tag, Toast.LENGTH_SHORT).show();
                    wifiCard.dismiss(true);
                    dashboard_summary.removeView(wifiCard);
                }
            });
            dashboard_summary.addView(wifiCard);
        }
    }

    @Subscribe
    public void onBatteryEvent(BatteryStateChanged event)
    {
        if (event.isBatteryStateEnabled())
        {
            final MessageCardView batteryCard = new MessageCardView(this);
            batteryCard.setText("Your phone battery is low. Charge your phone soon!");
            batteryCard.setButton(0, "Learn More", "tutorial_battery", false, 1);
            batteryCard.setButton(1, "Ok, Got it", "quiz_battery", false, 1);
            batteryCard.setListener(new MessageCardView.OnMessageCardButtonClicked() {
                @Override
                public void onMessageCardButtonClicked(final String tag) {
                    Toast.makeText(getApplicationContext(), tag, Toast.LENGTH_SHORT).show();
                    batteryCard.dismiss(true);
                    dashboard_summary.removeView(batteryCard);
                }
            });
            dashboard_summary.addView(batteryCard);
        }
    }

    @Subscribe
    public void onLocationEvent(LocationStateChanged event)
    {
        if (event.isLocationEnabled())
        {
            final MessageCardView locationCard = new MessageCardView(this);
            locationCard.setText("Your phone GPS is enabled.");
            locationCard.setButton(0, "Learn More", "tutorial_location", false, 1);
            locationCard.setButton(1, "Ok, Got it", "quiz_location", false, 1);
            locationCard.setListener(new MessageCardView.OnMessageCardButtonClicked() {
                @Override
                public void onMessageCardButtonClicked(final String tag) {
                    Toast.makeText(getApplicationContext(), tag, Toast.LENGTH_SHORT).show();
                    locationCard.dismiss(true);
                    dashboard_summary.removeView(locationCard);
                }
            });
            dashboard_summary.addView(locationCard);
        }
    }
}
