package com.yzyfdf.library.utils

import android.content.Context
import androidx.fragment.app.Fragment
import com.yzyfdf.library.base.BaseApplication
import es.dmoral.toasty.Toasty


/**
 * 短暂显示Toast提示(来自String)
 */
fun Context.showToast(text: String) {
    Toasty.normal(BaseApplication.appContext, text)
        .show()
}

/**
 * 短暂显示Toast提示(id)
 */
fun Context.showToast(resId: Int) {
    Toasty.normal(BaseApplication.appContext, resId)
        .show()
}

/**
 * 长提示
 */
fun Context.showLongToast(text: String) {
    Toasty.normal(BaseApplication.appContext, text, Toasty.LENGTH_LONG)
        .show()
}

/**
 * 错误提示
 */
fun Context.showErrTip(msg: String) {
    Toasty.error(BaseApplication.appContext, msg)
        .show()
}

/**
 * 操作成功提示
 */
fun Context.showSuccessTip(msg: String) {
    Toasty.success(BaseApplication.appContext, msg)
        .show()
}

//===============Fragment

/**
 * 短暂显示Toast提示(来自String)
 */
fun Fragment.showToast(text: String) {
    Toasty.normal(BaseApplication.appContext, text)
        .show()
}

/**
 * 短暂显示Toast提示(id)
 */
fun Fragment.showToast(resId: Int) {
    Toasty.normal(BaseApplication.appContext, resId)
        .show()
}

/**
 * 长提示
 */
fun Fragment.showLongToast(text: String) {
    Toasty.normal(BaseApplication.appContext, text, Toasty.LENGTH_LONG)
        .show()
}

/**
 * 错误提示
 */
fun Fragment.showErrTip(msg: String) {
    Toasty.error(BaseApplication.appContext, msg)
        .show()
}

/**
 * 操作成功提示
 */
fun Fragment.showSuccessTip(msg: String) {
    Toasty.success(BaseApplication.appContext, msg)
        .show()
}
