package com.yzyfdf.library.base


interface BaseView {
    /*******内嵌加载 */
    //    void showLoading(String title);
    //    void stopLoading();
    //    void showErrorTip(String msg);
    fun returnFail(message: String)
}