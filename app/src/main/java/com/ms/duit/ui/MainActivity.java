package com.ms.duit.ui;

import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.ms.duit.R;


public class MainActivity extends ActionBarActivity {


    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private RadioGroup mDrawerRadioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.navigation_drawer);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.hello_world, R.string.hello_world) {

            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle("Duit");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("Opened");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        mDrawerRadioGroup = (RadioGroup)findViewById(R.id.radioGroup);

        selectItem(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void selectItem(int position) {

        Fragment fragment = ContentFragment.newInstance(position);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();

        // update selected item title, then close the drawer
        mDrawerLayout.closeDrawer(mDrawerRadioGroup);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
         mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }


        private RecyclerView mRecyclerView;
        private RecyclerView.Adapter mAdapter;

        private RecyclerView.LayoutManager mLayoutManager;
        //private SwipeRefreshLayout mSwipeRefreshLayout;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerview_main);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this.getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(new HeaderFooterRecyclerViewAdapter(getActivity()));
            mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

                Paint paint = new Paint();

                @Override
                public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                    super.onDraw(c, parent, state);
                    paint.setColor(getResources().getColor(R.color.item_separator));
                }

                @Override
                public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                    super.onDrawOver(c, parent, state);
                    for (int i = 0, size = parent.getChildCount(); i < size; i++) {
                        View child = parent.getChildAt(i);
                        c.drawLine(child.getLeft(), child.getBottom(), child.getRight(), child.getBottom(), paint);
                    }
                }
            });

           // mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.refresh_main);
            return rootView;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

           /* mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }, 4 * 1000);
                }
            });*/
        }
    }
}
