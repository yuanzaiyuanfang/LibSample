package com.yzyfdf.library.rx

import android.content.Context
import com.blankj.utilcode.util.NetworkUtils
import com.yzyfdf.library.R
import com.yzyfdf.library.base.BaseApplication
import com.yzyfdf.library.view.CustomProgressDialog
import io.reactivex.Observable
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
        }

        //统一异常处理 自己重写方法
        val servicesError = _onServicesError(e)
        if (servicesError) {
            return
        }

        //其它
        _onError(BaseApplication.appContext.getString(R.string.net_error))
    }

    protected abstract fun _onNext(t: T)

    /**
     * 返回true则自己处理异常
     */
    protected abstract fun _onServicesError(e: Throwable): Boolean

    protected abstract fun _onError(message: String)

}

/**
 *
 * @param serviceError 参照 {@link com.yzyfdf.library.rx.BaseRxSubscriberKt.getDoServiceError}
 */
fun <T> Observable<T>.subscribe2(manager: RxManager?,
                                 success: (t: T) -> Unit,
                                 error: (msg: String) -> Unit,
                                 serviceError: (e: Throwable) -> Boolean = { false }) {

    subscribe(object : BaseRxSubscriber<T>(manager) {

        override fun _onNext(t: T) {
            success(t)
        }

        override fun _onServicesError(e: Throwable): Boolean {
            return serviceError(e)
        }

        override fun _onError(message: String) {
            error(message)
        }
    })
}


var doServiceError: (e: Throwable) -> Boolean = {
    if (it is HttpException) {
        try {
            val string = it.response()?.errorBody()?.string()
            true
        } catch (e: Exception) {
            false
        }
    } else {
        false
    }
}