package com.cango.adpickcar.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cango.adpickcar.R;
import com.cango.adpickcar.base.BaseFragment;
import com.cango.adpickcar.main.MainActivity;
import com.cango.adpickcar.util.CommUtil;

import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment implements LoginContract.View {
    public static LoginFragment getInstance() {
        LoginFragment loginFragment = new LoginFragment();
        Bundle bundle = new Bundle();
        loginFragment.setArguments(bundle);
        return loginFragment;
    }

    @OnClick({R.id.btn_login_signin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_signin:
                startActivity(new Intent(mActivity, MainActivity.class));
                break;
        }
    }

    private LoginActivity mActivity;
    private LoginContract.Presenter mPresenter;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!CommUtil.checkIsNull(mPresenter)) {
            mPresenter.onDetach();
        }
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mActivity = (LoginActivity) getActivity();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoginIndicator(boolean active) {

    }

    @Override
    public void showLoginError() {

    }

    @Override
    public void showLoginSuccess(boolean isSuccess, String message) {

    }

    @Override
    public void openOtherUi() {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
