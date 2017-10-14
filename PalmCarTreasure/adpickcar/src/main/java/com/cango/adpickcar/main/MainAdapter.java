package com.cango.adpickcar.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cango.adpickcar.R;
import com.cango.adpickcar.baseAdapter.BaseAdapter;
import com.cango.adpickcar.baseAdapter.BaseHolder;
import com.cango.adpickcar.model.CarTakeTaskList;

import java.util.List;

/**
 * Created by cango on 2017/9/19.
 */

public class MainAdapter extends BaseAdapter<CarTakeTaskList.DataBean.CarTakeTaskListBean> {
    public MainAdapter(Context context, List<CarTakeTaskList.DataBean.CarTakeTaskListBean> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.main_item;
    }

    @Override
    protected void convert(BaseHolder holder, final CarTakeTaskList.DataBean.CarTakeTaskListBean data) {
        TextView tvApplyCD = holder.getView(R.id.tv_ApplyCD);
        ImageView ivCarBrandPicURL = holder.getView(R.id.iv_CarBrandPicURL);
        TextView tvCarBrandName = holder.getView(R.id.tv_CarBrandName);
        TextView tvTowingcoShortName = holder.getView(R.id.tv_TowingcoShortName);
        TextView tvCTMan = holder.getView(R.id.tv_CTMan);
        TextView tvCustName = holder.getView(R.id.tv_CustName);
        TextView tvLicensePlateNo = holder.getView(R.id.tv_LicensePlateNo);
        TextView tvColor = holder.getView(R.id.tv_Color);
        ImageView ivPhone = holder.getView(R.id.iv_main_item_phone);
        tvApplyCD.setText(data.getApplyCD());
        Glide.with(mContext)
                .load(data.getCarBrandPicURL())
                .into(ivCarBrandPicURL);
        tvCarBrandName.setText(data.getCarBrandName());
        tvTowingcoShortName.setText(data.getTowingcoShortName());
        tvCTMan.setText(data.getCTMan());
        tvCustName.setText(data.getCustName());
        tvLicensePlateNo.setText(data.getLicenseplateNO());
        tvColor.setText(data.getColor());

        ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + data.getCTMobile()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }
}
