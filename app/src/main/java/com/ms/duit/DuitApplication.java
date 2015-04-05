package com.ms.duit;

import android.app.Application;
import android.content.Context;

/**
 * Created by jarzhao on 4/5/2015.
 */
public class DuitApplication extends Application {

    private static Context _context;

    public void onCreate(){
        super.onCreate();
        DuitApplication._context = getApplicationContext();
    }

    public static Context getAppContext() {
        return DuitApplication._context;
    }
}
