package com.yzyfdf.library.rx

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class RxManager {

    private val mCompositeSubscription = CompositeDisposable()

    /**
     * RxBus注入监听
     */
    inline fun <reified T : Any> on(crossinline subscribe: (t: T) -> Unit) {
        RxBus.observe<T>()
            .subscribe {
                subscribe(it)
            }
            .registerInBus(this)
    }

    fun on(disposable: Disposable) {
        mCompositeSubscription.add(disposable)
    }

    /**
     * 单个presenter生命周期结束，取消订阅和所有rxbus观察
     */
    fun clear() {
        RxBus.unRegister(this)
        mCompositeSubscription.clear()
    }

    //发送rxbus
    fun post(event: Any) {
        RxBus.send(event)
    }

}