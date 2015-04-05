package com.ms.duit;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by jarzhao on 4/5/2015.
 */
public class DisplayUtils {

    private static int screenWidth = -1;

    public static int dip2px(int dip) {
            final float scale = DuitApplication.getAppContext().getResources().getDisplayMetrics().density;
            return (int)(dip * scale + 0.5f);
    }

    public static int dip2px(float dip) {
        final float scale = DuitApplication.getAppContext().getResources().getDisplayMetrics().density;
        return (int)(dip * scale + 0.5f);
    }

    public static int getScreenWidth() {
        if (screenWidth == -1) {
            WindowManager wm = (WindowManager)DuitApplication.getAppContext().getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics(); // : displayMetrics;
            wm.getDefaultDisplay().getMetrics(dm);

            screenWidth = dm.widthPixels;
        }

        return screenWidth;
    }

    public static float getDimension(int id) {
        return DuitApplication.getAppContext().getResources().getDimension(id);
    }
}
