package com.cango.adpickcar.detail.imageinfo;


import android.os.Bundle;

import com.cango.adpickcar.R;
import com.cango.adpickcar.base.BaseFragment;
import com.cango.adpickcar.detail.carinfo.CarInfoFragment;

public class ImageInfoFragment extends BaseFragment {
    public static ImageInfoFragment getInstance() {
        ImageInfoFragment imageInfoFragment = new ImageInfoFragment();
        Bundle bundle = new Bundle();
        imageInfoFragment.setArguments(bundle);
        return imageInfoFragment;
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_image_info;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
