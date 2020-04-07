package com.yzyfdf.library.sample


import com.yzyfdf.library.base.BaseModel
import com.yzyfdf.library.base.BasePresenter
import com.yzyfdf.library.base.BaseView
import com.yzyfdf.library.rx.BaseRespose

import io.reactivex.Observable

interface SampleContract {

    interface Model : BaseModel {
        operator fun get(s: String): Observable<BaseRespose<*>>
    }

    interface View : BaseView {
        fun returnS(bean: BaseRespose<*>)
    }

    abstract class Presenter : BasePresenter<View, Model>() {
        abstract operator fun get(s: String)
    }
}
