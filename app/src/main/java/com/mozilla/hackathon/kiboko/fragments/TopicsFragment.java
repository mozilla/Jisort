package com.mozilla.hackathon.kiboko.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.mozilla.hackathon.kiboko.R;
import com.mozilla.hackathon.kiboko.activities.TutorialSlideActivity;
import com.mozilla.hackathon.kiboko.adapters.TopicsAdapter;
import com.mozilla.hackathon.kiboko.models.Topic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TopicsFragment extends ListFragment {
    // List view
//    private GridView gridView;

    // Listview Adapter
    TopicsAdapter adapter;

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

//        gridView = (GridView) rootView.findViewById(R.id.dashboardGridView);

//        inputSearch = (EditText) rootView.findViewById(R.id.inputSearch);

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
        setListAdapter(adapter);

        // React to user clicks on item
//        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
//                                    long id) {
//                Intent intent = new Intent(getContext(), TutorialSlideActivity.class);
//                getContext().startActivity(intent);
//            }
//        });

//        inputSearch.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
//                // When user changed the Text
//                TopicsFragment.this.adapter.getFilter().filter(cs);
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable arg0) {
//
//            }
//        });
    }

}
