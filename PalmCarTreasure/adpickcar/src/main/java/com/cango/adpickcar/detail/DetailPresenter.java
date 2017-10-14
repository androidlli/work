package com.cango.adpickcar.detail;

import com.cango.adpickcar.ADApplication;
import com.cango.adpickcar.api.Api;
import com.cango.adpickcar.api.DetailService;
import com.cango.adpickcar.model.BaseData;
import com.cango.adpickcar.model.BaseInfo;
import com.cango.adpickcar.model.CarFilesInfo;
import com.cango.adpickcar.model.CarInfo;
import com.cango.adpickcar.model.ServerTime;
import com.cango.adpickcar.net.NetManager;
import com.cango.adpickcar.net.RxSubscriber;
import com.cango.adpickcar.util.CommUtil;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by cango on 2017/9/27.
 */

public class DetailPresenter implements DetailContract.Presenter {
    private DetailContract.View mView;
    private DetailService mService;
    private Subscription subscription1, subscription2, subscription3, subscription4, subscription5;

    public DetailPresenter(DetailContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mService = NetManager.getInstance().create(DetailService.class);
    }

    @Override
    public void start() {

    }

    @Override
    public void onDetach() {
        if (!CommUtil.checkIsNull(subscription1))
            subscription1.unsubscribe();
        if (!CommUtil.checkIsNull(subscription2))
            subscription2.unsubscribe();
        if (!CommUtil.checkIsNull(subscription3))
            subscription3.unsubscribe();
        if (!CommUtil.checkIsNull(subscription4))
            subscription4.unsubscribe();
        if (!CommUtil.checkIsNull(subscription5))
            subscription5.unsubscribe();
    }

    @Override
    public void GetCarTakeStoreBaseInfo(boolean showRefreshLoadingUI, final String CTSID,
                                        final String DisCarID) {
        if (mView.isActive()) {
            mView.showIndicator(showRefreshLoadingUI);
        }
        subscription1 = mService.getServerTime()
                .flatMap(new Func1<ServerTime, Observable<BaseInfo>>() {
                    @Override
                    public Observable<BaseInfo> call(ServerTime serverTime) {
                        boolean isSuccess = serverTime.getCode().equals("200");
                        if (isSuccess) {
                            ADApplication.mSPUtils.put(Api.SERVERTIME, serverTime.getData().getServerTime());
                        }
                        return mService.GetCarTakeStoreBaseInfo(CTSID, DisCarID);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<BaseInfo>() {
                    @Override
                    protected void _onNext(BaseInfo o) {
                        if (mView.isActive()) {
                            mView.showIndicator(false);
                            boolean isSuccess = o.getCode().equals("200");
                            if (isSuccess) {
                                mView.showCarTakeStoreBaseInfo(o);
                                mView.showItemInfo(o);
                            } else {
                                mView.showBaseInfoNoData();
                                mView.showItemInfoNoData();
                            }
                        }
                    }

                    @Override
                    protected void _onError() {
                        if (mView.isActive()) {
                            mView.showIndicator(false);
                            mView.showBaseInfoError();
                            mView.showItemInfoError();
                        }
                    }
                });
    }

    @Override
    public void GetCarTakeStoreCarInfo(final boolean showRefreshLoadingUI,
                                       final String DisCarID) {
        if (mView.isActive()) {
            mView.showIndicator(showRefreshLoadingUI);
        }
        subscription2 = mService.getServerTime()
                .flatMap(new Func1<ServerTime, Observable<CarInfo>>() {
                    @Override
                    public Observable<CarInfo> call(ServerTime serverTime) {
                        boolean isSuccess = serverTime.getCode().equals("200");
                        if (isSuccess) {
                            ADApplication.mSPUtils.put(Api.SERVERTIME, serverTime.getData().getServerTime());
                        }
                        return mService.GetCarTakeStoreCarInfo(DisCarID);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<CarInfo>() {
                    @Override
                    protected void _onNext(CarInfo o) {
                        if (mView.isActive()) {
                            mView.showIndicator(false);
                            boolean isSuccess = o.getCode().equals("200");
                            if (isSuccess) {
                                mView.showCarInfo(o);
                            } else {
                                mView.showCarInfoNoData();
                            }
                        }
                    }

                    @Override
                    protected void _onError() {
                        if (mView.isActive()) {
                            mView.showIndicator(false);
                            mView.showCarInfoError();
                        }
                    }
                });
    }

    @Override
    public void saveCarBasicItemInfo(boolean showRefreshLoadingUI, final BaseInfo baseInfo) {
        if (mView.isActive()) {
            mView.showIndicator(showRefreshLoadingUI);
        }
        subscription3 = mService.getServerTime()
                .flatMap(new Func1<ServerTime, Observable<BaseData>>() {
                    @Override
                    public Observable<BaseData> call(ServerTime serverTime) {
                        boolean isSuccess = serverTime.getCode().equals("200");
                        if (isSuccess) {
                            ADApplication.mSPUtils.put(Api.SERVERTIME, serverTime.getData().getServerTime());
                        }
                        Map<String, Object> paramsMap;
                        String encrypt = CommUtil.setParamsToJsonByEncrypt(baseInfo);
                        paramsMap = new HashMap<>();
                        paramsMap.put("RequestContent", encrypt);
                        return mService.saveCarBasicItemInfo(paramsMap);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<BaseData>() {
                    @Override
                    protected void _onNext(BaseData o) {
                        if (mView.isActive()) {
                            mView.showIndicator(false);
                            boolean isSuccess = o.getCode().equals("200");
                            mView.showSaveBasicItem(isSuccess, o.getMsg());
                        }
                    }

                    @Override
                    protected void _onError() {
                        if (mView.isActive()) {
                            mView.showIndicator(false);
                            //如果网络异常呢，那么将重置
                            mView.showSaveBasicItem(false, null);
                        }
                    }
                });
    }

    @Override
    public void saveCarInfo(boolean showRefreshLoadingUI, final String UserID,
                            final String LicensePlateNo, final String IsErpMapping, final String DisCarID) {
        if (mView.isActive()) {
            mView.showIndicator(showRefreshLoadingUI);
        }
        subscription4 = mService.getServerTime()
                .flatMap(new Func1<ServerTime, Observable<BaseData>>() {
                    @Override
                    public Observable<BaseData> call(ServerTime serverTime) {
                        boolean isSuccess = serverTime.getCode().equals("200");
                        if (isSuccess) {
                            ADApplication.mSPUtils.put(Api.SERVERTIME, serverTime.getData().getServerTime());
                        }
                        Map<String, Object> paramsMap = new HashMap<>();
                        paramsMap.put("UserID", UserID);
                        paramsMap.put("LicensePlateNo", LicensePlateNo);
                        paramsMap.put("IsErpMapping", IsErpMapping);
                        paramsMap.put("DisCarID", DisCarID);
                        String encrypt = CommUtil.getParmasMapToJsonByEncrypt(paramsMap);
                        paramsMap = new HashMap<>();
                        paramsMap.put("RequestContent", encrypt);
                        return mService.saveCarInfo(paramsMap);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<BaseData>() {
                    @Override
                    protected void _onNext(BaseData o) {
                        if (mView.isActive()) {
                            mView.showIndicator(false);
                            boolean isSuccess = o.getCode().equals("200");
                            mView.showSaveCarInfo(isSuccess, o.getMsg());
                        }
                    }

                    @Override
                    protected void _onError() {
                        if (mView.isActive()) {
                            mView.showIndicator(false);
                            //如果网络异常呢，那么将重置
                            mView.showSaveCarInfo(false, null);
                        }
                    }
                });
    }

    @Override
    public void getCarFilesInfo(boolean showRefreshLoadingUI, final String DisCarID) {
        if (mView.isActive()) {
            mView.showIndicator(showRefreshLoadingUI);
        }
        subscription5 = mService.getServerTime()
                .flatMap(new Func1<ServerTime, Observable<CarFilesInfo>>() {
                    @Override
                    public Observable<CarFilesInfo> call(ServerTime serverTime) {
                        boolean isSuccess = serverTime.getCode().equals("200");
                        if (isSuccess) {
                            ADApplication.mSPUtils.put(Api.SERVERTIME, serverTime.getData().getServerTime());
                        }
                        return mService.getCarFilesInfo(DisCarID);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<CarFilesInfo>() {
                    @Override
                    protected void _onNext(CarFilesInfo o) {
                        if (mView.isActive()) {
                            mView.showIndicator(false);
                            boolean isSuccess = o.getCode().equals("200");
                            if (isSuccess) {
                                if (o.getData() != null)
                                    mView.showCarFilesInfo(o);
                                else
                                    mView.showCarFilesInfoNoData();
                            } else {
                                mView.showCarFilesInfoNoData();
                            }
                        }
                    }

                    @Override
                    protected void _onError() {
                        if (mView.isActive()) {
                            mView.showIndicator(false);
                            mView.showCarFilesInfoError();
                        }
                    }
                });
    }
}
