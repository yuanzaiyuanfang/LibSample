package com.yzyfdf.library.base

import android.content.Context
import com.yzyfdf.library.rx.RxManager

import es.dmoral.toasty.Toasty

abstract class BasePresenter<T, E> {
    var mContext: Context? = null
    var mModel: E? = null
    var mView: T? = null
    var mRxManager = RxManager()

    fun setVM(v: T, m: E) {
        this.mView = v
        this.mModel = m
        this.onStart()
    }

    open fun onStart() {}

    fun onDestroy() {
        mRxManager.clear()
    }

    protected fun showErrorTip(msg: String) {
        Toasty.error(mContext!!, msg).show()
    }
}
