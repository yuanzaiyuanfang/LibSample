package com.yzyfdf.library.sample

import com.yzyfdf.library.rx.BaseRespose


class SamplePresenter : SampleContract.Presenter() {

    override fun onStart() {
        super.onStart()
    }

    override fun get(s: String) {
        mModel?.get(s)
            ?.subscribe(object : SampleRxSubscriber<BaseRespose<*>>(mRxManager) {

                override fun _onNext(baseRespose: BaseRespose<*>) {
                    mView?.returnS(baseRespose)
                }

                override fun _onError(message: String) {
                    showErrorTip(message)
                }
            })
    }
}
