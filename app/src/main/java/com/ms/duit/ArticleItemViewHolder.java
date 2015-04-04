package com.ms.duit;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by jarzhao on 4/5/2015.
 */
public class ArticleItemViewHolder extends RecyclerView.ViewHolder {

    public TextView ArticleTextView;
    public ArticleItemViewHolder(View itemView) {
        super(itemView);
        ArticleTextView = (TextView)itemView;
    }
}
