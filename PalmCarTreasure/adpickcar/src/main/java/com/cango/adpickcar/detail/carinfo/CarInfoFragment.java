package com.cango.adpickcar.detail.carinfo;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.cango.adpickcar.R;
import com.cango.adpickcar.base.BaseFragment;
import com.cango.adpickcar.detail.DetailActivity;
import com.cango.adpickcar.detail.DetailFragment;
import com.cango.adpickcar.detail.DetailPresenter;
import com.cango.adpickcar.model.CarInfo;
import com.cango.adpickcar.model.CarTakeTaskList;
import com.cango.adpickcar.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class CarInfoFragment extends BaseFragment {
    public static CarInfoFragment getInstance() {
        CarInfoFragment carInfoFragment = new CarInfoFragment();
        Bundle bundle = new Bundle();
        carInfoFragment.setArguments(bundle);
        return carInfoFragment;
    }

    @BindView(R.id.nsv_car)
    NestedScrollView nsvCar;
    @BindView(R.id.tv_CarBrandName)
    TextView tvCarBrandName;
    @BindView(R.id.tv_CarModelName)
    TextView tvCarModelName;
    @BindView(R.id.switch_isERPMapping)
    Switch switchIsERPMapping;
    @BindView(R.id.tv_carSeriesName)
    TextView tvCarSeriesName;
    @BindView(R.id.tv_color)
    TextView tvColor;
    @BindView(R.id.tv_discarno)
    TextView tvDiscarno;
    @BindView(R.id.tv_applycd)
    TextView tvApplyCD;
    @BindView(R.id.tv_fininstid)
    TextView tvFininstid;
    @BindView(R.id.tv_orgid)
    TextView tvOrgid;
    @BindView(R.id.tv_vin)
    TextView tvVin;
    @BindView(R.id.tv_custname)
    TextView tvCustName;
    @BindView(R.id.tv_licenseplatetype)
    TextView tvLicenseplateType;
    @BindView(R.id.tv_photo_number)
    TextView tvPhotoNumber;
    @BindView(R.id.tv_carcertno)
    TextView tvCarCertNO;
    @BindView(R.id.tv_mfyear)
    TextView tvMfyear;
    @BindView(R.id.tv_engineno)
    TextView tvEngineno;
    @BindView(R.id.tv_carregno)
    TextView tvCarRegno;
    @BindView(R.id.tv_carmodeltext)
    TextView tvCarModelText;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;
    @BindView(R.id.ll_sorry)
    LinearLayout llSorry;

    @OnClick({R.id.tv_car_info_alter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_car_info_alter:
                if (isEdit) {
                    final EditText et = new EditText(mActivity);
                    new AlertDialog.Builder(mActivity).setTitle("更新车牌号码")
                            .setView(et)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String input = et.getText().toString();
                                    if (input.equals("")) {
                                        ToastUtils.showShort("请输入内容");
                                    } else {
                                        tvPhotoNumber.setText(input);
                                    }
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                    break;
                }
        }
    }

    private DetailActivity mActivity;
    private DetailPresenter presenter;
    private CarTakeTaskList.DataBean.CarTakeTaskListBean mCarTakeTaskListBean;
    private boolean isEdit;
    private CarInfo mCarInfo;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_car_info;
    }

    @Override
    protected void initView() {
//        getData();
    }

    public void getData() {
        presenter.GetCarTakeStoreCarInfo(true, mCarTakeTaskListBean.getDisCarID() + "");
    }

    @Override
    protected void initData() {
        mActivity = (DetailActivity) getActivity();
        presenter = (DetailPresenter) ((DetailFragment) getParentFragment()).mPresenter;
        mCarTakeTaskListBean = ((DetailFragment) getParentFragment()).mCarTakeTaskListBean;
        isEdit = ((DetailFragment) getParentFragment()).isEdit;
    }

    public void updateUI(CarInfo carInfo) {
        mCarInfo = carInfo;
        if (isEdit) {
        } else {
            switchIsERPMapping.setEnabled(false);
            switchIsERPMapping.setFocusable(false);
            switchIsERPMapping.setFocusableInTouchMode(false);
        }
        CarInfo.DataBean dataBean = mCarInfo.getData();
        tvCarBrandName.setText(dataBean.getCarBrandName());
        tvCarModelName.setText(dataBean.getCarModelName());
        switchIsERPMapping.setChecked(dataBean.getIsErpMapping().equals("1"));
        tvCarSeriesName.setText(dataBean.getCarSeriesName());
        tvColor.setText(dataBean.getColor());
        tvDiscarno.setText(dataBean.getDisCarNo());
        tvApplyCD.setText(dataBean.getApplyCD());
        tvFininstid.setText(dataBean.getFininstName());
        tvOrgid.setText(dataBean.getOrgName());
        tvVin.setText(dataBean.getVin());
        tvCustName.setText(dataBean.getCustName());
        tvLicenseplateType.setText(dataBean.getLicensePlateType());
        tvPhotoNumber.setText(dataBean.getLicensePlateNo());
        tvCarCertNO.setText(dataBean.getCarcertNO());
        tvMfyear.setText(dataBean.getMfYear());
        tvEngineno.setText(dataBean.getEngineNO());
        tvCarRegno.setText(dataBean.getCarRegNO());
        tvCarModelText.setText(dataBean.getCarModelText());
    }

    public void showError() {
        nsvCar.setVisibility(View.GONE);
        llSorry.setVisibility(View.VISIBLE);
        llNoData.setVisibility(View.GONE);
    }

    public void showNoData() {
        nsvCar.setVisibility(View.GONE);
        llSorry.setVisibility(View.GONE);
        llNoData.setVisibility(View.VISIBLE);
    }

    public String getIsErpMapping() {
        mCarInfo.getData().setIsErpMapping(switchIsERPMapping.isChecked() ? "1" : "0");
        return switchIsERPMapping.isChecked() ? "1" : "0";
    }

    public String getLicenseplateNO() {
        mCarInfo.getData().setLicensePlateNo(tvPhotoNumber.getText().toString().trim());
        return tvPhotoNumber.getText().toString().trim();
    }
}
