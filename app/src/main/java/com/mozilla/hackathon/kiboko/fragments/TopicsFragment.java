package com.mozilla.hackathon.kiboko.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.mozilla.hackathon.kiboko.Analytics;
import com.mozilla.hackathon.kiboko.R;
import com.mozilla.hackathon.kiboko.adapters.TopicsAdapter;
import com.mozilla.hackathon.kiboko.models.Topic;
import com.mozilla.hackathon.kiboko.settings.SettingsUtils;

import java.util.ArrayList;
import java.util.List;

public class TopicsFragment extends ListFragment implements CompoundButton.OnCheckedChangeListener {
    public static int OVERLAY_PERMISSION_REQ_CODE_CHATHEAD = 1234;
    TopicsAdapter adapter;
    private LinearLayout listFooterView;
    private SwitchCompat toggleSwitch, funmodeSwitch = null;

    public TopicsFragment() {
    }

    public static TopicsFragment newInstance() {
        TopicsFragment fragment = new TopicsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_topics_layout, container, false);
        return rootView;
    }

    private List<Topic> getTopics() {
        List<Topic> list = new ArrayList<Topic>();
        list.add(get("icons", "Play the Icon Game!", R.drawable.circular_blue_help));
        list.add(get("wifi", "Wi-Fi ni Noma!", R.drawable.circular_wifi));
        list.add(get("connect_wifi", "Connecting to WiFi", R.drawable.circular_wifi));
        list.add(get("tracking_data", "Data Consumption", R.drawable.circular_cell));
        list.add(get("data", "Using Data Wisely", R.drawable.circular_money));
        list.add(get("playstore", "Downloading new Apps", R.drawable.circular_googleplay));
        list.add(get("storage", "Freeing up Storage", R.drawable.circular_sdcard));
        list.add(get("airplane_mode", "Using Airplane Mode", R.drawable.circular_airplane));
        list.add(get("accounts_passwords", "Accounts and Passwords", R.drawable.circular_account));
        return list;
    }

    private Topic get(String t, String s, int i) {
        return new Topic(t, s, i);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new TopicsAdapter(getContext(), getTopics());
        setListAdapter(adapter);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.funmodeSwitch:
                if(!isChecked){
                    Analytics.add("TopicsFragment::FunMode Switch", "Off");
                }else{
                    Analytics.add("TopicsFragment::FunMode Switch", "On");
                }
                SettingsUtils.setFunMode(getContext(), isChecked);
                break;
            default:
                break;
        }
    }
}
