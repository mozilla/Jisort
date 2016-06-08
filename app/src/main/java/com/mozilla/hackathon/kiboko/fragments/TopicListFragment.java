package com.mozilla.hackathon.kiboko.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.mozilla.hackathon.kiboko.R;
import com.mozilla.hackathon.kiboko.adapters.TopicListAdapter;
import com.mozilla.hackathon.kiboko.models.TopicItem;

import java.util.ArrayList;
import java.util.List;


public class TopicListFragment extends ListFragment {
    // Listview Adapter
    TopicListAdapter adapter;
    String decription = "Description of Topic. Description of Topic. Description of Topic. Description of Topic";

    // Search EditText
    Button view_icons;

    public TopicListFragment() {
    }

    public static TopicListFragment newInstance() {
        TopicListFragment fragment = new TopicListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topiclist_layout, container, false);

        return view;
    }

    private List<TopicItem> getTopics() {
        List<TopicItem> list = new ArrayList<TopicItem>();
        list.add(get(1, "Phone Topic 1",decription ));
        list.add(get(2, "Phone Topic 2",decription));
        list.add(get(3, "App Topic 3", decription));
        list.add(get(4, "Account Topic 4", decription));
        list.add(get(5, "Calendar Topic 5", decription));
        list.add(get(6, "Alarm Topic 6", decription));
        list.add(get(7, "Settings Topic 7", decription));
        list.add(get(8, "Search Topic 8", decription));

        return list;
    }

    private TopicItem get(int id, String s, String s2) {
        return new TopicItem(id, s, s2);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new TopicListAdapter(this.getActivity(), getTopics());
        setListAdapter(adapter);

        // React to user clicks on item
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
                                    long id) {


                // We know the View is a <extView so we can cast it
//                TextView clickedView = (TextView) view;

                Toast.makeText(getContext(), position, Toast.LENGTH_SHORT).show();

            }
        });

    }

}
