package com.cango.adpickcar.detail.carinfo;


import android.os.Bundle;

import com.cango.adpickcar.R;
import com.cango.adpickcar.base.BaseFragment;

public class CarInfoFragment extends BaseFragment {
    public static CarInfoFragment getInstance() {
        CarInfoFragment carInfoFragment = new CarInfoFragment();
        Bundle bundle = new Bundle();
        carInfoFragment.setArguments(bundle);
        return carInfoFragment;
    }

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
