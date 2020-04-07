package com.yzyfdf.library.utils

import com.yzyfdf.library.base.BaseApplication

import es.dmoral.toasty.Toasty

/**
 * @author sjj , 2019/5/8 16:06
 * 防止点击过快
 */
object DoubleUtil {

    private var lastClickTime: Long = 0

    fun isFastDoubleClick(millis: Int = 800, msg: String = "点击过快"): Boolean {
        val time = System.currentTimeMillis()
        return if (time - lastClickTime < millis) {
            Toasty.warning(BaseApplication.appContext, msg)
                .show()
            true
        } else {
            lastClickTime = time
            false
        }
    }
}
