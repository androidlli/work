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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.cango.adpickcar.ADApplication;
import com.cango.adpickcar.R;
import com.cango.adpickcar.api.Api;
import com.cango.adpickcar.base.BaseFragment;
import com.cango.adpickcar.baseAdapter.BaseAdapter;
import com.cango.adpickcar.baseAdapter.BaseHolder;
import com.cango.adpickcar.baseAdapter.GridSpacingItemDecoration;
import com.cango.adpickcar.baseAdapter.OnBaseItemClickListener;
import com.cango.adpickcar.camera.CameraActivity;
import com.cango.adpickcar.detail.DetailActivity;
import com.cango.adpickcar.detail.DetailFragment;
import com.cango.adpickcar.detail.DetailPresenter;
import com.cango.adpickcar.model.CarFilesInfo;
import com.cango.adpickcar.model.CarTakeTaskList;
import com.cango.adpickcar.model.PhotoResult;
import com.cango.adpickcar.util.CommUtil;
import com.cango.adpickcar.util.SizeUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FacadeFragment extends BaseFragment {
    private static final int REQUEST_IMAGE_CAPTURE_FACADE = 1001;

    public static FacadeFragment getInstance() {
        FacadeFragment fragment = new FacadeFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.rv_facade)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;
    @BindView(R.id.ll_sorry)
    LinearLayout llSorry;

    private DetailActivity mActivity;
    private DetailFragment detailFragment;
    private ArrayList<CarFilesInfo.DataBean.SurfaceFileListBean> mDatas;
    private DetailPresenter presenter;
    private CarTakeTaskList.DataBean.CarTakeTaskListBean mCarTakeTaskListBean;
    private CarFilesInfo mCarFilesInfo;
    private boolean isEdit;
    private int currentPostion;
    /**
     * 0:不是添加新的图片,1:是添加新的图片
     */
    private int fromType;
    private FacadeAdapter mAdapter;
    boolean isOver;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_facade;
    }

    @Override
    protected void initView() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        mDatas = new ArrayList<>();
        mAdapter = new FacadeAdapter(mActivity, mDatas, false);
        mAdapter.setOnItemClickListener(new OnBaseItemClickListener<CarFilesInfo.DataBean.SurfaceFileListBean>() {
            @Override
            public void onItemClick(BaseHolder viewHolder, CarFilesInfo.DataBean.SurfaceFileListBean data, int position) {
                if (!isEdit)
                    return;
                if (data.getPicFileID() == -1) {
                    fromType = 1;
                    currentPostion = position;
                    Intent cameraIntent = new Intent(mActivity, CameraActivity.class);
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE_FACADE);
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
        detailFragment = (DetailFragment) getParentFragment().getParentFragment();
        presenter = (DetailPresenter) ((DetailFragment) (getParentFragment().getParentFragment())).mPresenter;
        mCarTakeTaskListBean = ((DetailFragment) (getParentFragment().getParentFragment())).mCarTakeTaskListBean;
        isEdit = ((DetailFragment) (getParentFragment().getParentFragment())).isEdit;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isOver) {
            isOver = true;
            updateUI(((ImageInfoFragment) getParentFragment()).mCarFilesInfo);
        }
    }

    public void updateUI(CarFilesInfo carFilesInfo) {
        mCarFilesInfo = carFilesInfo;
        if (CommUtil.checkIsNull(mCarFilesInfo.getData().getSurfaceFileList())) {
            showNoData();
        } else {
            if (isEdit) {
                mDatas.addAll(mCarFilesInfo.getData().getSurfaceFileList());
                CarFilesInfo.DataBean.SurfaceFileListBean lastBean = new CarFilesInfo.DataBean.SurfaceFileListBean();
                lastBean.setPicFileID(-1);
                if (isEdit) {
                    mDatas.add(lastBean);
                } else {
                }
                mAdapter.notifyDataSetChanged();
            } else {
                mDatas.addAll(mCarFilesInfo.getData().getSurfaceFileList());
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    public void showError() {
        mRecyclerView.setVisibility(View.GONE);
        llSorry.setVisibility(View.VISIBLE);
        llNoData.setVisibility(View.GONE);
    }

    public void showNoData() {
        mRecyclerView.setVisibility(View.GONE);
        llSorry.setVisibility(View.GONE);
        llNoData.setVisibility(View.VISIBLE);
    }

    public void updateAddPhoto(PhotoResult photoResult) {
        if (fromType == 0) {
            //更新有subname的图片地址
            CarFilesInfo.DataBean.SurfaceFileListBean bean = mDatas.get(currentPostion);
            bean.setPicFileID(photoResult.getData().getPicFileID());
            bean.setPicPath(photoResult.getData().getPicPath());
            bean.setThumbPath(photoResult.getData().getThumbPath());
            mAdapter.notifyItemChanged(currentPostion);
        } else if (fromType == 1) {
            //增加一个图片
            PhotoResult.DataBean result = photoResult.getData();
            CarFilesInfo.DataBean.SurfaceFileListBean bean = new CarFilesInfo.DataBean.SurfaceFileListBean();
            bean.setPicFileID(result.getPicFileID());
            bean.setPicPath(result.getPicPath());
            bean.setThumbPath(result.getThumbPath());
            mDatas.add(currentPostion, bean);
            mAdapter.notifyItemInserted(currentPostion);
            mAdapter.notifyItemChanged(currentPostion);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void updateDeletePhoto() {
        CarFilesInfo.DataBean.SurfaceFileListBean bean = mDatas.get(currentPostion);
        if (bean.getPicFileID() == -1) {
            //类型是拍摄更多图片的按钮
        } else if (TextUtils.isEmpty(bean.getSubName())) {
            //类型拍摄更多后的图片
            mDatas.remove(currentPostion);
            mAdapter.notifyItemRemoved(currentPostion);
            mAdapter.notifyItemRangeChanged(currentPostion, mDatas.size());
        } else {
            //类型是有btn按钮的图片
            bean.setPicFileID(0);
            bean.setPicPath("");
            bean.setThumbPath("");
            mAdapter.notifyItemChanged(currentPostion);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String photoPath = null;
        if (requestCode == REQUEST_IMAGE_CAPTURE_FACADE) {
            if (resultCode == Activity.RESULT_OK) {
                photoPath = data.getStringExtra("path");
                if (!TextUtils.isEmpty(photoPath)) {
                    switch (fromType) {
                        case 0:
//                            bean.setPicPath(photoPath);
//                            mAdapter.notifyItemChanged(currentPostion);
                            CarFilesInfo.DataBean.SurfaceFileListBean bean = mDatas.get(currentPostion);
                            detailFragment.zipPicture(1, 0, photoPath, ADApplication.mSPUtils.getString(Api.USERID),
                                    mCarTakeTaskListBean.getDisCarID() + "", "20", bean.getSubCategory(),
                                    bean.getSubID(), bean.getPicFileID() + "");
                            break;
                        case 1:
//                            CarFilesInfo.DataBean.SurfaceFileListBean newItem = new CarFilesInfo.DataBean.SurfaceFileListBean();
//                            newItem.setPicPath(photoPath);
//                            mDatas.add(currentPostion, newItem);
//                            mAdapter.notifyItemInserted(currentPostion);
//                            mAdapter.notifyItemChanged(currentPostion);
//                            mAdapter.notifyDataSetChanged();
                            detailFragment.zipPicture(1, 0, photoPath, ADApplication.mSPUtils.getString(Api.USERID),
                                    mCarTakeTaskListBean.getDisCarID() + "", "20", "10",
                                    null, null);
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

    public class FacadeAdapter extends BaseAdapter<CarFilesInfo.DataBean.SurfaceFileListBean> {
        CenterCrop centerCrop;
        GlideRoundTransform glideRoundTransform;

        public FacadeAdapter(Context context, List<CarFilesInfo.DataBean.SurfaceFileListBean> datas, boolean isOpenLoadMore) {
            super(context, datas, isOpenLoadMore);
            centerCrop = new CenterCrop(mContext);
            glideRoundTransform = new GlideRoundTransform(mContext, 5);
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.image_card_item;
        }

        @Override
        protected void convert(final BaseHolder holder, final CarFilesInfo.DataBean.SurfaceFileListBean data) {
            ImageView ivContent = holder.getView(R.id.iv_image_card_item);
            Button btnPrompt = holder.getView(R.id.btn_image_prompt);
            ImageView ivClose = holder.getView(R.id.iv_image_close);
            btnPrompt.setText(data.getSubName());
            if (data.getPicFileID() == -1) {
                Glide.with(mContext).load(R.drawable.morephotos)
                        .placeholder(R.drawable.photosimg)
                        .error(R.drawable.loadfailure)
                        .transform(glideRoundTransform)
                        .into(ivContent);
                btnPrompt.setVisibility(View.INVISIBLE);
                ivClose.setVisibility(View.INVISIBLE);
            } else {
                if (!TextUtils.isEmpty(data.getPicPath())) {
                    Glide.with(mContext).load(data.getPicPath())
                            .placeholder(R.drawable.photosimg)
                            .error(R.drawable.loadfailure)
                            .transform(centerCrop, glideRoundTransform)
                            .into(ivContent);
                    //防止自己添加的图片有btn

                } else {
                    Glide.with(mContext).load(R.drawable.photosimg)
                            .placeholder(R.drawable.photosimg)
                            .error(R.drawable.loadfailure)
                            .transform(centerCrop, glideRoundTransform)
                            .into(ivContent);
                }
                //防止拍摄更多的照片有btn
                if (!TextUtils.isEmpty(data.getSubName())) {
                    btnPrompt.setVisibility(View.VISIBLE);
                } else {
                    btnPrompt.setVisibility(View.INVISIBLE);
                }
                ivClose.setVisibility(View.VISIBLE);
            }
            btnPrompt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isEdit)
                        return;
                    fromType = 0;
                    currentPostion = holder.getAdapterPosition();
                    Intent cameraIntent = new Intent(mActivity, CameraActivity.class);
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE_FACADE);
                }
            });
            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (data.getPicFileID() == -1) {
//                        //类型是拍摄更多图片的按钮
//                    } else if (TextUtils.isEmpty(data.getSubName())) {
//                        //类型拍摄更多后的图片
//                        int deletePosition = holder.getAdapterPosition();
//                        mDatas.remove(deletePosition);
//                        mAdapter.notifyItemRemoved(deletePosition);
//                    } else {
//                        //类型是有btn按钮的图片
//                        int changePosition = holder.getAdapterPosition();
//                        mDatas.get(changePosition).setPicPath("");
//                        mAdapter.notifyItemChanged(changePosition);
//                    }
                    currentPostion = holder.getAdapterPosition();
                    detailFragment.DeletePhoto(1, 0, true, ADApplication.mSPUtils.getString(Api.USERID), data.getPicFileID() + "");
                }
            });

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
