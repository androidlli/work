package com.cango.adpickcar.main;

import com.cango.adpickcar.base.BasePresenter;
import com.cango.adpickcar.base.BaseView;

/**
 * Created by cango on 2017/9/26.
 */

public interface MainContract {
    interface View extends BaseView<Presenter> {
        void showMainIndicator(boolean active);

        void showMainError();

        void showNoData();

        void showMainSuccess(boolean isSuccess, String message);

        void openOtherUi();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void loadListByStatus(int pageCount,int pageSize);
    }
}
