package com.cango.adpickcar.main;

import com.cango.adpickcar.ADApplication;
import com.cango.adpickcar.api.Api;
import com.cango.adpickcar.api.MainService;
import com.cango.adpickcar.model.BaseData;
import com.cango.adpickcar.model.CarTakeTaskList;
import com.cango.adpickcar.model.ServerTime;
import com.cango.adpickcar.net.NetManager;
import com.cango.adpickcar.net.RxSubscriber;
import com.cango.adpickcar.util.CommUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by cango on 2017/9/26.
 */

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mView;
    private MainService mService;
    private Subscription subscription1, subscription2, subscription3, subscription4, subscription5,
            subscription6, subscription7;

    public MainPresenter(MainContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mService = NetManager.getInstance().create(MainService.class);
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
        if (!CommUtil.checkIsNull(subscription6))
            subscription6.unsubscribe();
        if (!CommUtil.checkIsNull(subscription7))
            subscription7.unsubscribe();
    }

    @Override
    public void loadListByStatus(boolean showRefreshLoadingUI, final String UserID, final String CustName, final String LicensePlateNO,
                                 final String CarBrandName, final String QueryType, final String PageIndex, final String PageSize) {
        if (mView.isActive()) {
            mView.showMainIndicator(showRefreshLoadingUI);
        }
        switch (Integer.parseInt(QueryType)) {
            case MainFragment.WEIJIECHE:
                subscription1 = mService.getServerTime()
                        .flatMap(new Func1<ServerTime, Observable<CarTakeTaskList>>() {
                            @Override
                            public Observable<CarTakeTaskList> call(ServerTime serverTime) {
                                boolean isSuccess = serverTime.getCode().equals("200");
                                if (isSuccess) {
                                    ADApplication.mSPUtils.put(Api.SERVERTIME, serverTime.getData().getServerTime());
                                }
                                return mService.getDatasByStatus(UserID, CustName, LicensePlateNO, CarBrandName, QueryType,
                                        PageIndex, PageSize);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RxSubscriber<CarTakeTaskList>() {
                            @Override
                            protected void _onNext(CarTakeTaskList o) {
                                if (mView.isActive()) {
                                    mView.showMainIndicator(false);
                                    boolean isSuccess = o.getCode().equals("200");
                                    if (CommUtil.handingCodeLogin(o.getCode())) {
                                        mView.openOtherUi();
                                        return;
                                    }
//                                    o.setCode("211");
                                    if ("211".equals(o.getCode())) {
                                        mView.updateApk();
                                        return;
                                    }
                                    if (isSuccess) {
                                        CarTakeTaskList.DataBean dataBean = o.getData();
                                        if (!CommUtil.checkIsNull(dataBean)) {
                                            mView.showMainTitle(dataBean);
                                            ArrayList<CarTakeTaskList.DataBean.CarTakeTaskListBean> list =
                                                    (ArrayList<CarTakeTaskList.DataBean.CarTakeTaskListBean>) dataBean.getCarTakeTaskList();
                                            if (!CommUtil.checkIsNull(list) && list.size() > 0) {
                                                mView.showMainSuccess(isSuccess, list);
                                            } else {
                                                mView.showNoData();
                                            }
                                        } else {
                                            mView.showMainTitleError();
                                            mView.showNoData();
                                        }
                                    } else {
                                        mView.showMainTitleError();
                                        mView.showMainError();
                                    }
                                }
                            }

                            @Override
                            protected void _onError() {
                                if (mView.isActive()) {
                                    mView.showMainIndicator(false);
                                    mView.showMainTitleError();
                                    mView.showMainError();
                                }
                            }
                        });
                break;
            case MainFragment.WEITIJIAO:
                subscription2 = mService.getServerTime()
                        .flatMap(new Func1<ServerTime, Observable<CarTakeTaskList>>() {
                            @Override
                            public Observable<CarTakeTaskList> call(ServerTime serverTime) {
                                boolean isSuccess = serverTime.getCode().equals("200");
                                if (isSuccess) {
                                    ADApplication.mSPUtils.put(Api.SERVERTIME, serverTime.getData().getServerTime());
                                }
                                return mService.getDatasByStatus(UserID, CustName, LicensePlateNO, CarBrandName, QueryType,
                                        PageIndex, PageSize);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RxSubscriber<CarTakeTaskList>() {
                            @Override
                            protected void _onNext(CarTakeTaskList o) {
                                if (mView.isActive()) {
                                    mView.showMainIndicator(false);
                                    boolean isSuccess = o.getCode().equals("200");
                                    if (CommUtil.handingCodeLogin(o.getCode())) {
                                        mView.openOtherUi();
                                        return;
                                    }
                                    if (isSuccess) {
                                        CarTakeTaskList.DataBean dataBean = o.getData();
                                        if (!CommUtil.checkIsNull(dataBean)) {
                                            mView.showMainTitle(dataBean);
                                            ArrayList<CarTakeTaskList.DataBean.CarTakeTaskListBean> list =
                                                    (ArrayList<CarTakeTaskList.DataBean.CarTakeTaskListBean>) dataBean.getCarTakeTaskList();
                                            if (!CommUtil.checkIsNull(list) && list.size() > 0) {
                                                mView.showMainSuccess(isSuccess, list);
                                            } else {
                                                mView.showNoData();
                                            }
                                        } else {
                                            mView.showMainTitleError();
                                            mView.showNoData();
                                        }
                                    } else {
                                        mView.showMainTitleError();
                                        mView.showMainError();
                                    }
                                }
                            }

                            @Override
                            protected void _onError() {
                                if (mView.isActive()) {
                                    mView.showMainIndicator(false);
                                    mView.showMainTitleError();
                                    mView.showMainError();
                                }
                            }
                        });
                break;
            case MainFragment.SHENHEZHON:
                subscription3 = mService.getServerTime()
                        .flatMap(new Func1<ServerTime, Observable<CarTakeTaskList>>() {
                            @Override
                            public Observable<CarTakeTaskList> call(ServerTime serverTime) {
                                boolean isSuccess = serverTime.getCode().equals("200");
                                if (isSuccess) {
                                    ADApplication.mSPUtils.put(Api.SERVERTIME, serverTime.getData().getServerTime());
                                }
                                return mService.getDatasByStatus(UserID, CustName, LicensePlateNO, CarBrandName, QueryType,
                                        PageIndex, PageSize);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RxSubscriber<CarTakeTaskList>() {
                            @Override
                            protected void _onNext(CarTakeTaskList o) {
                                if (mView.isActive()) {
                                    mView.showMainIndicator(false);
                                    boolean isSuccess = o.getCode().equals("200");
                                    if (CommUtil.handingCodeLogin(o.getCode())) {
                                        mView.openOtherUi();
                                        return;
                                    }
                                    if (isSuccess) {
                                        CarTakeTaskList.DataBean dataBean = o.getData();
                                        if (!CommUtil.checkIsNull(dataBean)) {
                                            mView.showMainTitle(dataBean);
                                            ArrayList<CarTakeTaskList.DataBean.CarTakeTaskListBean> list =
                                                    (ArrayList<CarTakeTaskList.DataBean.CarTakeTaskListBean>) dataBean.getCarTakeTaskList();
                                            if (!CommUtil.checkIsNull(list) && list.size() > 0) {
                                                mView.showMainSuccess(isSuccess, list);
                                            } else {
                                                mView.showNoData();
                                            }
                                        } else {
                                            mView.showMainTitleError();
                                            mView.showNoData();
                                        }
                                    } else {
                                        mView.showMainTitleError();
                                        mView.showMainError();
                                    }
                                }
                            }

                            @Override
                            protected void _onError() {
                                if (mView.isActive()) {
                                    mView.showMainIndicator(false);
                                    mView.showMainTitleError();
                                    mView.showMainError();
                                }
                            }
                        });
                break;
            case MainFragment.SHENHETONGUO:
                subscription4 = mService.getServerTime()
                        .flatMap(new Func1<ServerTime, Observable<CarTakeTaskList>>() {
                            @Override
                            public Observable<CarTakeTaskList> call(ServerTime serverTime) {
                                boolean isSuccess = serverTime.getCode().equals("200");
                                if (isSuccess) {
                                    ADApplication.mSPUtils.put(Api.SERVERTIME, serverTime.getData().getServerTime());
                                }
                                return mService.getDatasByStatus(UserID, CustName, LicensePlateNO, CarBrandName, QueryType,
                                        PageIndex, PageSize);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RxSubscriber<CarTakeTaskList>() {
                            @Override
                            protected void _onNext(CarTakeTaskList o) {
                                if (mView.isActive()) {
                                    mView.showMainIndicator(false);
                                    boolean isSuccess = o.getCode().equals("200");
                                    if (CommUtil.handingCodeLogin(o.getCode())) {
                                        mView.openOtherUi();
                                        return;
                                    }
                                    if (isSuccess) {
                                        CarTakeTaskList.DataBean dataBean = o.getData();
                                        if (!CommUtil.checkIsNull(dataBean)) {
                                            mView.showMainTitle(dataBean);
                                            ArrayList<CarTakeTaskList.DataBean.CarTakeTaskListBean> list =
                                                    (ArrayList<CarTakeTaskList.DataBean.CarTakeTaskListBean>) dataBean.getCarTakeTaskList();
                                            if (!CommUtil.checkIsNull(list) && list.size() > 0) {
                                                mView.showMainSuccess(isSuccess, list);
                                            } else {
                                                mView.showNoData();
                                            }
                                        } else {
                                            mView.showMainTitleError();
                                            mView.showNoData();
                                        }
                                    } else {
                                        mView.showMainTitleError();
                                        mView.showMainError();
                                    }
                                }
                            }

                            @Override
                            protected void _onError() {
                                if (mView.isActive()) {
                                    mView.showMainIndicator(false);
                                    mView.showMainTitleError();
                                    mView.showMainError();
                                }
                            }
                        });
                break;
            case MainFragment.SHENHETUIHUI:
                subscription5 = mService.getServerTime()
                        .flatMap(new Func1<ServerTime, Observable<CarTakeTaskList>>() {
                            @Override
                            public Observable<CarTakeTaskList> call(ServerTime serverTime) {
                                boolean isSuccess = serverTime.getCode().equals("200");
                                if (isSuccess) {
                                    ADApplication.mSPUtils.put(Api.SERVERTIME, serverTime.getData().getServerTime());
                                }
                                return mService.getDatasByStatus(UserID, CustName, LicensePlateNO, CarBrandName, QueryType,
                                        PageIndex, PageSize);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RxSubscriber<CarTakeTaskList>() {
                            @Override
                            protected void _onNext(CarTakeTaskList o) {
                                if (mView.isActive()) {
                                    mView.showMainIndicator(false);
                                    boolean isSuccess = o.getCode().equals("200");
                                    if (CommUtil.handingCodeLogin(o.getCode())) {
                                        mView.openOtherUi();
                                        return;
                                    }
                                    if (isSuccess) {
                                        CarTakeTaskList.DataBean dataBean = o.getData();
                                        if (!CommUtil.checkIsNull(dataBean)) {
                                            mView.showMainTitle(dataBean);
                                            ArrayList<CarTakeTaskList.DataBean.CarTakeTaskListBean> list =
                                                    (ArrayList<CarTakeTaskList.DataBean.CarTakeTaskListBean>) dataBean.getCarTakeTaskList();
                                            if (!CommUtil.checkIsNull(list) && list.size() > 0) {
                                                mView.showMainSuccess(isSuccess, list);
                                            } else {
                                                mView.showNoData();
                                            }
                                        } else {
                                            mView.showMainTitleError();
                                            mView.showNoData();
                                        }
                                    } else {
                                        mView.showMainTitleError();
                                        mView.showMainError();
                                    }
                                }
                            }

                            @Override
                            protected void _onError() {
                                if (mView.isActive()) {
                                    mView.showMainIndicator(false);
                                    mView.showMainTitleError();
                                    mView.showMainError();
                                }
                            }
                        });
                break;
        }
    }

    @Override
    public void logout(boolean showRefreshLoadingUI, final String UserID) {
        if (mView.isActive())
            mView.showLoadView(showRefreshLoadingUI);
        subscription6 = mService.getServerTime()
                .flatMap(new Func1<ServerTime, Observable<BaseData>>() {
                    @Override
                    public Observable<BaseData> call(ServerTime serverTime) {
                        boolean isSuccess = serverTime.getCode().equals("200");
                        if (isSuccess) {
                            ADApplication.mSPUtils.put(Api.SERVERTIME, serverTime.getData().getServerTime());
                        }
                        Map<String, Object> paramsMap = new HashMap<>();
                        paramsMap.put("UserID", UserID);
                        String encrypt = CommUtil.getParmasMapToJsonByEncrypt(paramsMap);
                        paramsMap = new HashMap<>();
                        paramsMap.put("RequestContent", encrypt);
                        return mService.logout(paramsMap);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<BaseData>() {
                    @Override
                    protected void _onNext(BaseData o) {
                        if (mView.isActive()) {
                            mView.showLoadView(false);
                            boolean isSuccess = o.getCode().equals("200");
                            if (CommUtil.handingCodeLogin(o.getCode())) {
                                mView.openOtherUi();
                                return;
                            }
                            ADApplication.mSPUtils.clear();
                            mView.showLogout(isSuccess, o.getMsg());
                        }
                    }

                    @Override
                    protected void _onError() {
                        if (mView.isActive()) {
                            mView.showLoadView(false);
                            mView.showLogout(false, null);
                        }
                    }
                });
    }

    @Override
    public void GetCarTakeTaskList(boolean showRefreshLoadingUI, final String UserID, final String CTTaskID,
                                   final String CTWhno, final String Vin, final String CarID) {
        if (mView.isActive())
            mView.showLoadView(showRefreshLoadingUI);
        subscription7 = mService.getServerTime()
                .flatMap(new Func1<ServerTime, Observable<BaseData>>() {
                    @Override
                    public Observable<BaseData> call(ServerTime serverTime) {
                        boolean isSuccess = serverTime.getCode().equals("200");
                        if (isSuccess) {
                            ADApplication.mSPUtils.put(Api.SERVERTIME, serverTime.getData().getServerTime());
                        }
                        Map<String, Object> paramsMap = new HashMap<>();
                        paramsMap.put("UserID", UserID);
                        paramsMap.put("CTTaskID", CTTaskID);
                        paramsMap.put("CTWhno", CTWhno);
                        paramsMap.put("Vin", Vin);
                        paramsMap.put("CarID", CarID);
                        String encrypt = CommUtil.getParmasMapToJsonByEncrypt(paramsMap);
                        paramsMap = new HashMap<>();
                        paramsMap.put("RequestContent", encrypt);
                        return mService.carTakeStoreConfirm(paramsMap);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<BaseData>() {
                    @Override
                    protected void _onNext(BaseData o) {
                        if (mView.isActive()) {
                            mView.showLoadView(false);
                            boolean isSuccess = o.getCode().equals("200");
                            if (CommUtil.handingCodeLogin(o.getCode())) {
                                mView.openOtherUi();
                                return;
                            }
                            mView.showGetCarTake(isSuccess, o.getMsg());
                        }
                    }

                    @Override
                    protected void _onError() {
                        if (mView.isActive()) {
                            mView.showLoadView(false);
                            mView.showGetCarTake(false, null);
                        }
                    }
                });
    }
}
