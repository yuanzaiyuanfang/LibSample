package com.yzyfdf.library.base;

import android.content.Context;

import com.yzyfdf.library.rx.RxManager;

import es.dmoral.toasty.Toasty;

public abstract class BasePresenter<T,E>{
    public Context mContext;
    public E mModel;
    public T mView;
    public RxManager mRxManager = new RxManager();

    public void setVM(T v, E m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }
    public void onStart(){
    }
    public void onDestroy() {
        mRxManager.clear();
    }

    protected void showErrorTip(String msg) {
        Toasty.error(mContext, msg).show();
    }
}
