package com.ms.duit.ui;

import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.duit.R;

/**
 * Created by jarzhao on 4/5/2015.
 */
public class HeaderFooterRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final FragmentActivity mActivity;

    public HeaderFooterRecyclerViewAdapter(FragmentActivity mActivity) {
        this.mActivity = mActivity;
    }

    public int recyclerViewWidth = 1080;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADER) {
            SlideViewPager slideViewPager = new SlideViewPager(parent.getContext());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,  recyclerViewWidth / 2 );
            slideViewPager.setLayoutParams(layoutParams);
            slideViewPager.setAdapter(new SlideViewPager.SlideViewPagerAdapter(slideViewPager.getContext(), new String[] {"P50408-205046.JPG", "P50408-214225.JPG", "P50408-214230.JPG"}));

            return new HeaderViewHolder(slideViewPager);
        }
        else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.viewholder_item_article, parent, false);
            return new ArticleItemViewHolder(v, (TextView)v.findViewById(R.id.text_article_title));
        } else {
            TextView textView = new TextView(parent.getContext());
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            textView.setTypeface(Typeface.DEFAULT);
            textView.setText("加载更多。。。");
            return new FooterViewHolder(textView);

        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        OnStateChangedListener recycledListener = (OnStateChangedListener)holder;
        if (recycledListener != null) {
            recycledListener.onRecycled();
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            SlideViewPager pager = (SlideViewPager)holder.itemView;
            //pager.setAdapter(new SlideViewPager.SlideViewPagerAdapter(pager.getContext(), new String[] {"P50408-205046.JPG","P50408-214225.JPG", "P50408-214230.JPG"}));

        } else if (holder instanceof ArticleItemViewHolder)  {
            (((ArticleItemViewHolder)holder).ArticleTextView).setText("央行明日起降息0.25个百分点");
            ImageView imageView = (ImageView)holder.itemView.findViewById(R.id.article_thumbnail);
            imageView.setImageResource(R.mipmap.duit);
        }
    }

    @Override
    public int getItemCount() {
            return 20;
    }

    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;
    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        else if (position == 19)
            return TYPE_FOOTER;

        return TYPE_ITEM;
    }

    public static interface OnStateChangedListener {
        void onRecycled();
        void onBind();
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder implements OnStateChangedListener {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onRecycled() {

        }

        @Override
        public void onBind() {

        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder implements OnStateChangedListener {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onRecycled() {

        }

        @Override
        public void onBind() {

        }
    }
}
