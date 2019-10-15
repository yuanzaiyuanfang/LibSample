package com.yzyfdf.library.rx;

import android.content.Context;

import retrofit2.HttpException;

/**
 * @author sjj , 2019/4/25 17:21
 * 自定义重写业务错误处理
 */
public abstract class RxSubscriber<T> extends BaseRxSubscriber<T> {

    public RxSubscriber(RxManager rxManager) {
        super(rxManager);
    }

    public RxSubscriber(Context context, RxManager rxManager, boolean showDialog) {
        super(context, rxManager, showDialog);
    }

    public RxSubscriber(Context context, RxManager rxManager, boolean showDialog, String msg) {
        super(context, rxManager, showDialog, msg);
    }

    @Override
    public void servicesError(HttpException e) {
//        try {
//            String string = e.response().errorBody().string();
//            BaseErrorBean errorBean = GsonUtils.fromJson(string, new TypeToken<BaseErrorBean>() {
//            }.getType());
//
//            int code = errorBean.getCode();
//            String message = errorBean.getMessage();
//            _onError(message);
//            LogUtils.e(code, message);
//        } catch (Exception ignored) {
//        }
//
//        switch (e.code()) {
//            case 401:
//                IntentGo.getInstance().goLoginActivity();
//                break;
//        }
    }
}
