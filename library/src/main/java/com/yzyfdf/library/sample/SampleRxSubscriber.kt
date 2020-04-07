package com.yzyfdf.library.sample

import android.content.Context
import com.yzyfdf.library.rx.BaseRxSubscriber
import com.yzyfdf.library.rx.RxManager

import retrofit2.HttpException

/**
 * @author sjj , 2019/4/25 17:21
 * 自定义重写业务错误处理
 */
abstract class SampleRxSubscriber<T> : BaseRxSubscriber<T> {

    constructor(rxManager: RxManager) : super(rxManager)

    constructor(rxManager: RxManager, context: Context) : this(rxManager, context, "")

    constructor(rxManager: RxManager, context: Context,
                msg: String) : super(rxManager, context, msg)

    public override fun servicesError(e: HttpException) {
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
