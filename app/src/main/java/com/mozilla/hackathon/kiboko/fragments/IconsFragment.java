package com.mozilla.hackathon.kiboko.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

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
        list.add(get("wifi", R.drawable.ic_wifi_tethering_black_48dp));
        list.add(get("phone", R.drawable.ic_phone_black_48dp));
        list.add(get("app", R.drawable.ic_get_app_black_48dp));
        list.add(get("account", R.drawable.ic_account_box_black_48dp));
        list.add(get("calendar", R.drawable.ic_perm_contact_calendar_black_48dp));
        list.add(get("alarm", R.drawable.ic_alarm_black_48dp));
        list.add(get("settings", R.drawable.ic_settings_applications_black_48dp));
        list.add(get("search", R.drawable.ic_search_black_48dp));

        return list;
    }

    private Topic get(String s, int i) {
        return new Topic(s, i);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new IconsAdapter(this.getActivity(), getTopics());
        gridView.setAdapter(adapter);

        // React to user clicks on item
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
                                    long id) {


                // We know the View is a <extView so we can cast it
//                TextView clickedView = (TextView) view;

                Toast.makeText(getContext(), position, Toast.LENGTH_SHORT).show();

            }
        });

    }

}
