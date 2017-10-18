package com.cango.adpickcar;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.cango.adpickcar.util.SPUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cango on 2017/9/18.
 */

public class ADApplication extends Application {
    private static Context mContext;
    public static SPUtils mSPUtils;
    public static List<AppCompatActivity> activityList;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mSPUtils = new SPUtils("AD_CAR");
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static Context getmContext() {
        return mContext;
    }

    public static void addActivity(AppCompatActivity activity) {
        if (activityList == null) {
            activityList = new ArrayList<>();
        }
        activityList.add(activity);
    }

    public static void clearLastActivity() {
        if (activityList == null || activityList.size() == 0) {

        } else {
            activityList.remove(activityList.size() - 1);
        }
    }

    public static void clearExceptLastActivitys() {
        if (activityList == null || activityList.size() == 0) {

        } else {
            for (int i = 0; i < activityList.size() - 1; i++) {
                if (activityList.get(i) != null) {
                    activityList.get(i).finish();
                }
            }
            AppCompatActivity activity = activityList.get(activityList.size() - 1);
            activityList.clear();
            activityList.add(activity);
        }
    }
}
