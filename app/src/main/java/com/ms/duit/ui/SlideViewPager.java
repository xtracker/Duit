package com.ms.duit.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
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

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by jarzhao on 4/5/2015.
 */

public class SlideViewPager extends FrameLayout {

    private static final int DEFAULT_INDICATOR_WIDTH_DIP = 10;
    private static final int DEFAULT_INDICATOR_HEIGHT_DIP = 2;
    private static final int DEFAULT_INDICATOR_MARGIN_DIP = 2;


    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private LinearLayout mIndicatorContainer;
    private TextView mTitle;
    private ArrayList<OnSlideViewPageChangedListener> mSlideViewPagerChangedListeners;
    public SlideViewPager(Context context) {

        super(context);
        setUpChildViews(context);
    }

    public SlideViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        setUpChildViews(context);
    }

    public SlideViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpChildViews(context);
    }

    public void refreshIndicators(int selection) {
        for (int i = 0; i < this.mIndicatorContainer.getChildCount(); ++i) {
            if (i == selection % 3) {
                mIndicatorContainer.getChildAt(i).setBackgroundColor(getContext().getResources().getColor(R.color.indicator_selected));
            } else {
                mIndicatorContainer.getChildAt(i).setBackgroundColor(getContext().getResources().getColor(R.color.indicator_unselected));
            }
        }
    }

    private void setUpChildViews(Context context) {

        mViewPager = new ViewPager(context);
        mViewPager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.addView(mViewPager);
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.BOTTOM);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        mTitle = textView;
        mIndicatorContainer = new LinearLayout(context);
        mIndicatorContainer.setOrientation(LinearLayout.HORIZONTAL);
        FrameLayout.LayoutParams indicatorContainerLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        indicatorContainerLayoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        mIndicatorContainer.setLayoutParams(indicatorContainerLayoutParams);
        this.addView(mIndicatorContainer);


        mViewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = mViewPager.getWidth();
                if (mPagerAdapter == null) return;

                if (SysUtils.hasJellyBean())
                    mViewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                else
                    mViewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                /*for (OnSlideViewPageChangedListener listener : mSlideViewPagerChangedListeners) {
                    listener.onSlideViewPageChanged(position);
                }*/

                refreshIndicators(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    public static Point getSize() {
        int width = DisplayUtils.getScreenWidth() - DisplayUtils.dip2px(DisplayUtils.getDimension(R.dimen.activity_horizontal_margin) * 2);
        int height = (int)(((float)width * 1.0) * 0.5 );
        return new Point(width, height);
    }

    private void populateIndicators() {
        int count = ((SlideViewPagerAdapter)mPagerAdapter).getRealCount() ;
        if (mIndicatorContainer.getChildCount() > 0) {
            mIndicatorContainer.removeAllViews();
        }

        int curSel = mViewPager.getCurrentItem() % 3;
        for (int i = 0; i < count; ++i) {
            View indicator = new View(this.getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DisplayUtils.dip2px(DEFAULT_INDICATOR_WIDTH_DIP), DisplayUtils.dip2px(DEFAULT_INDICATOR_HEIGHT_DIP));
            layoutParams.setMargins(5, 10, 5, 20);
            if (i == curSel) {
                indicator.setBackgroundColor(getContext().getResources().getColor(R.color.indicator_selected));
            } else {
                indicator.setBackgroundColor(getContext().getResources().getColor(R.color.indicator_unselected));
            }

            mIndicatorContainer.addView(indicator, layoutParams);
        }
    }

    public void setAdapter(PagerAdapter pagerAdapter) {
        if (mViewPager != null) {
            mViewPager.setAdapter(pagerAdapter);
            mPagerAdapter = pagerAdapter;
            populateIndicators();
            mViewPager.setCurrentItem(333333);
        }
    }

    public PagerAdapter getAdapter() {
        return mPagerAdapter;
    }

    public void AddSlideViewPagerChangedListener(OnSlideViewPageChangedListener slideViewPageChangedListener) {
        if (mSlideViewPagerChangedListeners == null) {
            mSlideViewPagerChangedListeners = new ArrayList<OnSlideViewPageChangedListener>();
        }
        if (!mSlideViewPagerChangedListeners.contains(slideViewPageChangedListener))
            mSlideViewPagerChangedListeners.add(slideViewPageChangedListener);
    }

    public static interface OnSlideViewPageChangedListener {
        void onSlideViewPageChanged(int currentItem);
    }

    public static class SlideViewPagerAdapter extends PagerAdapter {

        private final Context mContext;
        private String[] mDataSet = new String[3];
        private String imagePath;
        private int mWidth = -1;

        public SlideViewPagerAdapter(Context context, String[] dataSet) {
            super();
            mContext = context;
            //mDataSet = dataSet;
            imagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath(); // Environment.getExternalStorageDirectory().getPath() + "/DCIM/";

            File rootDir = new File(imagePath + "/Camera/");
            if (!rootDir.exists()) {
                rootDir = new File(imagePath);
            }
            int i = 0;
            for (File pic : rootDir.listFiles()) {
                if (pic.isFile() && i < 3) {
                    mDataSet[i++] = pic.getAbsolutePath();
                }
            }
        }

        @Override
        public int getCount() {
                return Integer.MAX_VALUE;//mDataSet.length; //getPagerRealWidth() > 0 ? mDataSet.length : 0;
        }

        public int getRealCount() {
            return 3;
        }

        public int getPagerRealWidth() { return mWidth; }
        public void setPagerRealWidth(int val) { mWidth = val;}

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Point x = getSize();

            ImageView imageView = new ImageView(mContext);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageDrawable(new DrawablePlaceHolder(mContext.getResources(), imageView));

            BitmapHelper.loadBitmap(imageView, mDataSet[position % 3], x.x, x.y);

            container.addView(imageView);
            return imageView;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    public static class DrawablePlaceHolder extends Drawable {

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
