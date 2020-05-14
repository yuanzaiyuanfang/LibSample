package com.yzyfdf.library.rx

import com.yzyfdf.library.callback.Callable
import io.reactivex.Observable


/**
 * Created by Administrator .
 * 描述
 */
object RxUtils {

    fun <R> createObservable(callable: Callable<R>): Observable<R> {
        return Observable.create<R> {
            try {
                val result = callable.call()
                it.onNext(result)
            } catch (e: Exception) {
                it.onError(e)
            }

            it.onComplete()
        }
    }

}

