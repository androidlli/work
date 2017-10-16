package com.cango.adpickcar.detail.iteminfo;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cango.adpickcar.ADApplication;
import com.cango.adpickcar.R;
import com.cango.adpickcar.api.Api;
import com.cango.adpickcar.baseAdapter.BaseAdapter;
import com.cango.adpickcar.baseAdapter.BaseHolder;
import com.cango.adpickcar.detail.DetailFragment;
import com.cango.adpickcar.model.BaseInfo;

import java.util.List;

/**
 * Created by cango on 2017/10/11.
 */

public class ItemInfoAdapter extends BaseAdapter<BaseInfo.DataBean.InPicFileListBean> {
    boolean isEdit;
    DetailFragment detailFragment;

    public ItemInfoAdapter(Context context, List<BaseInfo.DataBean.InPicFileListBean> datas, boolean isOpenLoadMore, DetailFragment detailFragment) {
        super(context, datas, isOpenLoadMore);
        this.detailFragment = detailFragment;
    }

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_info_item_image;
    }

    @Override
    protected void convert(BaseHolder holder, final BaseInfo.DataBean.InPicFileListBean data) {
        ImageView iv = holder.getView(R.id.iv_item_info);
        Button btn = holder.getView(R.id.btn_item_info);
        if (data.getPicFileID() == -1) {
            iv.setImageResource(R.drawable.addphoto);
            btn.setVisibility(View.INVISIBLE);
        } else {
            Glide.with(mContext).load(data.getPicPath()).into(iv);
            btn.setVisibility(View.VISIBLE);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit) {
//                    int removeIndex = mDatas.indexOf(data);
//                    mDatas.remove(removeIndex);
//                    ItemInfoAdapter.this.notifyItemRemoved(removeIndex);
//                    ItemInfoAdapter.this.notifyItemRangeChanged(removeIndex, mDatas.size());
                    detailFragment.DeletePhoto(0,-1, true, ADApplication.mSPUtils.getString(Api.USERID), data.getPicFileID() + "");
                } else {

                }
            }
        });
    }
}
