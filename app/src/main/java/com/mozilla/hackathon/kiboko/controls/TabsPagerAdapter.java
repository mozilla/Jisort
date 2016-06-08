package com.mozilla.hackathon.kiboko.controls;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mozilla.hackathon.kiboko.fragments.IconsFragment;
import com.mozilla.hackathon.kiboko.fragments.TopicsFragment;


public class TabsPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 2;
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return TopicsFragment.newInstance();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return IconsFragment.newInstance();
            default:
                return null;
        }
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "TAB " + (position + 1);
    }
}