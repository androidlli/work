package com.cango.adpickcar.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

import com.cango.adpickcar.R;
import com.cango.adpickcar.base.BaseFragment;
import com.cango.adpickcar.baseAdapter.BaseHolder;
import com.cango.adpickcar.baseAdapter.OnBaseItemClickListener;
import com.cango.adpickcar.resetps.ResetPSActivity;
import com.cango.adpickcar.util.BarUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by cango on 2017/9/19.
 */

public class MainFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
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
    @BindView(R.id.srl_main)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerview_main)
    RecyclerView mRecyclerView;
    @BindView(R.id.fl_shadow)
    FrameLayout flShadow;

    @OnClick({R.id.ll_modify_ps, R.id.ll_main_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_modify_ps:
                startActivity(new Intent(mActivity, ResetPSActivity.class));
                break;
            case R.id.ll_main_search:
                showPopSearch();
                break;
        }
    }

    private MainActivity mActivity;

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

        initRecyclerView();
    }

    private void initRecyclerView() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent, R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add(i + "");
        }
        MainTestAdapter adapter = new MainTestAdapter(mActivity, datas, false);
        adapter.setOnItemClickListener(new OnBaseItemClickListener<String>() {
            @Override
            public void onItemClick(BaseHolder viewHolder, String data, int position) {

            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        mActivity = (MainActivity) getActivity();
    }

    @Override
    public void onRefresh() {

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
}
