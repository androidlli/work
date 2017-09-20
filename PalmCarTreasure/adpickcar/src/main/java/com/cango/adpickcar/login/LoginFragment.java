package com.cango.adpickcar.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cango.adpickcar.R;
import com.cango.adpickcar.base.BaseFragment;
import com.cango.adpickcar.main.MainActivity;

import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment {
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
}
