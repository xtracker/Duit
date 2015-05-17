package com.ms.duit;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by jarzhao on 4/5/2015.
 */
public class DuitApplication extends Application {
    private static final String TAG = "DuitApp";

    private static Context s_context;

    private static DuitApplication s_instance;

    @Override
    public void onCreate(){
        super.onCreate();
        DuitApplication.s_context = getApplicationContext();
        s_instance = this;
        resetDensity(s_context);
    }

    public static Context getAppContext() {
        return DuitApplication.s_context;
    }

    public static DuitApplication getInstance() { return DuitApplication.s_instance; }

    public static void resetDensity(Context context) {
        if (context == null) {
            Log.d(TAG, "resentDensity with context == null");
            return;
        }

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        double diagonalPixels = Math.sqrt(Math.pow(dm.widthPixels / dm.xdpi, 2) + Math.pow(dm.heightPixels / dm.ydpi, 2));
        int realDensityDpi = (int)(Math.sqrt( dm.widthPixels * dm.widthPixels + dm.heightPixels * dm.heightPixels) / diagonalPixels);

        int targetDensityDpi = (realDensityDpi / 40) * 40;
        targetDensityDpi += 40;

        float targetDensity = (float)targetDensityDpi / DisplayMetrics.DENSITY_DEFAULT;

        dm.densityDpi = targetDensityDpi;
        dm.density = targetDensity;
        dm.scaledDensity = targetDensity;
    }
}
