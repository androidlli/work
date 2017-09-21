package com.cango.adpickcar.resetps;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cango.adpickcar.R;
import com.cango.adpickcar.base.BaseFragment;
import com.cango.adpickcar.util.BarUtil;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResetFragment extends BaseFragment {

    public static ResetFragment getInstance() {
        ResetFragment resetFragment = new ResetFragment();
        Bundle bundle = new Bundle();
        resetFragment.setArguments(bundle);
        return resetFragment;
    }

    @BindView(R.id.toolbar_reset)
    Toolbar mToolbar;
    ResetPSActivity mActivity;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_reset;
    }

    @Override
    protected void initView() {
        int statusBarHeight = BarUtil.getStatusBarHeight(getActivity());
        int actionBarHeight = BarUtil.getActionBarHeight(getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight + actionBarHeight);
            mToolbar.setLayoutParams(layoutParams);
            mToolbar.setPadding(0, statusBarHeight, 0, 0);
        }
        mActivity.setSupportActionBar(mToolbar);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity.getSupportActionBar().setHomeButtonEnabled(true);
        mActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        mActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });
    }

    @Override
    protected void initData() {
        mActivity = (ResetPSActivity) getActivity();
    }
}
