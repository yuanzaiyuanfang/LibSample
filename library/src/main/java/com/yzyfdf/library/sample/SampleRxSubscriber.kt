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

    public override fun _onServicesError(e: Throwable): Boolean {
        if (e is HttpException) {
            try {
                val string = e.response()?.errorBody()?.string()
                println("string = ${string}")
                return true
            } catch (e: Exception) {
                return false
            }
        } else {
            return false
        }
    }
}
