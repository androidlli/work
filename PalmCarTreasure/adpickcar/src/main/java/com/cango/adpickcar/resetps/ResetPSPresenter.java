package com.cango.adpickcar.resetps;

/**
 * Created by cango on 2017/9/21.
 */

public class ResetPSPresenter implements ResetPSContract.Presenter {
    private ResetPSContract.View mView;
    public ResetPSPresenter(ResetPSContract.View view) {
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
    public void resetPS() {

    }
}
