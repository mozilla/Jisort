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
        list.add(get("Connectivity", R.drawable.ic_wifi_tethering_black_48dp));
        list.add(get("Phone Usage", R.drawable.ic_phone_black_48dp));
        list.add(get("Apps", R.drawable.ic_get_app_black_48dp));
        list.add(get("Accounts", R.drawable.ic_account_box_black_48dp));

        return list;
    }

    private Topic get(String s, int i) {
        return new Topic(s, i);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new TopicsAdapter(this.getActivity(), getTopics());
        setListAdapter(adapter);

        // React to user clicks on item
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
                                    long id) {
//                Toast.makeText(context, "You Clicked "+ topic.getName(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), TutorialSlideActivity.class);
                getContext().startActivity(intent);
            }
        });

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
