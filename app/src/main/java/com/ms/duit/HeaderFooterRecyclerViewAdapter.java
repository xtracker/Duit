package com.ms.duit;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jarzhao on 4/5/2015.
 */
public class HeaderFooterRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        /*View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_item_article, parent, false);
        return new ArticleItemViewHolder(v, (TextView)v.findViewById(R.id.text_article_title));*/
        SlideViewPager slideViewPager = new SlideViewPager(parent.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        slideViewPager.setLayoutParams(layoutParams);
        return HeaderViewHolder.createFrom(slideViewPager);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;
    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }

        public static HeaderViewHolder createFrom(SlideViewPager slideViewPager) {
            return new HeaderViewHolder(slideViewPager);
        }
    }
}
