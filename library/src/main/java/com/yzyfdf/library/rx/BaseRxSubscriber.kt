package com.yzyfdf.library.rx

import android.content.Context

import com.blankj.utilcode.util.NetworkUtils
import com.yzyfdf.library.R
import com.yzyfdf.library.base.BaseApplication
import com.yzyfdf.library.view.CustomProgressDialog

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException


abstract class BaseRxSubscriber<T>
/**
 * 显示进度框 文字
 */
@JvmOverloads constructor(private val mRxManager: RxManager?, private val mContext: Context? = null,
                          private val msg: String = "") : Observer<T> {

    private var dialog: CustomProgressDialog? = null

    override fun onSubscribe(d: Disposable) {
        mContext?.let {
            if (dialog == null) {
                dialog = CustomProgressDialog(it, msg)
            }
            dialog!!.show()
        }
        mRxManager?.on(d)
    }

    override fun onNext(t: T) {
        _onNext(t)
    }


    override fun onComplete() {
        dialog?.apply {
            dismiss()
        }
    }

    override fun onError(e: Throwable) {
        dialog?.apply {
            dismiss()
        }

        //        e.printStackTrace()
        //网络
        if (!NetworkUtils.isConnected()) {
            _onError(BaseApplication.appContext.getString(R.string.load_no_net))
        } else if (e is HttpException) {
            //统一异常处理 自己重写方法
            servicesError(e)
        } else {
            _onError(BaseApplication.appContext.getString(R.string.net_error))
        } //其它
        //服务器
    }

    protected abstract fun servicesError(e: HttpException)

    protected abstract fun _onNext(t: T)

    protected abstract fun _onError(message: String)

}