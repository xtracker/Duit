package com.ms.duit.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ms.duit.R;
import com.ms.duit.ui.slidetabs.SlidingTabLayout;

/**
 * Created by jarzhao on 4/11/2015.
 */
public class ContentFragment extends Fragment {

    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;
    public ContentFragment() {}

    public static ContentFragment newInstance(int position) {
        return new ContentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_content, container, false);

        mViewPager = (ViewPager)rootView.findViewById(R.id.pager_main);
        mViewPager.setAdapter(new MainPagerAdapter(getActivity().getSupportFragmentManager(), getActivity()));
        mSlidingTabLayout = (SlidingTabLayout) rootView.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.indicator_selected));
        return rootView;
    }
}
