package com.yzyfdf.library.rx

import com.blankj.utilcode.util.LogUtils
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object RxHelper {

    private val gson = Gson()
    private val sTag = "返回值"

    fun <T> logAndThread(upstream: Observable<T>): ObservableSource<T> {
        return upstream.map { t ->
            LogUtils.dTag(sTag, gson.toJson(t))
            t
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    }

    fun <T> logOnly(upstream: Observable<T>): ObservableSource<T> {
        return upstream.map { t ->
            LogUtils.dTag(sTag, gson.toJson(t))
            t
        }
    }

    fun <T> threadOnly(upstream: Observable<T>): ObservableSource<T> {
        return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }


    fun <T> logAndThread(upstream: Single<T>): SingleSource<T> {
        return upstream.map { t ->
            LogUtils.dTag(sTag, gson.toJson(t))
            t
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun <T> logOnly(upstream: Single<T>): SingleSource<T> {
        return upstream.map { t ->
            LogUtils.dTag(sTag, gson.toJson(t))
            t
        }
    }

    fun <T> threadOnly(upstream: Single<T>): SingleSource<T> {
        return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}
