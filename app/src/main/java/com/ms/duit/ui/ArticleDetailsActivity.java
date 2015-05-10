package com.ms.duit.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ms.duit.R;


public class ArticleDetailsActivity extends ActionBarActivity {

    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_article_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mWebView = (WebView)findViewById(R.id.web_view_article_details);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_article_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            String summary = "<p>This is a test article</p><img src=\"http://img4.cache.netease.com/2008/2013/9/18/2013091800585877c89.jpg\"></img>";
            mWebView.loadData(summary, "text/html", null);
            //mWebView.loadUrl("http://www.csdn.net/article/2015-05-08/2824643");
            return true;
        }

        if (id == android.R.id.home ) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
