package com.cango.adpickcar.detail;

import com.cango.adpickcar.base.BasePresenter;
import com.cango.adpickcar.base.BaseView;

/**
 * Created by cango on 2017/9/27.
 */

public interface DetailContract {
    interface View extends BaseView<Presenter> {
        void showIndicator(boolean active);

        void showError();

        void showNoData();

        void showSuccess(boolean isSuccess, String message);

        void openOtherUi();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {
    }
}
