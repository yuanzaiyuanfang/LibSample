package com.yzyfdf.library.activity;

import com.yzyfdf.library.rx.BaseRespose;
import com.yzyfdf.library.rx.BaseRxSubscriber;

import retrofit2.HttpException;

/**
 * @author MLRC-iOS-CI
 * @date 2018/5/8
 */
public class SamplePresenter extends SampleContract.Presenter {

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void get(String s) {
        mModel.get(s).subscribe(new BaseRxSubscriber<BaseRespose>(mContext, mRxManager) {
            @Override
            protected void servicesError(HttpException e) {

            }

            @Override
            protected void _onNext(BaseRespose baseRespose) {
                mView.returnS(baseRespose);
            }

            @Override
            protected void _onError(String message) {
                mView.returnFail(message);
            }
        });
    }
}
