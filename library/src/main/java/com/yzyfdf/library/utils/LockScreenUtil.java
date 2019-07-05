package com.yzyfdf.library.utils;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.view.WindowManager;

/**
 * @author sjj , 2019/5/30 16:58
 * 锁屏相关
 */
public class LockScreenUtil {

    /**
     * 自定义锁屏activity显示在原始锁屏界面之上
     */
    public static void showWhenLocked(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            activity.setShowWhenLocked(true);
//            activity.setTurnScreenOn(true);//亮屏
            KeyguardManager keyguardManager = (KeyguardManager) activity.getSystemService(Context.KEYGUARD_SERVICE);
            if (keyguardManager != null) {
                keyguardManager.requestDismissKeyguard(activity, null);
            }
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        }
    }
}
