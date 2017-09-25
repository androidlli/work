package com.cango.adpickcar.login;

import com.cango.adpickcar.base.BasePresenter;
import com.cango.adpickcar.base.BaseView;

/**
 * Created by cango on 2017/9/21.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void showLoginIndicator(boolean active);

        void showLoginError();

        void showLoginSuccess(boolean isSuccess, String message);

        void openOtherUi();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void login(String userName, String password, String deviceType);
    }
}
