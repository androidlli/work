package com.cango.adpickcar.detail.imageinfo;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.cango.adpickcar.R;
import com.cango.adpickcar.base.BaseFragment;
import com.cango.adpickcar.baseAdapter.BaseAdapter;
import com.cango.adpickcar.baseAdapter.BaseHolder;
import com.cango.adpickcar.baseAdapter.GridSpacingItemDecoration;
import com.cango.adpickcar.baseAdapter.OnBaseItemClickListener;
import com.cango.adpickcar.camera.CameraActivity;
import com.cango.adpickcar.detail.DetailActivity;
import com.cango.adpickcar.util.SizeUtil;
import com.cango.adpickcar.util.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SupplementFragment extends BaseFragment {
    private static final int REQUEST_IMAGE_CAPTURE_SUPPLEMENT = 1003;
    public static SupplementFragment getInstance() {
        SupplementFragment fragment = new SupplementFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.rv_supplerment)
    RecyclerView mRecyclerView;

    private DetailActivity mActivity;
    private ArrayList<String> mDatas;
    private SupplementFragment.SupplementAdapter mAdapter;
    private int currentPostion;
    /**
     * 0:不是添加新的图片,1:是添加新的图片
     */
    private int fromType;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_supplement;
    }

    @Override
    protected void initView() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            mDatas.add(i + "");
        }
        mDatas.add("last");
        mAdapter = new SupplementFragment.SupplementAdapter(mActivity, mDatas, false);
        mAdapter.setOnItemClickListener(new OnBaseItemClickListener<String>() {
            @Override
            public void onItemClick(BaseHolder viewHolder, String data, int position) {
                if (data.equals("last")) {
                    fromType = 1;
                    currentPostion= position;
                    Intent cameraIntent = new Intent(mActivity, CameraActivity.class);
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE_SUPPLEMENT);
                }
            }
        });
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, SizeUtil.dp2px(mActivity, 10), true));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mActivity = (DetailActivity) getActivity();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String photoPath = null;
        if (requestCode == REQUEST_IMAGE_CAPTURE_SUPPLEMENT) {
            if (resultCode == Activity.RESULT_OK) {
                photoPath = data.getStringExtra("path");
                if (!TextUtils.isEmpty(photoPath)) {
                    switch (fromType){
                        case 0:
                            mDatas.set(currentPostion,photoPath);
                            mAdapter.notifyItemChanged(currentPostion);
                            break;
                        case 1:
                            String oldLastItem = mDatas.get(currentPostion);
                            mDatas.remove(oldLastItem);
                            mAdapter.notifyItemRemoved(currentPostion);
                            mDatas.add(photoPath);
                            mDatas.add(oldLastItem);
                            mAdapter.notifyItemRangeInserted(currentPostion,2);
                            mAdapter.notifyItemRangeChanged(currentPostion,2);
                            break;
                    }
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

    public class SupplementAdapter extends BaseAdapter<String> {
        CenterCrop centerCrop;
        SupplementFragment.GlideRoundTransform glideRoundTransform;

        public SupplementAdapter(Context context, List<String> datas, boolean isOpenLoadMore) {
            super(context, datas, isOpenLoadMore);
            centerCrop = new CenterCrop(mContext);
            glideRoundTransform = new SupplementFragment.GlideRoundTransform(mContext, 5);
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.image_card_item;
        }

        @Override
        protected void convert(BaseHolder holder, String data) {
            ImageView ivContent = holder.getView(R.id.iv_image_card_item);
            Button btnPrompt = holder.getView(R.id.btn_image_prompt);
            ImageView ivClose = holder.getView(R.id.iv_image_close);
            if (data.equals("last")) {
                Glide.with(mContext).load(R.drawable.morephotos)
                        .placeholder(R.drawable.photosimg)
//                    .error(R.drawable.placeholder_big)
                        .transform(glideRoundTransform)
                        .into(ivContent);
                btnPrompt.setVisibility(View.INVISIBLE);
                ivClose.setVisibility(View.INVISIBLE);
            } else {
                if (!TextUtils.isEmpty(data)) {
                    Glide.with(mContext).load(R.drawable.photosimg)
                            .placeholder(R.drawable.photosimg)
//                    .error(R.drawable.placeholder_big)
                            .transform(centerCrop, glideRoundTransform)
                            .into(ivContent);

                } else {
                    Glide.with(mContext).load(R.drawable.photosimg)
                            .placeholder(R.drawable.photosimg)
//                    .error(R.drawable.placeholder_big)
                            .transform(centerCrop, glideRoundTransform)
                            .into(ivContent);
                }
                btnPrompt.setVisibility(View.VISIBLE);
                ivClose.setVisibility(View.VISIBLE);
            }

        }
    }

    public class GlideRoundTransform extends BitmapTransformation {

        private float radius = 0f;

        public GlideRoundTransform(Context context, int dp) {
            super(context);
            this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return roundCrop(pool, toTransform);
        }

        private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName() + Math.round(radius);
        }
    }

}
