package com.cango.palmcartreasure.trailer.map;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.cango.palmcartreasure.R;
import com.cango.palmcartreasure.base.BaseActivity;
import com.cango.palmcartreasure.model.TypeTaskData;
import com.cango.palmcartreasure.util.CommUtil;

/**
 * 送车入库和拖车导航的界面
 */
public class TrailerMapActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer_map);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {

        }
        String type = getIntent().getStringExtra(TrailerMapFragment.TYPE);
        TypeTaskData.DataBean.TaskListBean taskListBean = getIntent().getParcelableExtra(TrailerMapFragment.TASKLISTBEAN);
        TrailerMapFragment mapFragment = (TrailerMapFragment) getSupportFragmentManager().findFragmentById(R.id.fl_trailer_map_contains);
        if (CommUtil.checkIsNull(mapFragment)) {
            mapFragment = TrailerMapFragment.newInstance(type,taskListBean);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fl_trailer_map_contains, mapFragment);
            transaction.commit();
        }
    }
}
