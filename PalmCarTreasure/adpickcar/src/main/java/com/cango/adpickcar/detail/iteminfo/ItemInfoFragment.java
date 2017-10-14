package com.cango.adpickcar.detail.iteminfo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.cango.adpickcar.R;
import com.cango.adpickcar.base.BaseFragment;
import com.cango.adpickcar.baseAdapter.BaseHolder;
import com.cango.adpickcar.baseAdapter.OnBaseItemClickListener;
import com.cango.adpickcar.camera.CameraActivity;
import com.cango.adpickcar.detail.DetailActivity;
import com.cango.adpickcar.detail.DetailFragment;
import com.cango.adpickcar.model.BaseInfo;
import com.cango.adpickcar.model.CarTakeTaskList;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;

public class ItemInfoFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {
    private static final int REQUEST_IMAGE_CAPTURE_INFO = 1;

    public static ItemInfoFragment getInstance() {
        ItemInfoFragment itemInfoFragment = new ItemInfoFragment();
        Bundle bundle = new Bundle();
        itemInfoFragment.setArguments(bundle);
        return itemInfoFragment;
    }

    @BindView(R.id.nsv_item)
    NestedScrollView nsvItem;
    @BindView(R.id.et_InCarDlvNO)
    EditText etInCarDlvNO;
    @BindView(R.id.cb1)
    CheckBox cb1;
    @BindView(R.id.cb2)
    CheckBox cb2;
    @BindView(R.id.cb3)
    CheckBox cb3;
    @BindView(R.id.cb4)
    CheckBox cb4;
    @BindView(R.id.cb5)
    CheckBox cb5;
    @BindView(R.id.cb6)
    CheckBox cb6;
    @BindView(R.id.cb7)
    CheckBox cb7;
    @BindView(R.id.cb8)
    CheckBox cb8;
    @BindView(R.id.et_InCarList)
    EditText etInCarList;
    @BindView(R.id.et_InCarNmb)
    EditText etInCarNmb;
    @BindView(R.id.et_InCarDlvComp)
    EditText etInCarDlvComp;
    @BindView(R.id.rv_item_info)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_sorry)
    LinearLayout llSorry;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;

    private DetailActivity mActivity;
    private ArrayList<BaseInfo.DataBean.InPicFileListBean> datas;
    private ItemInfoAdapter mAdapter;
    private CarTakeTaskList.DataBean.CarTakeTaskListBean mCarTakeTaskListBean;
    private boolean isEdit;
    private BaseInfo mBaseInfo;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_item_info;
    }

    @Override
    protected void initView() {
        initRecyclerView();
        cb1.setOnCheckedChangeListener(this);
        cb2.setOnCheckedChangeListener(this);
        cb3.setOnCheckedChangeListener(this);
        cb4.setOnCheckedChangeListener(this);
        cb5.setOnCheckedChangeListener(this);
        cb6.setOnCheckedChangeListener(this);
        cb7.setOnCheckedChangeListener(this);
        cb8.setOnCheckedChangeListener(this);
    }

    private void initRecyclerView() {
        datas = new ArrayList<>();
        BaseInfo.DataBean.InPicFileListBean lastBean = new BaseInfo.DataBean.InPicFileListBean();
        lastBean.setPicFileID(-1);
        datas.add(lastBean);
        mAdapter = new ItemInfoAdapter(mActivity, datas, false);
        mAdapter.setIsEdit(isEdit);
        mAdapter.setOnItemClickListener(new OnBaseItemClickListener<BaseInfo.DataBean.InPicFileListBean>() {
            @Override
            public void onItemClick(BaseHolder viewHolder, BaseInfo.DataBean.InPicFileListBean data, int position) {
                if (isEdit) {
                    if (data.getPicFileID() == -1) {
                        Intent cameraIntent = new Intent(mActivity, CameraActivity.class);
                        cameraIntent.putExtra("type", 0);
                        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE_INFO);
                    }
                } else {
                }
            }
        });
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 3));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mActivity = (DetailActivity) getActivity();
        mCarTakeTaskListBean = ((DetailFragment) getParentFragment()).mCarTakeTaskListBean;
        isEdit = ((DetailFragment) getParentFragment()).isEdit;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String photoPath = null;
        if (requestCode == REQUEST_IMAGE_CAPTURE_INFO) {
            if (resultCode == Activity.RESULT_OK) {
                photoPath = data.getStringExtra("path");
                if (!TextUtils.isEmpty(photoPath)) {
                    Collections.reverse(datas);
                    BaseInfo.DataBean.InPicFileListBean bean = new BaseInfo.DataBean.InPicFileListBean();
                    bean.setPicPath(photoPath);
                    datas.add(bean);
                    Collections.reverse(datas);
                    mAdapter.notifyDataSetChanged();
                }

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                if (photoPath != null) {
                    File emptyFile = new File(photoPath);
                    if (emptyFile.exists()) emptyFile.delete();
                }
            }
        }
    }

    public void updateUI(BaseInfo baseInfo) {
        mBaseInfo = baseInfo;
        if (isEdit) {
            etInCarDlvNO.setFocusable(true);
            etInCarDlvNO.setFocusableInTouchMode(true);
            etInCarList.setFocusable(true);
            etInCarList.setFocusableInTouchMode(true);
            etInCarNmb.setFocusable(true);
            etInCarNmb.setFocusableInTouchMode(true);
            etInCarDlvComp.setFocusable(true);
            etInCarDlvComp.setFocusableInTouchMode(true);
        } else {
            etInCarDlvNO.setFocusable(false);
            etInCarDlvNO.setFocusableInTouchMode(false);
            etInCarList.setFocusable(false);
            etInCarList.setFocusableInTouchMode(false);
            etInCarNmb.setFocusable(false);
            etInCarNmb.setFocusableInTouchMode(false);
            etInCarDlvComp.setFocusable(false);
            etInCarDlvComp.setFocusableInTouchMode(false);
            cb1.setEnabled(false);
            cb2.setEnabled(false);
            cb3.setEnabled(false);
            cb4.setEnabled(false);
            cb5.setEnabled(false);
            cb6.setEnabled(false);
            cb7.setEnabled(false);
            cb8.setEnabled(false);
        }
        nsvItem.setVisibility(View.VISIBLE);
        llSorry.setVisibility(View.GONE);
        llNoData.setVisibility(View.GONE);

        BaseInfo.DataBean dataBean = baseInfo.getData();
        etInCarDlvNO.setText(dataBean.getInCarDlvNO());
        //车内物品清单
        ArrayList<BaseInfo.DataBean.CarUsualListBean> carUsualListBeanArrayList =
                (ArrayList<BaseInfo.DataBean.CarUsualListBean>) dataBean.getInCarUsualList();
        for (int i = 0; i < carUsualListBeanArrayList.size(); i++) {
            BaseInfo.DataBean.CarUsualListBean bean = carUsualListBeanArrayList.get(i);
            if (i == 0) {
                cb1.setText(bean.getUsualName());
                cb1.setChecked(bean.getIsChecked().equals("1"));
            } else if (i == 1) {
                cb2.setText(bean.getUsualName());
                cb2.setChecked(bean.getIsChecked().equals("1"));
            } else if (i == 2) {
                cb3.setText(bean.getUsualName());
                cb3.setChecked(bean.getIsChecked().equals("1"));
            } else if (i == 3) {
                cb4.setText(bean.getUsualName());
                cb4.setChecked(bean.getIsChecked().equals("1"));
            } else if (i == 4) {
                cb5.setText(bean.getUsualName());
                cb5.setChecked(bean.getIsChecked().equals("1"));
            } else if (i == 5) {
                cb6.setText(bean.getUsualName());
                cb6.setChecked(bean.getIsChecked().equals("1"));
            } else if (i == 6) {
                cb7.setText(bean.getUsualName());
                cb7.setChecked(bean.getIsChecked().equals("1"));
            } else if (i == 7) {
                cb8.setText(bean.getUsualName());
                cb8.setChecked(bean.getIsChecked().equals("1"));
            }
        }
        etInCarList.setText(dataBean.getInCarList());
        etInCarNmb.setText(dataBean.getInCarNmb() + "");
        etInCarDlvComp.setText(dataBean.getInCarDlvComp());
    }

    public void showError() {
        nsvItem.setVisibility(View.GONE);
        llSorry.setVisibility(View.VISIBLE);
        llNoData.setVisibility(View.GONE);
    }

    public void showNoData() {
        nsvItem.setVisibility(View.GONE);
        llSorry.setVisibility(View.GONE);
        llNoData.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String check = isChecked ? "1" : "0";
        switch (buttonView.getId()) {
            case R.id.cb1:
                mBaseInfo.getData().getInCarUsualList().get(0).setIsChecked(check);
                break;
            case R.id.cb2:
                mBaseInfo.getData().getInCarUsualList().get(1).setIsChecked(check);
                break;
            case R.id.cb3:
                mBaseInfo.getData().getInCarUsualList().get(2).setIsChecked(check);
                break;
            case R.id.cb4:
                mBaseInfo.getData().getInCarUsualList().get(3).setIsChecked(check);
                break;
            case R.id.cb5:
                mBaseInfo.getData().getInCarUsualList().get(4).setIsChecked(check);
                break;
            case R.id.cb6:
                mBaseInfo.getData().getInCarUsualList().get(5).setIsChecked(check);
                break;
            case R.id.cb7:
                mBaseInfo.getData().getInCarUsualList().get(6).setIsChecked(check);
                break;
            case R.id.cb8:
                mBaseInfo.getData().getInCarUsualList().get(7).setIsChecked(check);
                break;
        }
    }
}
