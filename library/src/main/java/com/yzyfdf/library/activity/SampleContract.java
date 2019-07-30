package com.yzyfdf.library.activity;


import com.yzyfdf.library.base.BaseModel;
import com.yzyfdf.library.base.BasePresenter;
import com.yzyfdf.library.base.BaseView;
import com.yzyfdf.library.rx.BaseRespose;

import io.reactivex.Observable;

public interface SampleContract {

    interface Model extends BaseModel {
        Observable<BaseRespose> get(String s);
    }

    interface View extends BaseView {
        void returnS(BaseRespose bean);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void get(String s);
    }
}
