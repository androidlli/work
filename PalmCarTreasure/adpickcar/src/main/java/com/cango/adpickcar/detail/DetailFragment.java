package com.cango.adpickcar.detail;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.cango.adpickcar.R;
import com.cango.adpickcar.base.BaseFragment;
import com.cango.adpickcar.detail.basicinfo.BasicInfoFragment;
import com.cango.adpickcar.detail.carinfo.CarInfoFragment;
import com.cango.adpickcar.detail.imageinfo.ImageInfoFragment;
import com.cango.adpickcar.detail.iteminfo.ItemInfoFragment;
import com.cango.adpickcar.util.BarUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailFragment extends BaseFragment {

    public static DetailFragment getInstance() {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @BindView(R.id.toolbar_detail)
    Toolbar mToolbar;

    @OnClick({R.id.ll_detail_basic_info, R.id.ll_detail_item_info, R.id.ll_detail_car_info,R.id.ll_detail_image_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_detail_basic_info:
                hideFragments();
                showFragment(0);
                break;
            case R.id.ll_detail_item_info:
                hideFragments();
                showFragment(1);
                break;
            case R.id.ll_detail_car_info:
                hideFragments();
                showFragment(2);
                break;
            case R.id.ll_detail_image_info:
                hideFragments();
                showFragment(3);
                break;
        }
    }

    private DetailActivity mActivity;
    FragmentManager fm;
    private ArrayList<BaseFragment> mFragments = new ArrayList<>();

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_detail;
    }

    @Override
    protected void initView() {
        initToolbar();
        addChildFragment();
        hideFragments();
        showFragment(0);
    }

    private void addChildFragment() {
        BasicInfoFragment basicInfoFragment = BasicInfoFragment.getInstance();
        ItemInfoFragment itemInfoFragment = ItemInfoFragment.getInstance();
        CarInfoFragment carInfoFragment = CarInfoFragment.getInstance();
        ImageInfoFragment imageInfoFragment = ImageInfoFragment.getInstance();
        mFragments.add(basicInfoFragment);
        mFragments.add(itemInfoFragment);
        mFragments.add(carInfoFragment);
        mFragments.add(imageInfoFragment);
        fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment base : mFragments) {
            ft.add(R.id.fl_detail_child_container, base, "basicinfo");
        }
        ft.commit();
    }

    public void showFragment(int position) {
        hideFragments();
        Fragment fragment = mFragments.get(position);
        FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragment);
        ft.commit();
    }

    public void hideFragments() {
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : mFragments) {
            if (fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commit();
    }

    @Override
    protected void initData() {
        mActivity = (DetailActivity) getActivity();
    }

    private void initToolbar() {
        int statusBarHeight = BarUtil.getStatusBarHeight(getActivity());
        int actionBarHeight = BarUtil.getActionBarHeight(getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, statusBarHeight + actionBarHeight);
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
}
