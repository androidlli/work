package com.cango.adpickcar.resetps;

import com.cango.adpickcar.base.BasePresenter;
import com.cango.adpickcar.base.BaseView;

/**
 * Created by cango on 2017/9/21.
 */

public interface ResetPSContract {
    interface View extends BaseView<Presenter> {
        void showResetIndicator(boolean active);

        void showResetError();

        void showResetSuccess(boolean isSuccess, String message);

        void openOtherUi();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void resetPS();
    }
}
