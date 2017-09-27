package com.cango.adpickcar.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cango.adpickcar.R;
import com.cango.adpickcar.base.BaseFragment;
import com.cango.adpickcar.baseAdapter.BaseHolder;
import com.cango.adpickcar.baseAdapter.OnBaseItemClickListener;
import com.cango.adpickcar.baseAdapter.OnLoadMoreListener;
import com.cango.adpickcar.detail.DetailActivity;
import com.cango.adpickcar.resetps.ResetPSActivity;
import com.cango.adpickcar.util.BarUtil;
import com.cango.adpickcar.util.CommUtil;
import com.cango.adpickcar.util.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MainFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener ,MainContract.View{
    public static final String CURRENT_TYPE = "current_type";

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

    @OnClick({R.id.ll_modify_ps, R.id.ll_main_search, R.id.rl_main_first, R.id.rl_main_second, R.id.rl_main_third,
            R.id.rl_main_fourth, R.id.rl_main_fifth})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_modify_ps:
                startActivity(new Intent(mActivity, ResetPSActivity.class));
                break;
            case R.id.ll_main_search:
                showPopSearch();
                break;
            case R.id.rl_main_first:
                selectTitleStatus(0);
                break;
            case R.id.rl_main_second:
                selectTitleStatus(1);
                break;
            case R.id.rl_main_third:
                selectTitleStatus(2);
                break;
            case R.id.rl_main_fourth:
                selectTitleStatus(3);
                break;
            case R.id.rl_main_fifth:
                selectTitleStatus(4);
                break;
        }
    }

    private MainActivity mActivity;
    private MainContract.Presenter mPresenter;
    private MainAdapter mAdapter;
    private ArrayList<String> datas;
    private Badge firstQV, secondQV, thirdQV, fourthQV, fifthQV;
    private int selectColor, noSelectColor;
    private int mPageCount = 1, mTempPageCount = 2;
    static int PAGE_SIZE = 10;
    private boolean isLoadMore;

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

        initNum();
        selectTitleStatus(0);
        initRecyclerView();
    }

    private void initNum() {
        firstQV = new QBadgeView(mActivity).bindTarget(tvFirstNum).setBadgeNumber(1).setShowShadow(false).setBadgeBackgroundColor(Color.TRANSPARENT);
        secondQV = new QBadgeView(mActivity).bindTarget(tvSecondNum).setBadgeNumber(2).setShowShadow(false).setBadgeBackgroundColor(Color.TRANSPARENT);
        thirdQV = new QBadgeView(mActivity).bindTarget(tvThirdNum).setBadgeNumber(3).setShowShadow(false).setBadgeBackgroundColor(Color.TRANSPARENT);
        fourthQV = new QBadgeView(mActivity).bindTarget(tvFourthNum).setBadgeNumber(4).setBadgeTextColor(Color.WHITE).setShowShadow(false);
        fifthQV = new QBadgeView(mActivity).bindTarget(tvFifthNum).setBadgeNumber(5).setShowShadow(false).setBadgeBackgroundColor(Color.TRANSPARENT);
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
        for (int i = 0; i < 20; i++) {
            datas.add(i + "");
        }
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
                mPresenter.loadListByStatus(mPageCount,PAGE_SIZE);
            }
        });
        mAdapter.setOnItemClickListener(new OnBaseItemClickListener<String>() {
            @Override
            public void onItemClick(BaseHolder viewHolder, String data, int position) {
                new AlertDialog.Builder(mActivity)
                        .setTitle("确认接车")
                        .setMessage("申请编号\r\n" + "客户姓名\r\n" + "车牌号码\r\n" + "颜色\r\n")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(mActivity, DetailActivity.class));
                            }
                        })
                        .create().show();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mAdapter);
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
        mAdapter.notifyDataSetChanged();
        mAdapter.setLoadingView(R.layout.load_loading_layout);
        mPresenter.loadListByStatus(mPageCount,PAGE_SIZE);
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
        View popupView = LayoutInflater.from(context).inflate(layoutId, null);
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
    public void showMainSuccess(boolean isSuccess, String message) {
        llSorry.setVisibility(View.GONE);
        ArrayList<String> newArrays = new ArrayList<>();
        newArrays.add(message);
        if (isLoadMore) {
            mTempPageCount++;
            mAdapter.setLoadMoreData(newArrays);
        } else {
//            mAdapter.setNewData(tasks);
            mAdapter.setNewDataNoError(newArrays);
        }
        if (newArrays.size() < PAGE_SIZE) {
            mAdapter.setLoadEndView(R.layout.load_end_layout);
        }
    }

    @Override
    public void openOtherUi() {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
