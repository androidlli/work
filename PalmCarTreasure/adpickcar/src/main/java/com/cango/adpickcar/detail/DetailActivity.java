package com.cango.adpickcar.detail;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.cango.adpickcar.R;
import com.cango.adpickcar.base.BaseActivity;
import com.cango.adpickcar.main.MainFragment;
import com.cango.adpickcar.util.CommUtil;

public class DetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.fl_detail_container);
        if (CommUtil.checkIsNull(detailFragment)) {
            detailFragment = DetailFragment.getInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fl_detail_container, detailFragment);
            transaction.commit();
        }
    }
}