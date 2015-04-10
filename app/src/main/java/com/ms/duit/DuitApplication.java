package com.ms.duit;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by jarzhao on 4/5/2015.
 */
public class DuitApplication extends Application {
    private static final String TAG = "DuitApp";

    private static Context s_context;

    private static DuitApplication s_instance;

    public void onCreate(){
        super.onCreate();
        DuitApplication.s_context = getApplicationContext();
        s_instance = this;

        WindowManager wm = (WindowManager)s_context.getSystemService(WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
    }

    public static Context getAppContext() {
        return DuitApplication.s_context;
    }

    public static DuitApplication getInstance() { return DuitApplication.s_instance; }
}
