package com.yzyfdf.library.rx

import android.util.Log
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.ReplaySubject

object RxBus {
    private val TAG = javaClass.simpleName

    /**
     * 用于保存RxBus事件的所有订阅，并在需要时正确的取消订阅。
     */
    private val disposablesMap: HashMap<Any, CompositeDisposable?> = HashMap()


    /**
     * 避免直接使用此属性，因为它仅在内联函数中使用而暴露
     */
    val rxBus = ReplaySubject.create<Any>()
        .toSerialized()


    /**
     * 向RxBus发送一个事件，这个事件可以来自任意一个线程
     */
    fun send(event: Any) {
        rxBus.onNext(event)
    }

    /**
     * 订阅特定类型T的事件。可以从任何线程调用
     */
    inline fun <reified T : Any> observe(): Observable<T> {
        return rxBus.ofType(T::class.java)
    }

    /**
     * 从RxBus事件中取消注册订阅者
     * 调用订阅的取消订阅方法
     */
    fun unRegister(tag: Any) {
        val compositeDisposable = disposablesMap[tag]
        if (compositeDisposable == null) {
            Log.w(TAG, "Trying to unregister subscriber that wasn't registered")
        } else {
            compositeDisposable.clear()
            disposablesMap.remove(tag)
        }
    }

    /**
     * internal修饰符 只有在相同模块中才能使用
     */
    internal fun register(tag: Any, disposable: Disposable) {
        var compositeDisposable = disposablesMap[tag]
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable.add(disposable)
        disposablesMap[tag] = compositeDisposable
    }
}

/**
 * 注册订阅以便以后正确注销它以避免内存泄漏
 * @param tag 拥有此订阅的订阅对象
 */
fun Disposable.registerInBus(tag: Any, disposable: Disposable = this) {
    RxBus.register(tag, disposable)
}