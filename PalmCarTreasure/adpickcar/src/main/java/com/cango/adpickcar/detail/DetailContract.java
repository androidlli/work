package com.cango.adpickcar.detail;

import com.cango.adpickcar.base.BasePresenter;
import com.cango.adpickcar.base.BaseView;
import com.cango.adpickcar.model.BaseInfo;
import com.cango.adpickcar.model.CarFilesInfo;
import com.cango.adpickcar.model.CarInfo;

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

        void showCarTakeStoreBaseInfo(BaseInfo baseInfo);

        void showBaseInfoError();

        void showBaseInfoNoData();

        void showItemInfo(BaseInfo baseInfo);

        void showItemInfoError();

        void showItemInfoNoData();

        void showCarInfo(CarInfo carInfo);

        void showCarInfoNoData();

        void showCarInfoError();

        void showSaveBasicItem(boolean isSuccess, String message);

        void showSaveCarInfo(boolean isSuccess, String message);

        void showCarFilesInfo(CarFilesInfo carFilesInfo);

        void showCarFilesInfoError();

        void showCarFilesInfoNoData();

    }

    interface Presenter extends BasePresenter {
        void GetCarTakeStoreBaseInfo(boolean showRefreshLoadingUI, String CTSID, String DisCarID);

        void GetCarTakeStoreCarInfo(boolean showRefreshLoadingUI, String DisCarID);

        //保存
        void saveCarBasicItemInfo(boolean showRefreshLoadingUI, BaseInfo baseInfo);

        /**
         * UserID											用户ID									string
         * LicensePlateNo											车牌号码									string
         * IsErpMapping											与ERP车型是否相符（0-不相符/1-相符）									string
         * DisCarID											处置车辆ID									string
         */
        void saveCarInfo(boolean showRefreshLoadingUI, String UserID, String LicensePlateNo, String IsErpMapping, String DisCarID);

        void getCarFilesInfo(boolean showRefreshLoadingUI, String DisCarID);
    }
}
