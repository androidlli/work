package com.cango.adpickcar.detail.basicinfo;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.cango.adpickcar.R;
import com.cango.adpickcar.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class BasicInfoFragment extends BaseFragment {
    public static BasicInfoFragment getInstance(){
        BasicInfoFragment basicInfoFragment = new BasicInfoFragment();
        Bundle bundle = new Bundle();
        basicInfoFragment.setArguments(bundle);
        return basicInfoFragment;
    }
    @BindView(R.id.sp_detail_drive_permit)
    Spinner spDrivepermit;
    @BindView(R.id.sp_detail_gps_screen)
    Spinner spGPSScreen;
    @BindView(R.id.sp_detail_gps_install)
    Spinner spGPSInstall;
    @BindView(R.id.sp_detail_battery_power_supply)
    Spinner spBatteryPowerSupply;
    @BindView(R.id.sp_detail_locks)
    Spinner spLocks;
    @BindView(R.id.sp_detail_car_paper)
    Spinner spCarPaper;
    @BindView(R.id.sp_detail_car_status)
    Spinner spCarStatus;
    @BindView(R.id.sp_detail_status)
    Spinner spStatus;
    @BindView(R.id.sp_detail_approve_status)
    Spinner spApproveStatus;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_basic_info;
    }

    @Override
    protected void initView() {
        //构造ArrayAdapter
        ArrayList<String> mSpinnerDatas = new ArrayList<>();
        mSpinnerDatas.add("A");
        mSpinnerDatas.add("B");
        mSpinnerDatas.add("C");
        mSpinnerDatas.add("D");
        mSpinnerDatas.add("E");
        mSpinnerDatas.add("F");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.simple_spinner_item, mSpinnerDatas);
        //设置下拉样式以后显示的样式
        adapter.setDropDownViewResource(R.layout.my_drop_down_item);

        spDrivepermit.setAdapter(adapter);
        spGPSScreen.setAdapter(adapter);
        spGPSInstall.setAdapter(adapter);
        spBatteryPowerSupply.setAdapter(adapter);
        spLocks.setAdapter(adapter);
        spCarPaper.setAdapter(adapter);
        spCarStatus.setAdapter(adapter);
        spStatus.setAdapter(adapter);
        spApproveStatus.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }
}
