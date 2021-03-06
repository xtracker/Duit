package com.ms.duit.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by jarzhao on 4/5/2015.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;

    public MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "iOS开发指南" + (position + 1);
    }

    @Override
    public Fragment getItem(int i) {
        return new MainActivity.PlaceholderFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
