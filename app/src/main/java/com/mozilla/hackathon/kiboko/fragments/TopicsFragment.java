package com.mozilla.hackathon.kiboko.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import com.mozilla.hackathon.kiboko.Analytics;
import com.mozilla.hackathon.kiboko.App;
import com.mozilla.hackathon.kiboko.R;
import com.mozilla.hackathon.kiboko.adapters.TopicsAdapter;
import com.mozilla.hackathon.kiboko.models.Topic;
import com.mozilla.hackathon.kiboko.services.ChatHeadService;
import com.mozilla.hackathon.kiboko.utilities.Utils;
import java.util.ArrayList;
import java.util.List;

public class TopicsFragment extends ListFragment implements CompoundButton.OnCheckedChangeListener {
    public static int OVERLAY_PERMISSION_REQ_CODE_CHATHEAD = 1234;
    TopicsAdapter adapter;
    private LinearLayout listFooterView;
    private SwitchCompat toggleSwitch = null;

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
        list.add(get("wifi", "Wi-Fi ni Noma!", R.drawable.circular_wifi));
        list.add(get("connect_wifi", "Connecting to Wi-Fi", R.drawable.circular_wifi));
        list.add(get("tracking_data", "How much data are you using?", R.drawable.circular_cell));
        list.add(get("data", "Using your data wisely", R.drawable.circular_money));
        list.add(get("playstore", "Find new apps!", R.drawable.circular_googleplay));
        list.add(get("storage", "Free up memory for new apps", R.drawable.circular_sdcard));
        list.add(get("icons", "What is that icon?", R.drawable.circular_help));
        list.add(get("airplane_mode", "Using Airplane Mode", R.drawable.circular_airplane));
        list.add(get("accounts_passwords", "Strengthen your Passwords!", R.drawable.circular_account));
        return list;
    }

    private Topic get(String t, String s, int i) {
        return new Topic(t, s, i);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new TopicsAdapter(getContext(), getTopics());
        // Inflate footer view
        listFooterView = (LinearLayout) LayoutInflater.from(this.getActivity()).inflate(R.layout.dashboard_footer_view, null);
        toggleSwitch = (SwitchCompat) listFooterView.findViewById(R.id.toggleSwitch);
        toggleSwitch.setChecked(App.isServiceRunning());
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
                    Analytics.add("TopicsFragment::FABSwitch", "off");
                }else{
                    Analytics.add("TopicsFragment::FABSwitch", "on");
                    if(!App.isServiceRunning()){
                        if(Utils.canDrawOverlays(getContext()))
                            startOverlayService();
                        else{
                            requestPermission(OVERLAY_PERMISSION_REQ_CODE_CHATHEAD);
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    private void needPermissionDialog(final int requestCode){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Jisort needs permissions.");
        builder.setPositiveButton("OK",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermission(requestCode);
                    }
                });
        builder.setCancelable(false);
        builder.show();
    }

    private void startOverlayService(){
        getContext().startService(new Intent(getContext(), ChatHeadService.class));
    }

    private void requestPermission(int requestCode){
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getContext().getPackageName()));
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE_CHATHEAD) {
            if (!Utils.canDrawOverlays(getContext())) {
                needPermissionDialog(requestCode);
            }else{
                startOverlayService();
            }
        }
    }
}
