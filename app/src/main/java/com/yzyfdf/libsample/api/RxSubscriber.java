package com.yzyfdf.libsample.api;

import android.content.Context;

import com.yzyfdf.library.rx.BaseRxSubscriber;
import com.yzyfdf.library.rx.RxManager;

import retrofit2.HttpException;

/**
 * @author sjj , 2019/4/25 17:21
 * 自定义重写业务错误处理
 */
public abstract class RxSubscriber<T> extends BaseRxSubscriber<T> {

    public RxSubscriber(Context context, RxManager rxManager) {
        super(context, rxManager);
    }

    public RxSubscriber(Context context, RxManager rxManager, boolean showDialog) {
        super(context, rxManager, showDialog);
    }

    public RxSubscriber(Context context, RxManager rxManager, boolean showDialog, String msg) {
        super(context, rxManager, showDialog, msg);
    }

    @Override
    protected void servicesError(HttpException e) {
        try {
            String string = e.response().errorBody().string();
//            ErrorBean errorBean = GsonUtil.getInstance().fromJson(string, new TypeToken<ErrorBean>() {
//            }.getType());
//            String message = errorBean.getMessage();
//            _onError(message);

//            ToastUtils.showShort(message);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        switch (e.code()) {
            case 401:
//                IntentGo.getIt().goLoginActivity(mContext);
                break;
        }
    }
}
