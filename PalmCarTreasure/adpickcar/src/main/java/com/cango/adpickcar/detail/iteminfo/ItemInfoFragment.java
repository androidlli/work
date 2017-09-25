package com.cango.adpickcar.detail.iteminfo;


import android.os.Bundle;

import com.cango.adpickcar.R;
import com.cango.adpickcar.base.BaseFragment;

public class ItemInfoFragment extends BaseFragment {
    public static ItemInfoFragment getInstance() {
        ItemInfoFragment itemInfoFragment = new ItemInfoFragment();
        Bundle bundle = new Bundle();
        itemInfoFragment.setArguments(bundle);
        return itemInfoFragment;
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_item_info;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
