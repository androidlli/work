package com.cango.adpickcar.detail.basicinfo;


import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.cango.adpickcar.R;
import com.cango.adpickcar.base.BaseFragment;
import com.cango.adpickcar.detail.DetailFragment;
import com.cango.adpickcar.detail.DetailPresenter;
import com.cango.adpickcar.model.BaseInfo;
import com.cango.adpickcar.model.CarTakeTaskList;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.BindView;

public class BasicInfoFragment extends BaseFragment {
    public static BasicInfoFragment getInstance() {
        BasicInfoFragment basicInfoFragment = new BasicInfoFragment();
        Bundle bundle = new Bundle();
        basicInfoFragment.setArguments(bundle);
        return basicInfoFragment;
    }

    @BindView(R.id.nsv_basic)
    NestedScrollView nsvBasic;
    @BindView(R.id.et_MileAgeReg)
    EditText etMileAgeReg;
    @BindView(R.id.et_carInfoDesc)
    EditText etCarInfoDesc;
    @BindView(R.id.sp_detail_num)
    Spinner spNum;
    @BindView(R.id.switch_has_card)
    Switch switchCard;
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
    @BindView(R.id.et_Antitowing)
    EditText etAntitowing;
    @BindView(R.id.et_RegMemo)
    EditText etRegMemo;
    @BindView(R.id.et_WhPosition)
    EditText etWhPosition;
    @BindView(R.id.sp_detail_car_status)
    Spinner spCarStatus;
    @BindView(R.id.sp_detail_status)
    Spinner spStatus;
    @BindView(R.id.sp_detail_approve_status)
    Spinner spApproveStatus;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;
    @BindView(R.id.ll_sorry)
    LinearLayout llSorry;

    private DetailPresenter presenter;
    private CarTakeTaskList.DataBean.CarTakeTaskListBean mCarTakeTaskListBean;
    private boolean isEdit;
    private BaseInfo mBaseInfo;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_basic_info;
    }

    @Override
    protected void initView() {
        getData();
    }

    @Override
    protected void initData() {
        presenter = (DetailPresenter) ((DetailFragment) getParentFragment()).mPresenter;
        mCarTakeTaskListBean = ((DetailFragment) getParentFragment()).mCarTakeTaskListBean;
        isEdit = ((DetailFragment) getParentFragment()).isEdit;
    }

    public void getData(){
        presenter.GetCarTakeStoreBaseInfo(true, mCarTakeTaskListBean.getCTSID() + "", mCarTakeTaskListBean.getDisCarID() + "");
    }

    public void updateUI(BaseInfo baseInfo) {
        mBaseInfo = baseInfo;
        if (isEdit) {
            etMileAgeReg.setFocusable(true);
            etMileAgeReg.setFocusableInTouchMode(true);
            etCarInfoDesc.setFocusable(true);
            etCarInfoDesc.setFocusableInTouchMode(true);
            etAntitowing.setFocusable(true);
            etAntitowing.setFocusableInTouchMode(true);
            etRegMemo.setFocusable(true);
            etRegMemo.setFocusableInTouchMode(true);
            etWhPosition.setFocusable(true);
            etWhPosition.setFocusableInTouchMode(true);
        } else {
            etMileAgeReg.setFocusable(false);
            etMileAgeReg.setFocusableInTouchMode(false);
            etCarInfoDesc.setFocusable(false);
            etCarInfoDesc.setFocusableInTouchMode(false);
            spNum.setClickable(false);
            spNum.setFocusable(false);
            spNum.setFocusableInTouchMode(false);
            spNum.setEnabled(false);
            switchCard.setClickable(false);
            switchCard.setFocusable(false);
            switchCard.setFocusableInTouchMode(false);
            spGPSScreen.setClickable(false);
            spGPSScreen.setFocusable(false);
            spGPSScreen.setFocusableInTouchMode(false);
            spGPSScreen.setEnabled(false);
            spGPSInstall.setClickable(false);
            spGPSInstall.setFocusable(false);
            spGPSInstall.setFocusableInTouchMode(false);
            spGPSInstall.setEnabled(false);
            spBatteryPowerSupply.setClickable(false);
            spBatteryPowerSupply.setFocusable(false);
            spBatteryPowerSupply.setFocusableInTouchMode(false);
            spBatteryPowerSupply.setEnabled(false);
            spLocks.setClickable(false);
            spLocks.setFocusable(false);
            spLocks.setFocusableInTouchMode(false);
            spLocks.setEnabled(false);
            spCarPaper.setClickable(false);
            spCarPaper.setFocusable(false);
            spCarPaper.setFocusableInTouchMode(false);
            spCarPaper.setEnabled(false);
            etAntitowing.setFocusable(false);
            etAntitowing.setFocusableInTouchMode(false);
            etRegMemo.setFocusable(false);
            etRegMemo.setFocusableInTouchMode(false);
            etWhPosition.setFocusable(false);
            etWhPosition.setFocusableInTouchMode(false);
            spCarStatus.setClickable(false);
            spCarStatus.setFocusable(false);
            spCarStatus.setFocusableInTouchMode(false);
            spCarStatus.setEnabled(false);
            spStatus.setClickable(false);
            spStatus.setFocusable(false);
            spStatus.setFocusableInTouchMode(false);
            spStatus.setEnabled(false);
            spApproveStatus.setClickable(false);
            spApproveStatus.setFocusable(false);
            spApproveStatus.setFocusableInTouchMode(false);
            spApproveStatus.setEnabled(false);
        }
        nsvBasic.setVisibility(View.VISIBLE);
        llSorry.setVisibility(View.GONE);
        llNoData.setVisibility(View.GONE);
        BaseInfo.DataBean dataBean = mBaseInfo.getData();
        etMileAgeReg.setText(dataBean.getMileAgeReg() + "");
        etCarInfoDesc.setText(dataBean.getCarInfoDesc());

        //车辆钥匙数
        if (isEdit) {
            ArrayList<String> spNumDatas = new ArrayList<>();
            spNumDatas.add("0");
            spNumDatas.add("1");
            spNumDatas.add("2");
            spNumDatas.add("3");
            ArrayAdapter<String> numAdapter = new ArrayAdapter<>(getActivity(),
                    R.layout.simple_spinner_item, spNumDatas);
            numAdapter.setDropDownViewResource(R.layout.my_drop_down_item);
            spNum.setAdapter(numAdapter);
            if (dataBean.getKeyNmb() >= 0) {
                spNum.setSelection(dataBean.getKeyNmb() - 1);
            } else {
            }
        } else {
            if (dataBean.getKeyNmb() >= 0) {
                ArrayList<String> spNumDatas = new ArrayList<>();
                spNumDatas.add(dataBean.getKeyNmb() + "");
                ArrayAdapter<String> numAdapter = new ArrayAdapter<>(getActivity(),
                        R.layout.simple_spinner_item, spNumDatas);
                numAdapter.setDropDownViewResource(R.layout.my_drop_down_item);
                spNum.setAdapter(numAdapter);
                spNum.setSelection(0);
            } else {
            }
        }
        //有无车辆行驶证
        if (dataBean.getHasDrvLic().equals("0")) {
            switchCard.setChecked(false);
        } else {
            switchCard.setChecked(true);
        }
        //客户GPS排查
        ArrayList<BaseInfo.DataBean.BaseSpinnerListBean> hasDriverCardListBeanArrayList =
                (ArrayList<BaseInfo.DataBean.BaseSpinnerListBean>) dataBean.getHasDriverCardList();
        ArrayAdapter<BaseInfo.DataBean.BaseSpinnerListBean> GPSScreenAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.simple_spinner_item, hasDriverCardListBeanArrayList);
        GPSScreenAdapter.setDropDownViewResource(R.layout.my_drop_down_item);
        spGPSScreen.setAdapter(GPSScreenAdapter);
        for (BaseInfo.DataBean.BaseSpinnerListBean bean : hasDriverCardListBeanArrayList) {
            if (bean.getId().equals(dataBean.getHasDrvLic())) {
                spGPSScreen.setSelection(hasDriverCardListBeanArrayList.indexOf(bean));
            }
        }
        //我司GPS安装
        ArrayList<BaseInfo.DataBean.BaseSpinnerListBean> HxGpsInstallListBeanArrayList =
                (ArrayList<BaseInfo.DataBean.BaseSpinnerListBean>) dataBean.getHxGpsInstallList();
        ArrayAdapter<BaseInfo.DataBean.BaseSpinnerListBean> GPSInstallAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.simple_spinner_item, HxGpsInstallListBeanArrayList);
        GPSInstallAdapter.setDropDownViewResource(R.layout.my_drop_down_item);
        spGPSInstall.setAdapter(GPSInstallAdapter);
        for (BaseInfo.DataBean.BaseSpinnerListBean bean : HxGpsInstallListBeanArrayList) {
            if (bean.getId().equals(dataBean.getHxGpsInstall())) {
                spGPSInstall.setSelection(HxGpsInstallListBeanArrayList.indexOf(bean));
            }
        }
        //点评电源
        ArrayList<BaseInfo.DataBean.BaseSpinnerListBean> batterPowerListBeanArrayList =
                (ArrayList<BaseInfo.DataBean.BaseSpinnerListBean>) dataBean.getBatteryPowerList();
        ArrayAdapter<BaseInfo.DataBean.BaseSpinnerListBean> batteryPowerSupplyAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.simple_spinner_item, batterPowerListBeanArrayList);
        batteryPowerSupplyAdapter.setDropDownViewResource(R.layout.my_drop_down_item);
        spBatteryPowerSupply.setAdapter(batteryPowerSupplyAdapter);
        for (BaseInfo.DataBean.BaseSpinnerListBean bean : batterPowerListBeanArrayList) {
            if (bean.getId().equals(dataBean.getBatteryPower())) {
                spBatteryPowerSupply.setSelection(batterPowerListBeanArrayList.indexOf(bean));
            }
        }
        //锁具
        ArrayList<BaseInfo.DataBean.BaseSpinnerListBean> lockerListBeanArrayList =
                (ArrayList<BaseInfo.DataBean.BaseSpinnerListBean>) dataBean.getLockerList();
        ArrayAdapter<BaseInfo.DataBean.BaseSpinnerListBean> lockerAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.simple_spinner_item, lockerListBeanArrayList);
        lockerAdapter.setDropDownViewResource(R.layout.my_drop_down_item);
        spLocks.setAdapter(lockerAdapter);
        for (BaseInfo.DataBean.BaseSpinnerListBean bean : lockerListBeanArrayList) {
            if (bean.getId().equals(dataBean.getLocker())) {
                spLocks.setSelection(lockerListBeanArrayList.indexOf(bean));
            }
        }
        //车况简述
        ArrayList<BaseInfo.DataBean.BaseSpinnerListBean> carInfoExamListBeanArrayList =
                (ArrayList<BaseInfo.DataBean.BaseSpinnerListBean>) dataBean.getCarInfoExamList();
        ArrayAdapter<BaseInfo.DataBean.BaseSpinnerListBean> carPaperAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.simple_spinner_item, carInfoExamListBeanArrayList);
        carPaperAdapter.setDropDownViewResource(R.layout.my_drop_down_item);
        spCarPaper.setAdapter(carPaperAdapter);
        for (BaseInfo.DataBean.BaseSpinnerListBean bean : carInfoExamListBeanArrayList) {
            if (bean.getId().equals(dataBean.getLocker())) {
                spCarPaper.setSelection(carInfoExamListBeanArrayList.indexOf(bean));
            }
        }
        //其他反馈措施
        etAntitowing.setText(dataBean.getAntitowing());
        //等级备注
        etRegMemo.setText(dataBean.getRegMemo());
        //苦味
        etWhPosition.setText(dataBean.getWhPosition());
        //车辆状态
        ArrayList<BaseInfo.DataBean.BaseSpinnerListBean> carStateListBeanArrayList =
                (ArrayList<BaseInfo.DataBean.BaseSpinnerListBean>) dataBean.getCarStateList();
        ArrayAdapter<BaseInfo.DataBean.BaseSpinnerListBean> carStatusAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.simple_spinner_item, carStateListBeanArrayList);
        carStatusAdapter.setDropDownViewResource(R.layout.my_drop_down_item);
        spCarStatus.setAdapter(carStatusAdapter);
        for (BaseInfo.DataBean.BaseSpinnerListBean bean : carStateListBeanArrayList) {
            if (bean.getId().equals(dataBean.getCarState())) {
                spCarStatus.setSelection(carStateListBeanArrayList.indexOf(bean));
            }
        }
        //状态
        ArrayList<BaseInfo.DataBean.BaseSpinnerListBean> statusListBeanArrayList =
                (ArrayList<BaseInfo.DataBean.BaseSpinnerListBean>) dataBean.getStatusList();
        ArrayAdapter<BaseInfo.DataBean.BaseSpinnerListBean> statusAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.simple_spinner_item, statusListBeanArrayList);
        statusAdapter.setDropDownViewResource(R.layout.my_drop_down_item);
        spStatus.setAdapter(statusAdapter);
        for (BaseInfo.DataBean.BaseSpinnerListBean bean : statusListBeanArrayList) {
            if (bean.getId().equals(dataBean.getCarState())) {
                spStatus.setSelection(statusListBeanArrayList.indexOf(bean));
            }
        }
        //审批状态
        ArrayList<BaseInfo.DataBean.BaseSpinnerListBean> auditFlagListBeanArrayList =
                (ArrayList<BaseInfo.DataBean.BaseSpinnerListBean>) dataBean.getAuditFlagList();
        ArrayAdapter<BaseInfo.DataBean.BaseSpinnerListBean> approveStatusAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.simple_spinner_item, auditFlagListBeanArrayList);
        approveStatusAdapter.setDropDownViewResource(R.layout.my_drop_down_item);
        spApproveStatus.setAdapter(approveStatusAdapter);
        for (BaseInfo.DataBean.BaseSpinnerListBean bean : auditFlagListBeanArrayList) {
            if (bean.getId().equals(dataBean.getAuditFlag())) {
                spApproveStatus.setSelection(auditFlagListBeanArrayList.indexOf(bean));
            }
        }
    }

    public void showError() {
        nsvBasic.setVisibility(View.GONE);
        llSorry.setVisibility(View.VISIBLE);
        llNoData.setVisibility(View.GONE);
    }

    public void showNoData() {
        nsvBasic.setVisibility(View.GONE);
        llSorry.setVisibility(View.GONE);
        llNoData.setVisibility(View.VISIBLE);

    }
}
