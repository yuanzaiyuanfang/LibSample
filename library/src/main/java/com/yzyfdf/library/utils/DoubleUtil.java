package com.yzyfdf.library.utils;

import com.blankj.utilcode.util.ToastUtils;

/**
 * @author sjj , 2019/5/8 16:06
 * 防止点击过快
 */
public class DoubleUtil {

    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(800);
    }

    public static boolean isFastDoubleClick(int millis) {
        return isFastDoubleClick(millis, "点击过快");
    }

    public static boolean isFastDoubleClick(int millis, String msg) {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < millis) {
            ToastUtils.showShort(msg);
            return true;
        } else {
            lastClickTime = time;
            return false;
        }
    }
}
