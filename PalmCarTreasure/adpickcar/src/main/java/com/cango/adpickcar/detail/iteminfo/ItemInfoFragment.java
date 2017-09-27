package com.cango.adpickcar.detail.iteminfo;


import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;

import com.cango.adpickcar.R;
import com.cango.adpickcar.base.BaseFragment;

import butterknife.BindView;

public class ItemInfoFragment extends BaseFragment {
    public static ItemInfoFragment getInstance() {
        ItemInfoFragment itemInfoFragment = new ItemInfoFragment();
        Bundle bundle = new Bundle();
        itemInfoFragment.setArguments(bundle);
        return itemInfoFragment;
    }

    @BindView(R.id.nsv_item)
    NestedScrollView nsvItem;

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
