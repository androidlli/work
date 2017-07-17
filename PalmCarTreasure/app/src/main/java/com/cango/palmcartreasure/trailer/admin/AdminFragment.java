package com.cango.palmcartreasure.trailer.admin;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cango.palmcartreasure.MtApplication;
import com.cango.palmcartreasure.R;
import com.cango.palmcartreasure.api.Api;
import com.cango.palmcartreasure.base.BaseFragment;
import com.cango.palmcartreasure.login.LoginActivity;
import com.cango.palmcartreasure.model.WareHouse;
import com.cango.palmcartreasure.trailer.download.DownloadActivity;
import com.cango.palmcartreasure.util.AppUtils;
import com.cango.palmcartreasure.util.SizeUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *管理员首页view，展示
 */
public class AdminFragment extends BaseFragment {

    private AdminActivity mActivity;
    @BindView(R.id.toolbar_manager)
    Toolbar mToolbar;
    @BindView(R.id.tv_exit)
    ImageView tvExit;
    @BindView(R.id.tv_popupParent)
    TextView tvPopupParent;
    @BindView(R.id.rl_shadow)
    RelativeLayout rlShadow;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick({R.id.tv_exit,R.id.iv_employee_group,R.id.iv_task_allocation,R.id.iv_task_query})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_employee_group:
                Intent staiffIntent=new Intent(getActivity(),StaiffActivity.class);
                staiffIntent.putExtra(StaiffFragment.TYPE,StaiffFragment.SHOW_GROUP);
                mActivity.mSwipeBackHelper.forward(staiffIntent);
                break;
            case R.id.iv_task_allocation:
                Intent taskIntent = new Intent(getActivity(),AdminTasksActivity.class);
                taskIntent.putExtra(AdminTasksFragment.TYPE,AdminTasksFragment.ADMIN_UNABSORBED);
                mActivity.mSwipeBackHelper.forward(taskIntent);
                break;
            case R.id.iv_task_query:
                Intent groupTaskIntent = new Intent(getActivity(),AdminTasksActivity.class);
                groupTaskIntent.putExtra(AdminTasksFragment.TYPE,AdminTasksFragment.GROUP);
                mActivity.mSwipeBackHelper.forward(groupTaskIntent);
                break;
            case R.id.tv_exit:
//                MtApplication.mSPUtils.clear();
//                MtApplication.mSPUtils.put(Api.ISSHOWSTARTOVER,true);
//                Intent loginIntent = new Intent(mActivity, LoginActivity.class);
//                loginIntent.putExtra("isFromLogout", true);
//                mActivity.mSwipeBackHelper.forward(loginIntent);
                rlShadow.setVisibility(View.VISIBLE);
                mMenuPW.update();
//                mMenuPW.showAtLocation(mToolbar,Gravity.RIGHT|Gravity.BOTTOM,SizeUtil.dp2px(mActivity,15),0);
                mMenuPW.showAsDropDown(tvPopupParent);
                break;
        }
    }

    private PopupWindow mMenuPW;

    public static AdminFragment newInstance() {
        AdminFragment fragment = new AdminFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_admin;
    }

    @Override
    protected void initView() {
        mActivity= (AdminActivity) getActivity();
        mActivity.setSupportActionBar(mToolbar);
        mMenuPW = getPopupWindow(mActivity, R.layout.admin_menu_pw);
    }

    @Override
    protected void initData() {
    }

    public PopupWindow getPopupWindow(Context context, final int layoutId) {
        final PopupWindow popupWindow = new PopupWindow(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View popupView = LayoutInflater.from(context).inflate(layoutId, null);
        TextView tvDown = (TextView) popupView.findViewById(R.id.tv_admin_down);
        TextView tvExit = (TextView) popupView.findViewById(R.id.tv_admin_exit);
        tvDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.mSwipeBackHelper.forward(DownloadActivity.class);
            }
        });
        tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MtApplication.mSPUtils.clear();
                MtApplication.mSPUtils.put(Api.ISSHOWSTARTOVER,true);
                Intent loginIntent = new Intent(mActivity, LoginActivity.class);
                loginIntent.putExtra("isFromLogout", true);
                mActivity.mSwipeBackHelper.forward(loginIntent);
            }
        });
        popupWindow.setContentView(popupView);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                rlShadow.setVisibility(View.GONE);
            }
        });

        popupWindow.update();
        return popupWindow;
    }

}
