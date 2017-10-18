package com.cango.adpickcar.main;

import com.cango.adpickcar.base.BasePresenter;
import com.cango.adpickcar.base.BaseView;
import com.cango.adpickcar.model.CarTakeTaskList;

import java.util.ArrayList;

/**
 * Created by cango on 2017/9/26.
 */

public interface MainContract {
    interface View extends BaseView<Presenter> {
        void showLoadView(boolean isShow);

        void showMainIndicator(boolean active);

        void showMainError();

        void showNoData();

        void showMainTitle(CarTakeTaskList.DataBean dataBean);

        void showMainTitleError();

        void showMainSuccess(boolean isSuccess, ArrayList<CarTakeTaskList.DataBean.CarTakeTaskListBean> carTakeTaskListBeanList);

        void showLogout(boolean isSuccess, String message);

        void showGetCarTake(boolean isSuccess, String message);

        void openOtherUi();

        boolean isActive();

        void updateApk();
    }

    interface Presenter extends BasePresenter {
        /**
         * UserID											用户ID									string
         * CustName											客户名称									string
         * LicensePlateNO											客户车牌号									string
         * CarBrandName											客户车辆品牌									string
         * QueryType											查询类型（1：未接车 2.未提交 3：审核中 4：审批通过 5.审批退回）									string
         * PageIndex											页码									string
         * PageSize											页大小									string
         */
        void loadListByStatus(boolean showRefreshLoadingUI, String UserID, String CustName, String LicensePlateNO,
                              String CarBrandName, String QueryType, String PageIndex, String PageSize);

        void logout(boolean showRefreshLoadingUI, String UserID);

        /**
         * UserID											用户ID									string
         * CTTaskID											接车任务ID									string
         * CTWhno											接车库点编号									string
         * Vin											车架号									string
         * CarID											车辆ID									string
         */
        void GetCarTakeTaskList(boolean showRefreshLoadingUI, String UserID, String CTTaskID, String CTWhno, String Vin, String CarID);
    }
}
