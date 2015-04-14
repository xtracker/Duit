package com.ms.duit.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ms.duit.R;
import com.ms.duit.utils.DisplayUtils;
import com.ms.duit.utils.SysUtils;
import com.ms.duit.utils.bitmap.BitmapHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by jarzhao on 4/5/2015.
 */

public class SlideViewPager extends FrameLayout {


    private WrapContentViewPager mViewPager;
    private SlideViewPagerAdapter mPagerAdapter;
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

        mViewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = mViewPager.getWidth();
                if (mPagerAdapter == null) return;
                mPagerAdapter.setPagerRealWidth(width);
                mPagerAdapter.notifyDataSetChanged();
                if (SysUtils.hasJellyBean())
                    mViewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                else
                    mViewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

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

    public void setAdapter(SlideViewPagerAdapter pagerAdapter) {
        if (mViewPager != null) {
            mViewPager.setAdapter(pagerAdapter);
            mPagerAdapter = pagerAdapter;
            setupIndicators();
        }
    }

    public SlideViewPagerAdapter getAdapter() {
        return mPagerAdapter;
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
        private int mWidth = -1;
        public SlideViewPagerAdapter(Context context, String[] dataSet) {
            super();
            mContext = context;
            mDataSet = dataSet;
            imagePath = Environment.getExternalStorageDirectory().getPath() + "/DCIM/";
        }

        @Override
        public int getCount() {
                return getPagerRealWidth() > 0 ? mDataSet.length : 0;
        }

        public int getPagerRealWidth() { return mWidth; }
        public void setPagerRealWidth(int val) { mWidth = val;}

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((View)object);

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Point x = getSize();

            ImageView imageView = new ImageView(mContext);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, x.y);
            imageView.setLayoutParams(new LayoutParams(x.x, x.y));
            imageView.setImageDrawable(new DrawablePlaceHolder(mContext.getResources(), imageView));
            BitmapHelper.loadBitmap(imageView, imagePath + mDataSet[position], x.x, x.y);

            container.addView(imageView);
            return imageView;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private static class DrawablePlaceHolder extends Drawable {

        private WeakReference<ImageView> mImageViewWeakReference;

        private static Bitmap s_loadingBitmap = null;

        private static Bitmap getLoadingBitmap(Resources res) {
            if (s_loadingBitmap == null) {
                s_loadingBitmap = BitmapFactory.decodeResource(res, R.mipmap.duit);
            }

            return s_loadingBitmap;
        }

        private final Resources mRes;
        public DrawablePlaceHolder(Resources res, ImageView imageView) {
            mRes = res;
            mImageViewWeakReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawColor(mRes.getColor(R.color.duit_app_color_grey));
            Bitmap bitmap = getLoadingBitmap(mRes);

            canvas.drawBitmap(bitmap, 540 - bitmap.getScaledWidth(canvas) / 2, 270 - bitmap.getScaledHeight(canvas) / 2, new Paint());
        }

        @Override
        public void setAlpha(int alpha) {

        }

        @Override
        public void setColorFilter(ColorFilter cf) {

        }

        @Override
        public int getOpacity() {
            return 0;
        }

        @Override
        public int getIntrinsicWidth() {
            return 1080;
        }

        @Override
        public int getIntrinsicHeight() {
            return 540;
        }
    }

}