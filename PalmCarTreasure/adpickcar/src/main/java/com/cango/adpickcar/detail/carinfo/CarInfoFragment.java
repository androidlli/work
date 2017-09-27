package com.cango.adpickcar.detail.carinfo;


import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;

import com.cango.adpickcar.R;
import com.cango.adpickcar.base.BaseFragment;

import butterknife.BindView;

public class CarInfoFragment extends BaseFragment {
    public static CarInfoFragment getInstance() {
        CarInfoFragment carInfoFragment = new CarInfoFragment();
        Bundle bundle = new Bundle();
        carInfoFragment.setArguments(bundle);
        return carInfoFragment;
    }

    @BindView(R.id.nsv_car)
    NestedScrollView nsvCar;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_car_info;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
