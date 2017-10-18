package com.cango.adpickcar.main;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cango.adpickcar.ADApplication;
import com.cango.adpickcar.R;
import com.cango.adpickcar.api.Api;
import com.cango.adpickcar.base.BaseFragment;
import com.cango.adpickcar.baseAdapter.BaseHolder;
import com.cango.adpickcar.baseAdapter.OnBaseItemClickListener;
import com.cango.adpickcar.baseAdapter.OnLoadMoreListener;
import com.cango.adpickcar.customview.UpdateFragment;
import com.cango.adpickcar.detail.DetailActivity;
import com.cango.adpickcar.login.LoginActivity;
import com.cango.adpickcar.model.CarTakeTaskList;
import com.cango.adpickcar.model.EventModel.RefreshMainEvent;
import com.cango.adpickcar.resetps.ResetPSActivity;
import com.cango.adpickcar.util.BarUtil;
import com.cango.adpickcar.util.CommUtil;
import com.cango.adpickcar.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MainFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, MainContract.View, EasyPermissions.PermissionCallbacks {
    /**
     * 查询类型（1：未接车 2.未提交 3：审核中 4：审批退回 5.审批通过）
     */
    public static final int WEIJIECHE = 1;
    public static final int WEITIJIAO = 2;
    public static final int SHENHEZHON = 3;
    public static final int SHENHETUIHUI = 4;
    public static final int SHENHETONGUO = 5;
    public int CURRENT_TYPE = -1;


    public static MainFragment getInstance() {
        MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    @BindView(R.id.drawer_main)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.rl_drawer)
    RelativeLayout rlDrawer;
    @BindView(R.id.tv_user_mobile)
    TextView tvUserMobile;
    @BindView(R.id.rl_main_head)
    RelativeLayout rlHead;
    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;
    @BindView(R.id.ll_main_search)
    LinearLayout llMainSearch;
    @BindView(R.id.iv_main_popup_parent)
    ImageView ivPopupParent;
    @BindView(R.id.cardview_main)
    CardView mCardView;
    @BindView(R.id.tv_main_first_num)
    TextView tvFirstNum;
    @BindView(R.id.iv_main_first)
    ImageView ivFirst;
    @BindView(R.id.tv_main_first)
    TextView tvFirst;
    @BindView(R.id.tv_main_second_num)
    TextView tvSecondNum;
    @BindView(R.id.iv_main_second)
    ImageView ivSecond;
    @BindView(R.id.tv_main_second)
    TextView tvSecond;
    @BindView(R.id.tv_main_third_num)
    TextView tvThirdNum;
    @BindView(R.id.iv_main_third)
    ImageView ivThird;
    @BindView(R.id.tv_main_third)
    TextView tvThird;
    @BindView(R.id.tv_main_fourth_num)
    TextView tvFourthNum;
    @BindView(R.id.iv_main_fourth)
    ImageView ivFourth;
    @BindView(R.id.tv_main_fourth)
    TextView tvFourth;
    @BindView(R.id.tv_main_fifth_num)
    TextView tvFifthNum;
    @BindView(R.id.iv_main_fifth)
    ImageView ivFifth;
    @BindView(R.id.tv_main_fifth)
    TextView tvFifth;
    @BindView(R.id.srl_main)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.ll_sorry)
    LinearLayout llSorry;
    @BindView(R.id.recyclerview_main)
    RecyclerView mRecyclerView;
    @BindView(R.id.fl_shadow)
    FrameLayout flShadow;
    @BindView(R.id.avl_login_indicator)
    AVLoadingIndicatorView mLoadView;

    @OnClick({R.id.ll_modify_ps, R.id.ll_main_search, R.id.rl_main_first, R.id.rl_main_second, R.id.rl_main_third,
            R.id.rl_main_fourth, R.id.rl_main_fifth, R.id.ll_sign_off, R.id.rl_drawer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_modify_ps:
                startActivity(new Intent(mActivity, ResetPSActivity.class));
                break;
            case R.id.ll_main_search:
                showPopSearch();
                break;
            case R.id.rl_main_first:
                llMainSearch.setVisibility(View.VISIBLE);
                selectTitleStatus(0);
                if (CURRENT_TYPE != WEIJIECHE) {
                    CURRENT_TYPE = WEIJIECHE;
                    onRefresh();
                } else {
                    if (!TextUtils.isEmpty(mLicensePlateNO) || !TextUtils.isEmpty(mCustName) || !TextUtils.isEmpty(mCarBrandName)) {
                        mLicensePlateNO = null;
                        mCustName = null;
                        mCarBrandName = null;
                        onRefresh();
                    }
                }
                break;
            case R.id.rl_main_second:
                llMainSearch.setVisibility(View.INVISIBLE);
                selectTitleStatus(1);
                if (CURRENT_TYPE != WEITIJIAO) {
                    CURRENT_TYPE = WEITIJIAO;
                    onRefresh();
                }
                break;
            case R.id.rl_main_third:
                llMainSearch.setVisibility(View.INVISIBLE);
                selectTitleStatus(2);
                if (CURRENT_TYPE != SHENHEZHON) {
                    CURRENT_TYPE = SHENHEZHON;
                    onRefresh();
                }
                break;
            case R.id.rl_main_fourth:
                llMainSearch.setVisibility(View.INVISIBLE);
                selectTitleStatus(3);
                if (CURRENT_TYPE != SHENHETUIHUI) {
                    CURRENT_TYPE = SHENHETUIHUI;
                    onRefresh();
                }
                break;
            case R.id.rl_main_fifth:
                llMainSearch.setVisibility(View.INVISIBLE);
                selectTitleStatus(4);
                if (CURRENT_TYPE != SHENHETONGUO) {
                    CURRENT_TYPE = SHENHETONGUO;
                    onRefresh();
                }
                break;
            case R.id.ll_sign_off:
                if (isDoLogout) {
                    isDoLogout = false;
                    mPresenter.logout(true, ADApplication.mSPUtils.getString(Api.USERID));
                }
                break;
            case R.id.rl_drawer:
                break;
        }
    }

    private MainActivity mActivity;
    private MainContract.Presenter mPresenter;
    private MainAdapter mAdapter;
    private ArrayList<CarTakeTaskList.DataBean.CarTakeTaskListBean> datas;
    private int itemOnClickPosition;
    private Badge firstQV, secondQV, thirdQV, fourthQV, fifthQV;
    private int selectColor, noSelectColor;
    private int mPageCount = 1, mTempPageCount = 2;
    static int PAGE_SIZE = 10;
    private boolean isLoadMore;
    //客户名称 客户车牌号
    private String mCustName, mLicensePlateNO, mCarBrandName;
    private boolean isDoLogout = true, isDoCarTakeStoreConfirm = true;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!CommUtil.checkIsNull(mPresenter))
            mPresenter.onDetach();
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_main;
    }

    /**
     *
     */
    @Override
    protected void initView() {
        showLoadView(false);
        int statusBarHeight = BarUtil.getStatusBarHeight(getActivity());
        int actionBarHeight = BarUtil.getActionBarHeight(getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight + actionBarHeight);
            mToolbar.setLayoutParams(layoutParams);
            mToolbar.setPadding(0, statusBarHeight, 0, 0);
        }
        mActivity.setSupportActionBar(mToolbar);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity.getSupportActionBar().setHomeButtonEnabled(true);
        mActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(mActivity, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mToggle.syncState();
        mToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });

        mDrawerLayout.addDrawerListener(mToggle);

        initNum(0, 0, 0, 0, 0);
        selectTitleStatus(0);
        CURRENT_TYPE = WEIJIECHE;
        tvUserMobile.setText(ADApplication.mSPUtils.getString(Api.MOBILE));
        initRecyclerView();
    }

    private void initNum(int first, int second, int third, int fourth, int fifth) {
        firstQV = new QBadgeView(mActivity).bindTarget(tvFirstNum).setBadgeNumber(first).setShowShadow(false).setBadgeBackgroundColor(Color.TRANSPARENT);
        secondQV = new QBadgeView(mActivity).bindTarget(tvSecondNum).setBadgeNumber(second).setShowShadow(false).setBadgeBackgroundColor(Color.TRANSPARENT);
        thirdQV = new QBadgeView(mActivity).bindTarget(tvThirdNum).setBadgeNumber(third).setShowShadow(false).setBadgeBackgroundColor(Color.TRANSPARENT);
        fourthQV = new QBadgeView(mActivity).bindTarget(tvFourthNum).setBadgeNumber(fourth).setBadgeTextColor(Color.WHITE).setShowShadow(false);
        fifthQV = new QBadgeView(mActivity).bindTarget(tvFifthNum).setBadgeNumber(fifth).setShowShadow(false).setBadgeBackgroundColor(Color.TRANSPARENT);
    }

    private void selectTitleStatus(int position) {
        switch (position) {
            case 0:
                firstQV.setBadgeTextColor(selectColor);
                secondQV.setBadgeTextColor(noSelectColor);
                thirdQV.setBadgeTextColor(noSelectColor);
                fourthQV.setBadgeBackgroundColor(noSelectColor);
                fifthQV.setBadgeTextColor(noSelectColor);
                ivFirst.setImageResource(R.drawable.weijieche_on);
                tvFirst.setTextColor(selectColor);
                ivSecond.setImageResource(R.drawable.weitijiao_off);
                tvSecond.setTextColor(noSelectColor);
                ivThird.setImageResource(R.drawable.shenhezhong_off);
                tvThird.setTextColor(noSelectColor);
                ivFourth.setImageResource(R.drawable.shenhetuihui_off);
                tvFourth.setTextColor(noSelectColor);
                ivFifth.setImageResource(R.drawable.shenhetongguo_off);
                tvFifth.setTextColor(noSelectColor);
                break;
            case 1:
                firstQV.setBadgeTextColor(noSelectColor);
                secondQV.setBadgeTextColor(selectColor);
                thirdQV.setBadgeTextColor(noSelectColor);
                fourthQV.setBadgeBackgroundColor(noSelectColor);
                fifthQV.setBadgeTextColor(noSelectColor);
                ivFirst.setImageResource(R.drawable.weijieche_off);
                tvFirst.setTextColor(noSelectColor);
                ivSecond.setImageResource(R.drawable.weitijiao_on);
                tvSecond.setTextColor(selectColor);
                ivThird.setImageResource(R.drawable.shenhezhong_off);
                tvThird.setTextColor(noSelectColor);
                ivFourth.setImageResource(R.drawable.shenhetuihui_off);
                tvFourth.setTextColor(noSelectColor);
                ivFifth.setImageResource(R.drawable.shenhetongguo_off);
                tvFifth.setTextColor(noSelectColor);
                break;
            case 2:
                firstQV.setBadgeTextColor(noSelectColor);
                secondQV.setBadgeTextColor(noSelectColor);
                thirdQV.setBadgeTextColor(selectColor);
                fourthQV.setBadgeBackgroundColor(noSelectColor);
                fifthQV.setBadgeTextColor(noSelectColor);
                ivFirst.setImageResource(R.drawable.weijieche_off);
                tvFirst.setTextColor(noSelectColor);
                ivSecond.setImageResource(R.drawable.weitijiao_off);
                tvSecond.setTextColor(noSelectColor);
                ivThird.setImageResource(R.drawable.shenhezhong_on);
                tvThird.setTextColor(selectColor);
                ivFourth.setImageResource(R.drawable.shenhetuihui_off);
                tvFourth.setTextColor(noSelectColor);
                ivFifth.setImageResource(R.drawable.shenhetongguo_off);
                tvFifth.setTextColor(noSelectColor);
                break;
            case 3:
                firstQV.setBadgeTextColor(noSelectColor);
                secondQV.setBadgeTextColor(noSelectColor);
                thirdQV.setBadgeTextColor(noSelectColor);
                fourthQV.setBadgeBackgroundColor(selectColor);
                fifthQV.setBadgeTextColor(noSelectColor);
                ivFirst.setImageResource(R.drawable.weijieche_off);
                tvFirst.setTextColor(noSelectColor);
                ivSecond.setImageResource(R.drawable.weitijiao_off);
                tvSecond.setTextColor(noSelectColor);
                ivThird.setImageResource(R.drawable.shenhezhong_off);
                tvThird.setTextColor(noSelectColor);
                ivFourth.setImageResource(R.drawable.shenhetuihui_on);
                tvFourth.setTextColor(selectColor);
                ivFifth.setImageResource(R.drawable.shenhetongguo_off);
                tvFifth.setTextColor(noSelectColor);
                break;
            case 4:
                firstQV.setBadgeTextColor(noSelectColor);
                secondQV.setBadgeTextColor(noSelectColor);
                thirdQV.setBadgeTextColor(noSelectColor);
                fourthQV.setBadgeBackgroundColor(noSelectColor);
                fifthQV.setBadgeTextColor(selectColor);
                ivFirst.setImageResource(R.drawable.weijieche_off);
                tvFirst.setTextColor(noSelectColor);
                ivSecond.setImageResource(R.drawable.weitijiao_off);
                tvSecond.setTextColor(noSelectColor);
                ivThird.setImageResource(R.drawable.shenhezhong_off);
                tvThird.setTextColor(noSelectColor);
                ivFourth.setImageResource(R.drawable.shenhetuihui_off);
                tvFourth.setTextColor(noSelectColor);
                ivFifth.setImageResource(R.drawable.shenhetongguo_on);
                tvFifth.setTextColor(selectColor);
                break;
        }
    }

    private void initRecyclerView() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.green, R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        datas = new ArrayList<>();
        mAdapter = new MainAdapter(mActivity, datas, true);
        mAdapter.setLoadingView(R.layout.load_loading_layout);
        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if (mPageCount == mTempPageCount && !isReload) {
                    return;
                }
                isLoadMore = true;
                mPageCount = mTempPageCount;
                mPresenter.loadListByStatus(false, ADApplication.mSPUtils.getString(Api.USERID), mCustName,
                        mLicensePlateNO, mCarBrandName, CURRENT_TYPE + "", mPageCount + "", PAGE_SIZE + "");
            }
        });
        mAdapter.setOnItemClickListener(new OnBaseItemClickListener<CarTakeTaskList.DataBean.CarTakeTaskListBean>() {
            @Override
            public void onItemClick(BaseHolder viewHolder, final CarTakeTaskList.DataBean.CarTakeTaskListBean data, final int position) {
                switch (CURRENT_TYPE) {
                    case WEIJIECHE:
                        new AlertDialog.Builder(mActivity)
                                .setTitle("确认接车")
                                .setMessage("申请编号：" + data.getApplyCD() + "\r\n" + "客户姓名：" + data.getCustName() + "\r\n" +
                                        "车牌号码：" + data.getLicenseplateNO() + "\r\n" + "颜色：" + data.getColor())
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        itemOnClickPosition = position;
                                        if (isDoCarTakeStoreConfirm) {
                                            isDoCarTakeStoreConfirm = false;
                                            mPresenter.GetCarTakeTaskList(true, ADApplication.mSPUtils.getString(Api.USERID),
                                                    data.getCTTaskID() + "", data.getPlanctWhno(), data.getVin(), data.getCarID() + "");

                                        }
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .create().show();
                        break;
                    case WEITIJIAO:
                    case SHENHEZHON:
                    case SHENHETONGUO:
                        Intent intent = new Intent(mActivity, DetailActivity.class);
                        intent.putExtra("CarTakeTaskListBean", data);
                        intent.putExtra("Type", CURRENT_TYPE);
                        startActivity(intent);
                        break;
                    case SHENHETUIHUI:
                        new AlertDialog.Builder(mActivity)
                                .setTitle("驳回原因")
                                .setMessage(data.getReturnReason())
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(mActivity, DetailActivity.class);
                                        intent.putExtra("CarTakeTaskListBean", data);
                                        intent.putExtra("Type", CURRENT_TYPE);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .create().show();
                        break;
                }
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        onRefresh();
    }

    @Override
    protected void initData() {
        mActivity = (MainActivity) getActivity();
        selectColor = getResources().getColor(R.color.colorPrimary);
        noSelectColor = getResources().getColor(R.color.ad888888);
    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        mPageCount = 1;
        mTempPageCount = 2;
        datas.clear();
        mAdapter.setLoadingView(R.layout.load_loading_layout);
        mAdapter.notifyDataSetChanged();
        mPresenter.loadListByStatus(true, ADApplication.mSPUtils.getString(Api.USERID), mCustName,
                mLicensePlateNO, mCarBrandName, CURRENT_TYPE + "", mPageCount + "", PAGE_SIZE + "");
    }

    private void showPopSearch() {
        if (mCardView.getWidth() > 0) {
            PopupWindow mSearchPopup = getPopupWindow(mActivity, R.layout.main_search_pop);
            rlHead.setBackgroundColor(Color.parseColor("#36000000"));
            flShadow.setVisibility(View.VISIBLE);
            mSearchPopup.showAsDropDown(ivPopupParent);
        }
    }

    private void closePopSearch() {
        rlHead.setBackgroundColor(Color.WHITE);
        flShadow.setVisibility(View.GONE);
    }

    public PopupWindow getPopupWindow(Context context, final int layoutId) {
        final PopupWindow popupWindow = new PopupWindow(mCardView.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        final View popupView = LayoutInflater.from(context).inflate(layoutId, null);
        final EditText etPlateNO = (EditText) popupView.findViewById(R.id.et_PlateNO);
        final EditText etName = (EditText) popupView.findViewById(R.id.et_Name);
        final EditText etCarType = (EditText) popupView.findViewById(R.id.et_car_type);
        Button btn = (Button) popupView.findViewById(R.id.btn_search);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String plateNo = etPlateNO.getText().toString().trim();
                String name = etName.getText().toString().trim();
                String carType = etCarType.getText().toString().trim();
                if (!TextUtils.isEmpty(plateNo) || !TextUtils.isEmpty(name) || !TextUtils.isEmpty(carType)) {
                    mLicensePlateNO = plateNo;
                    mCustName = name;
                    mCarBrandName = carType;
                    popupWindow.dismiss();
                    onRefresh();
                } else {
                    ToastUtils.showShort(R.string.input_params_prompt);
                }
            }
        });
        popupWindow.setContentView(popupView);
//        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                closePopSearch();
            }
        });
        popupWindow.update();
        return popupWindow;
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoadView(boolean isShow) {
        if (isShow)
            mLoadView.smoothToShow();
        else
            mLoadView.smoothToHide();
    }

    @Override
    public void showMainIndicator(final boolean active) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(active);
            }
        });
    }

    @Override
    public void showMainError() {
        if (isLoadMore) {
            mAdapter.setLoadFailedView(R.layout.load_failed_layout);
        } else {
            llSorry.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showNoData() {
        if (isLoadMore) {
            mAdapter.setLoadEndView(R.layout.load_end_layout);
        } else {
            llSorry.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showMainTitle(CarTakeTaskList.DataBean dataBean) {
        firstQV.setBadgeNumber(dataBean.getNoTakeCarCount());
        secondQV.setBadgeNumber(dataBean.getNoCommitCount());
        thirdQV.setBadgeNumber(dataBean.getCommitCount());
        fourthQV.setBadgeNumber(dataBean.getApproveBackCount());
        fifthQV.setBadgeNumber(dataBean.getApproveCount());
    }

    @Override
    public void showMainTitleError() {
        firstQV.setBadgeNumber(0);
        secondQV.setBadgeNumber(0);
        thirdQV.setBadgeNumber(0);
        fourthQV.setBadgeNumber(0);
        fifthQV.setBadgeNumber(0);
    }

    @Override
    public void showMainSuccess(boolean isSuccess, ArrayList<CarTakeTaskList.DataBean.CarTakeTaskListBean> carTakeTaskListBeanList) {
        llSorry.setVisibility(View.GONE);
        if (isLoadMore) {
            mTempPageCount++;
            mAdapter.setLoadMoreData(carTakeTaskListBeanList);
        } else {
            mAdapter.setNewDataNoError(carTakeTaskListBeanList);
        }
        if (carTakeTaskListBeanList.size() < PAGE_SIZE) {
            mAdapter.setLoadEndView(R.layout.load_end_layout);
        }
    }

    @Override
    public void showLogout(boolean isSuccess, String message) {
        isDoLogout = true;
        if (isSuccess) {
            startActivity(new Intent(mActivity, LoginActivity.class));
            mActivity.finish();
        }
        if (!TextUtils.isEmpty(message))
            ToastUtils.showShort(message);
    }

    @Override
    public void showGetCarTake(boolean isSuccess, String message) {
        isDoCarTakeStoreConfirm = true;
        if (isSuccess) {
            //只有未接车才能确认接车
            if (itemOnClickPosition < datas.size()) {
                Intent intent = new Intent(mActivity, DetailActivity.class);
                intent.putExtra("CarTakeTaskListBean", datas.get(itemOnClickPosition));
                intent.putExtra("Type", CURRENT_TYPE);
                startActivity(intent);
            }
            onRefresh();
        }
        if (!TextUtils.isEmpty(message))
            ToastUtils.showShort(message);
    }

    @Override
    public void openOtherUi() {
        ToastUtils.showShort("认证失败，请重新登录");
        ADApplication.mSPUtils.clear();
        startActivity(new Intent(mActivity, LoginActivity.class));
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void updateApk() {
        openPermissions();
    }


    /**
     * 显示更新dialog
     */
    private void showUpdateDialog() {
        UpdateFragment updateFragment = new UpdateFragment();
        updateFragment.show(getChildFragmentManager(), updateFragment.getClass().getSimpleName());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //接受详情提交后返回成功，然后首页刷新
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshMainEvent(RefreshMainEvent event) {
        onRefresh();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private static final int REQUEST_STORAGE_GROUP = 1100;

    @AfterPermissionGranted(REQUEST_STORAGE_GROUP)
    private void openPermissions() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            showUpdateDialog();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.request_storage_group), REQUEST_STORAGE_GROUP, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Logger.d("onPermissionsGranted");
        showUpdateDialog();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == REQUEST_STORAGE_GROUP) {
            new AppSettingsDialog.Builder(this)
                    .setRequestCode(REQUEST_STORAGE_GROUP)
                    .setTitle("权限获取失败")
                    .setRationale(R.string.setting_request_storage_group)
                    .build().show();
        }
//    }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_STORAGE_GROUP) {
            openPermissions();
        }
    }
}
