package com.cango.adpickcar;

import android.app.Application;
import android.content.Context;

import com.cango.adpickcar.util.SPUtils;

/**
 * Created by cango on 2017/9/18.
 */

public class ADApplication extends Application {
    private static Context mContext;
    public static SPUtils mSPUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mSPUtils = new SPUtils("AD_CAR");
    }

    public static Context getmContext() {
        return mContext;
    }
}
