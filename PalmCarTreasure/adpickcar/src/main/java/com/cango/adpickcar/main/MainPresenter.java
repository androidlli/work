package com.cango.adpickcar.main;

/**
 * Created by cango on 2017/9/26.
 */

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mView;
    public MainPresenter(MainContract.View view) {
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
    public void loadListByStatus(int pageCount, int pageSize) {

    }
}
