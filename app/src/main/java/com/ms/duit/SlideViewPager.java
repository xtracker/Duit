package com.ms.duit;

import android.content.Context;
import android.graphics.Point;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ms.duit.utils.bitmap.BitmapHelper;

import java.util.ArrayList;

/**
 * Created by jarzhao on 4/5/2015.
 */

public class SlideViewPager extends FrameLayout {


    private WrapContentViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private LinearLayout mIndicatorContainer;
    private TextView mTitle;
    private ArrayList<ISlideViewPagerChangedListener> mSlideViewPagerChangedListeners;
    public SlideViewPager(Context context) {

        super(context);
        SetUpChildViews(context);
        //setupIndicators();
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
        mViewPager = (WrapContentViewPager)findViewById(R.id.viewpager_slideshow);
        mTitle = (TextView)findViewById(R.id.viewpager_title);
        mIndicatorContainer = (LinearLayout)findViewById(R.id.viewpager_indicator);
    }

    public static Point getSize() {
        int width = DisplayUtils.getScreenWidth() - DisplayUtils.dip2px(DisplayUtils.getDimension(R.dimen.activity_horizontal_margin) * 2);
        int height = (int)(((float)width * 1.0) * 0.5 );
        return new Point(width, height);
    }

    private void setupIndicators() {
        int count = mPagerAdapter.getCount();
        if (mIndicatorContainer.getChildCount() > 0) {
            mIndicatorContainer.removeAllViews();
        }
        for (int i = 0; i < count; ++i) {
            View indicator = new View(this.getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 5);
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

        private final Context mContext;
        private String[] mDataSet;
        private String imagePath;
        public SlideViewPagerAdapter(Context context, String[] dataSet) {
            super();
            mContext = context;
            mDataSet = dataSet;
            imagePath = Environment.getExternalStorageDirectory().getPath() + "/DCIM/";
        }

        @Override
        public int getCount() {
            return mDataSet.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            container.removeViewAt(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Point x = getSize();

            ImageView imageView = new ImageView(mContext);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, x.y);
            imageView.setLayoutParams(new LayoutParams(x.x, x.y));
            BitmapHelper.loadBitmap(imageView, imagePath + mDataSet[position], x.x, x.y);
            imageView.setImageResource(R.drawable.drawer_item_background);

            container.addView(imageView, position, layoutParams);
            return imageView;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
