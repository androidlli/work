package com.cango.adpickcar.detail;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

public class DetailFragment extends BaseFragment implements DetailContract.View {

    public static DetailFragment getInstance() {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @BindView(R.id.toolbar_detail)
    Toolbar mToolbar;
    @BindView(R.id.iv_detail_basic_info)
    ImageView ivBasic;
    @BindView(R.id.tv_detail_basic_info)
    TextView tvBasic;
    @BindView(R.id.iv_detail_item_info)
    ImageView ivItem;
    @BindView(R.id.tv_detail_item_info)
    TextView tvItem;
    @BindView(R.id.iv_detail_car_info)
    ImageView ivCar;
    @BindView(R.id.tv_detail_car_info)
    TextView tvCar;
    @BindView(R.id.iv_detail_image_info)
    ImageView ivImage;
    @BindView(R.id.tv_detail_image_info)
    TextView tvImage;

    @OnClick({R.id.ll_detail_basic_info, R.id.ll_detail_item_info, R.id.ll_detail_car_info, R.id.ll_detail_image_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_detail_basic_info:
                if (currentPosition == 0) {
                    return;
                }
                currentPosition = 0;
                SelectChildTitle(currentPosition);
                hideFragments();
                showFragment(currentPosition);
                break;
            case R.id.ll_detail_item_info:
                if (currentPosition == 1) {
                    return;
                }
                currentPosition = 1;
                SelectChildTitle(currentPosition);
                hideFragments();
                showFragment(currentPosition);
                break;
            case R.id.ll_detail_car_info:
                if (currentPosition == 2) {
                    return;
                }
                currentPosition = 2;
                SelectChildTitle(currentPosition);
                hideFragments();
                showFragment(currentPosition);
                break;
            case R.id.ll_detail_image_info:
                if (currentPosition == 3) {
                    return;
                }
                currentPosition = 3;
                SelectChildTitle(currentPosition);
                hideFragments();
                showFragment(currentPosition);
                break;
        }
    }

    private DetailActivity mActivity;
    public DetailPresenter mPresenter;
    FragmentManager fm;
    private ArrayList<BaseFragment> mFragments = new ArrayList<>();
    private int currentPosition;
    private int selectColor, noSelectColor;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_detail;
    }

    @Override
    protected void initView() {
        initToolbar();
        addChildFragment();
        currentPosition = 0;
        SelectChildTitle(currentPosition);
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
            ft.add(R.id.fl_detail_child_container, base, base.getClass().getSimpleName());
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
        mPresenter = new DetailPresenter();
        selectColor = getResources().getColor(R.color.colorPrimary);
        noSelectColor = getResources().getColor(R.color.ad888888);
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

    private void SelectChildTitle(int position) {
        switch (position) {
            case 0:
                ivBasic.setImageResource(R.drawable.jibenxinxi_on);
                tvBasic.setTextColor(selectColor);
                ivItem.setImageResource(R.drawable.wupinxinxi_off);
                tvItem.setTextColor(noSelectColor);
                ivCar.setImageResource(R.drawable.cheliangxinxi_off);
                tvCar.setTextColor(noSelectColor);
                ivImage.setImageResource(R.drawable.yingxiangxinxi_off);
                tvImage.setTextColor(noSelectColor);
                break;
            case 1:
                ivBasic.setImageResource(R.drawable.jibenxinxi_off);
                tvBasic.setTextColor(noSelectColor);
                ivItem.setImageResource(R.drawable.wupinxinxi_on);
                tvItem.setTextColor(selectColor);
                ivCar.setImageResource(R.drawable.cheliangxinxi_off);
                tvCar.setTextColor(noSelectColor);
                ivImage.setImageResource(R.drawable.yingxiangxinxi_off);
                tvImage.setTextColor(noSelectColor);
                break;
            case 2:
                ivBasic.setImageResource(R.drawable.jibenxinxi_off);
                tvBasic.setTextColor(noSelectColor);
                ivItem.setImageResource(R.drawable.wupinxinxi_off);
                tvItem.setTextColor(noSelectColor);
                ivCar.setImageResource(R.drawable.cheliangxinxi_on);
                tvCar.setTextColor(selectColor);
                ivImage.setImageResource(R.drawable.yingxiangxinxi_off);
                tvImage.setTextColor(noSelectColor);
                break;
            case 3:
                ivBasic.setImageResource(R.drawable.jibenxinxi_off);
                tvBasic.setTextColor(noSelectColor);
                ivItem.setImageResource(R.drawable.wupinxinxi_off);
                tvItem.setTextColor(noSelectColor);
                ivCar.setImageResource(R.drawable.cheliangxinxi_off);
                tvCar.setTextColor(noSelectColor);
                ivImage.setImageResource(R.drawable.yingxiangxinxi_on);
                tvImage.setTextColor(selectColor);
                break;
        }
    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
    }

    @Override
    public void showIndicator(boolean active) {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showSuccess(boolean isSuccess, String message) {

    }

    @Override
    public void openOtherUi() {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
