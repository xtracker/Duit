package com.ms.duit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jarzhao on 4/5/2015.
 */
public class HeaderFooterRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_item_article, parent, false);
        return new ArticleItemViewHolder(v, (TextView)v.findViewById(R.id.text_article_title));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ArticleItemViewHolder x = (ArticleItemViewHolder)holder;
        x.ArticleTextView.setText("Text article");
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
