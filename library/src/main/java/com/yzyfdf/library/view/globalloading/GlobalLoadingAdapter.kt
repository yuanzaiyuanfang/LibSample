package com.yzyfdf.library.view.globalloading

import android.view.View


/**
 * @author sjj , 2019/4/25 13:35
 * 全局加载
 */
class GlobalLoadingAdapter : Gloading.Adapter {

    override fun getView(holder: Gloading.Holder, convertView: View?, status: Int, icon: Int,
                         str: Int): View? {

        var loadingStatusView: GlobalLoadingStatusView? = null
        //convertView为可重用的布局
        //Holder中缓存了各状态下对应的View
        //	如果status对应的View为null，则convertView为上一个状态的View
        //	如果上一个状态的View也为null，则convertView为null
        if (convertView != null && convertView is GlobalLoadingStatusView) {
            loadingStatusView = convertView
        }
        if (loadingStatusView == null) {
            loadingStatusView = GlobalLoadingStatusView(holder.context)
            loadingStatusView.setRetryTask(holder.retryTask)
        }
        loadingStatusView.setStatus(status, icon, str)
        return loadingStatusView
    }
}
