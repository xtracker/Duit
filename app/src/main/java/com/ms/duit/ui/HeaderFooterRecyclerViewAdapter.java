package com.ms.duit.ui;

import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import com.ms.duit.R;

/**
 * Created by jarzhao on 4/5/2015.
 */
public class HeaderFooterRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final FragmentActivity mActivity;
    int itemCount = 10;

    public HeaderFooterRecyclerViewAdapter(FragmentActivity mActivity) {
        this.mActivity = mActivity;
    }

    public int recyclerViewWidth = 1080;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADER) {
            SlideViewPager slideViewPager = new SlideViewPager(parent.getContext());
            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,  recyclerViewWidth / 2 );
            slideViewPager.setLayoutParams(layoutParams);
            slideViewPager.setAdapter(new SlideViewPager.SlideViewPagerAdapter(slideViewPager.getContext(), new String[] {"P50408-205046.JPG", "P50408-214225.JPG", "P50408-214230.JPG"}));

            return new HeaderViewHolder(slideViewPager);
        }
        else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.viewholder_item_article, parent, false);
            return new ArticleItemViewHolder(v, (TextView)v.findViewById(R.id.text_article_title));
        } else {

            LinearLayout linearLayout = new LinearLayout(parent.getContext());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            ProgressBar spinner = new ProgressBar(parent.getContext(), null, android.R.attr.progressBarStyleInverse);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
            spinner.setLayoutParams(layoutParams);

            linearLayout.addView(spinner);
            TextView textView = new TextView(parent.getContext());
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            textView.setTypeface(Typeface.DEFAULT);
            textView.setText("加载更多。。。");
            linearLayout.addView(textView);
            return new FooterViewHolder(linearLayout);

        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        OnStateChangedListener recycledListener = (OnStateChangedListener)holder;
        if (recycledListener != null) {
            recycledListener.onRecycled(this);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            //SlideViewPager pager = (SlideViewPager)holder.itemView;
            //pager.setAdapter(new SlideViewPager.SlideViewPagerAdapter(pager.getContext(), new String[] {"P50408-205046.JPG","P50408-214225.JPG", "P50408-214230.JPG"}));

        } else if (holder instanceof ArticleItemViewHolder)  {
            (((ArticleItemViewHolder)holder).ArticleTextView).setText("央行明日起降息0.25个百分点");
            ImageView imageView = (ImageView)holder.itemView.findViewById(R.id.article_thumbnail);
            imageView.setImageResource(R.mipmap.duit);
        }

        OnStateChangedListener recycledListener = (OnStateChangedListener)holder;
        if (recycledListener != null) {
            recycledListener.onBind(this, position);
        }
    }

    @Override
    public int getItemCount() {
            return itemCount;
    }

    public void setItemCount(int val) {
        itemCount = val;
    }

    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;
    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        else if (position == itemCount - 1)
            return TYPE_FOOTER;

        return TYPE_ITEM;
    }

    public interface OnStateChangedListener {
        void onRecycled(HeaderFooterRecyclerViewAdapter adapter);
        void onBind(HeaderFooterRecyclerViewAdapter adapter, int position);
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder implements OnStateChangedListener {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onRecycled(HeaderFooterRecyclerViewAdapter adapter) {

        }

        @Override
        public void onBind(HeaderFooterRecyclerViewAdapter adapter, int position) {

        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder implements OnStateChangedListener {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onRecycled(HeaderFooterRecyclerViewAdapter adapter) {

        }

        @Override
        public void onBind(final HeaderFooterRecyclerViewAdapter adapter, int position) {
            Log.d("Adapter", "Footer bind");
             (new Handler()).postDelayed(new Runnable() {

                 @Override
                 public void run() {
                     int oldCount = adapter.getItemCount();
                     adapter.setItemCount(oldCount + 2);
                     adapter.notifyItemRangeInserted(oldCount, 2);
                 }
             }, 1000);

        }
    }
}
