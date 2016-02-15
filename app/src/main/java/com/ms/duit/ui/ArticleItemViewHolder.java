package com.ms.duit.ui;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import junit.framework.Assert;

/**
 * Created by jarzhao on 4/5/2015.
 */
public class ArticleItemViewHolder extends RecyclerView.ViewHolder implements HeaderFooterRecyclerViewAdapter.OnStateChangedListener {

    public TextView ArticleTextView;
    public ArticleItemViewHolder(final View itemView, TextView textView) {
        super(itemView);
        ArticleTextView = textView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), ArticleDetailsActivity.class));
                //((TextView)itemView.findViewById(R.id.text_article_title)).setTextColor(itemView.getContext().getResources().getColor(R.color.black_overlay));
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }

    @Override
    public void onRecycled(HeaderFooterRecyclerViewAdapter adapter) {

    }

    @Override
    public void onBind(HeaderFooterRecyclerViewAdapter adapter, int position) {
        Assert.assertTrue("position should be equal to adapter position", position == this.getAdapterPosition());
        int adp = this.getAdapterPosition();
        Log.d("Article", "position = " + position + " adapter position = " + adp);
    }
}
