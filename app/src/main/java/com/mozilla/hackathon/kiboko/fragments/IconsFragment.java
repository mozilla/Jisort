package com.mozilla.hackathon.kiboko.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.mozilla.hackathon.kiboko.R;
import com.mozilla.hackathon.kiboko.adapters.IconsAdapter;
import com.mozilla.hackathon.kiboko.models.Topic;

import java.util.ArrayList;
import java.util.List;


public class IconsFragment extends Fragment {
    private GridView gridView;

    // Listview Adapter
    IconsAdapter adapter;

    // Search EditText
    Button view_icons;

    public IconsFragment() {
    }

    public static IconsFragment newInstance() {
        IconsFragment fragment = new IconsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_icons_layout, container, false);

        gridView = (GridView) rootView.findViewById(R.id.icons_gridview);

        view_icons = (Button) rootView.findViewById(R.id.icons_button);

        return rootView;
    }

    private List<Topic> getTopics() {
        List<Topic> list = new ArrayList<Topic>();
        list.add(get("wifi", "Connect to a Wi-Fi hotspot for internet access.", R.drawable.ic_wifi_tethering_white_48dp));
        list.add(get("phone", "Make a voice call.", R.drawable.ic_phone_white_48dp));
        list.add(get("app","app", R.drawable.ic_get_app_white_48dp));
        list.add(get("account", "Personal information used by your phone or an app.", R.drawable.ic_account_box_white_48dp));
        list.add(get("calendar", "Your daily schedule.", R.drawable.ic_perm_contact_calendar_white_48dp));
        list.add(get("alarm", "Wakes you up in the morning.", R.drawable.ic_alarm_white_48dp));
        list.add(get("settings", "Options and preferences for using your phone or an app.", R.drawable.ic_settings_applications_white_48dp));
        list.add(get("search", "Find something you need.", R.drawable.ic_search_white_48dp));
        list.add(get("location", "Your phone is requesting or using your location (based on GPS).", R.drawable.ic_location_on_white_48dp));
        list.add(get("power", "Turn your phone on or off.", R.drawable.ic_power_settings_new_white_48dp));
        list.add(get("battery", "The amount of power your phone has before it needs to be recharged.", R.drawable.ic_battery_full_white_48dp));
        list.add(get("bluetooth", "Connect to devices and other phones nearby.", R.drawable.ic_bluetooth_connected_white_48dp));
        list.add(get("email", "Send or receive email through GMail, Yahoo Mail, or otherwise.", R.drawable.ic_email_white_48dp));
        list.add(get("android", "The friendly system running on your phone.", R.drawable.ic_android_white_48dp));
        list.add(get("sdcard", "Storage space for photos/apps/documents that can be removed or replaced.", R.drawable.ic_sd_storage_white_48dp));
        list.add(get("delete", "Remove a photo/app/document from your phone.", R.drawable.ic_delete_white_48dp));
        list.add(get("search", "Find something you need.", R.drawable.ic_search_white_48dp));

        return list;
    }

    private Topic get(String t, String s, int i) {
        return new Topic(t, s, i);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new IconsAdapter(this.getActivity(), getTopics());
        gridView.setAdapter(adapter);
    }
}