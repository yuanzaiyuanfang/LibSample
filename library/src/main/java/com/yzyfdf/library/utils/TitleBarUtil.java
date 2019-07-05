package com.yzyfdf.library.utils;

import android.content.Context;

import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author sjj , 2019/4/24 18:05
 * <p>
 * 先反射 不想改源码。。。
 */
public class TitleBarUtil {

    /**
     * 将右侧布局改成TextView
     * 颜色和字体一定要设置
     * <p>
     * TitleBarUtil.rightChange2Text(mTitlebar);
     * TextView tv = mTitlebar.getRightTextView();
     * tv.setText("改");
     * tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
     * tv.setTextSize(16);
     * <p>
     *
     * @param mTitlebar
     */
    public static void rightChange2Text(CommonTitleBar mTitlebar) {
        try {
            Class aClass = mTitlebar.getClass();

            Field rightType = aClass.getDeclaredField("rightType");
            rightType.setAccessible(true);
            rightType.set(mTitlebar, 1);

            Method initMainRightViews = aClass.getDeclaredMethod("initMainRightViews", Context.class);
            initMainRightViews.setAccessible(true);
            initMainRightViews.invoke(mTitlebar, mTitlebar.getContext());

        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void rightChange2Null(CommonTitleBar mTitlebar) {
        try {
            Class aClass = mTitlebar.getClass();

            Field rightType = aClass.getDeclaredField("rightType");
            rightType.setAccessible(true);
            rightType.set(mTitlebar, 0);

            Method initMainRightViews = aClass.getDeclaredMethod("initMainRightViews", Context.class);
            initMainRightViews.setAccessible(true);
            initMainRightViews.invoke(mTitlebar, mTitlebar.getContext());

        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
