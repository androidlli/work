package com.cango.adpickcar.login;

/**
 * Created by cango on 2017/9/21.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mView;

    public LoginPresenter(LoginContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void login(String userName, String password, String deviceType) {

    }
}
