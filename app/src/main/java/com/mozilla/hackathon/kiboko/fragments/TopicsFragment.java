package com.mozilla.hackathon.kiboko.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.mozilla.hackathon.kiboko.R;
import com.mozilla.hackathon.kiboko.adapters.TopicsAdapter;
import com.mozilla.hackathon.kiboko.models.Topic;
import com.mozilla.hackathon.kiboko.services.ChatHeadService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TopicsFragment extends ListFragment implements CompoundButton.OnCheckedChangeListener {
    // List view
//    private GridView gridView;

    // Listview Adapter
    TopicsAdapter adapter;
    private LinearLayout listFooterView;
    private SwitchCompat toggleSwitch = null;
    // Search EditText
//    EditText inputSearch;


    // ArrayList for Listview
    ArrayList<HashMap<String, String>> topicsList;

    public TopicsFragment() {
    }

    public static TopicsFragment newInstance() {
        TopicsFragment fragment = new TopicsFragment();
        Bundle args = new Bundle();
//        args.putInt(ARG_PAGE_NUMBER, 1);
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
        list.add(get("wifi", "Connect to WiFi", R.drawable.circular_wifi));
        list.add(get("data", "Find out how much data you are using", R.drawable.circular_money));
        list.add(get("playstore", "Find new apps in the App Store", R.drawable.circular_googleplay));
        list.add(get("storage", "Free up memory for new apps", R.drawable.circular_sdcard));
        list.add(get("broken", "Fix broken images and videos", R.drawable.circular_badimage));
        list.add(get("icons", "Learn what different icons mean", R.drawable.circular_help));

        return list;
    }

    private Topic get(String t, String s, int i) {
        return new Topic(t, s, i);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new TopicsAdapter(this.getActivity(), getTopics());

        // Inflate footer view
        listFooterView = (LinearLayout) LayoutInflater.from(this.getActivity()).inflate(R.layout.dashboard_footer_view, null);
        toggleSwitch = (SwitchCompat) listFooterView.findViewById(R.id.toggleSwitch);

        //attach a listener to check for changes in state
        toggleSwitch.setOnCheckedChangeListener(this);

        setListAdapter(adapter);

        getListView().addFooterView(listFooterView);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.toggleSwitch:
                if(!isChecked){
                    getContext().stopService(new Intent(getContext(), ChatHeadService.class));
                }else{
                    getContext().startService(new Intent(getContext(), ChatHeadService.class));

                }
                break;

            default:
                break;
        }
    }

}
