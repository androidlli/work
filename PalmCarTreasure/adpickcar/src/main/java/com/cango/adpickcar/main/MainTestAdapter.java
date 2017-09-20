package com.cango.adpickcar.main;

import android.content.Context;

import com.cango.adpickcar.R;
import com.cango.adpickcar.baseAdapter.BaseAdapter;
import com.cango.adpickcar.baseAdapter.BaseHolder;

import java.util.List;

/**
 * Created by cango on 2017/9/19.
 */

public class MainTestAdapter extends BaseAdapter<String> {
    public MainTestAdapter(Context context, List<String> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.main_item;
    }

    @Override
    protected void convert(BaseHolder holder, String data) {

    }
}
