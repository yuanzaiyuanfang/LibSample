package com.yzyfdf.library.view.globalloading;

import android.view.View;


/**
 * @author sjj , 2019/4/25 13:35
 * 全局加载
 */
public class GlobalLoadingAdapter implements Gloading.Adapter {
    @Override
    public View getView(Gloading.Holder holder, View convertView, int status, int icon, int str) {
        GlobalLoadingStatusView loadingStatusView = null;
        //convertView为可重用的布局
        //Holder中缓存了各状态下对应的View
        //	如果status对应的View为null，则convertView为上一个状态的View
        //	如果上一个状态的View也为null，则convertView为null
        if (convertView != null && convertView instanceof GlobalLoadingStatusView) {
            loadingStatusView = (GlobalLoadingStatusView) convertView;
        }
        if (loadingStatusView == null) {
            loadingStatusView = new GlobalLoadingStatusView(holder.getContext());
            loadingStatusView.setRetryTask(holder.getRetryTask());
        }
        loadingStatusView.setStatus(status, icon, str);
        return loadingStatusView;
    }
}
