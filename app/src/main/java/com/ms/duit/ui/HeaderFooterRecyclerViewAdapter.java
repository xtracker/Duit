package com.ms.duit.ui;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
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
        else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.viewholder_item_article, parent, false);
            return new ArticleItemViewHolder(v, (TextView)v.findViewById(R.id.text_article_title));
        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        OnRecycledListener recycledListener = (OnRecycledListener)holder;
        if (recycledListener != null) {
            recycledListener.OnRecycled();
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            SlideViewPager pager = (SlideViewPager)holder.itemView;
            //pager.setAdapter(new SlideViewPager.SlideViewPagerAdapter(pager.getContext(), new String[] {"P50408-205046.JPG","P50408-214225.JPG", "P50408-214230.JPG"}));

        } else {
            (((ArticleItemViewHolder)holder).ArticleTextView).setText("Item...");
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
    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    public static interface OnRecycledListener {
        void OnRecycled();
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder implements OnRecycledListener {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void OnRecycled() {

        }
    }
}
