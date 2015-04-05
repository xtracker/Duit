package com.ms.duit;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jarzhao on 4/5/2015.
 */


public class SlideViewPager extends FrameLayout {


    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private LinearLayout mIndicatorContainer;
    private TextView mTitle;
    private ArrayList<ISlideViewPagerChangedListener> mSlideViewPagerChangedListeners;
    public SlideViewPager(Context context) {

        super(context);
        SetUpChildViews(context);
        setupIndicators();
    }

    public SlideViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        SetUpChildViews(context);
    }

    public SlideViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        SetUpChildViews(context);
    }

    private void SetUpChildViews(Context context) {
        inflate(context, R.layout.slide_view_pager, this);
        mViewPager = (ViewPager)findViewById(R.id.viewpager_slideshow);
        mTitle = (TextView)findViewById(R.id.viewpager_title);
        mIndicatorContainer = (LinearLayout)findViewById(R.id.viewpager_indicator);

    }

    public static Point getSize() {
        int width = DisplayUtils.getScreenWidth() - DisplayUtils.dip2px(DisplayUtils.getDimension(R.dimen.activity_horizontal_margin) * 2);
        int height = (int)(((float)width * 1.0) * 0.6 );
        return new Point(width, height);

    }

    private void setupIndicators() {
        int count = 4;
        if (mIndicatorContainer.getChildCount() > 0) {
            mIndicatorContainer.removeAllViews();
        }
        for (int i = 0; i < count; ++i) {
            View indicator = new View(this.getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(40, 10);
            layoutParams.setMargins(10, 10, 10, 10);
            indicator.setBackgroundColor(getContext().getResources().getColor(R.color.indicator_selected));
            mIndicatorContainer.addView(indicator, layoutParams);
        }
    }

    public void setAdapter(PagerAdapter pagerAdapter) {
        if (mViewPager != null) {
            mViewPager.setAdapter(pagerAdapter);
            mPagerAdapter = pagerAdapter;
            setupIndicators();
        }
    }

    public void AddSlideViewPagerChangedListener(ISlideViewPagerChangedListener slideViewPagerChangedListener) {
        if (mSlideViewPagerChangedListeners == null) {
            mSlideViewPagerChangedListeners = new ArrayList<ISlideViewPagerChangedListener>();
        }
        if (!mSlideViewPagerChangedListeners.contains(slideViewPagerChangedListener))
            mSlideViewPagerChangedListeners.add(slideViewPagerChangedListener);
    }

    public static interface ISlideViewPagerChangedListener {
        void OnSlideViewPagerChanged(int currentItem);
    }

    public static class SlideViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
