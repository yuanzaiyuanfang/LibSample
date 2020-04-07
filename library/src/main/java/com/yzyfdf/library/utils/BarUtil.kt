package com.yzyfdf.library.utils

import android.annotation.TargetApi
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.drawerlayout.widget.DrawerLayout
import com.blankj.utilcode.util.BarUtils

/**
 * @author sjj , 2019/4/23 16:32
 * 状态栏设置
 */
class BarUtil {

    companion object {
        val instance: BarUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            BarUtil()
        }
    }

    val DEFAULT_STATUS_BAR_ALPHA = 112

    /**
     * 如果需要内容紧贴着StatusBar
     * 应该在对应的xml布局文件中，设置根布局fitsSystemWindows=true。
     */
    private var contentViewGroup: View? = null

    //白色可以替换成其他浅色系
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun setWhiteStatus(activity: Activity) {
        if (MIUISetStatusBarLightMode(activity.window, true)) { //MIUI
            activity.window.statusBarColor = Color.WHITE
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else if (FlymeSetStatusBarLightMode(activity.window, true)) { //Flyme
            activity.window.statusBarColor = Color.WHITE
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //6.0
            activity.window.statusBarColor = Color.WHITE
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.statusBarColor = calculateStatusColor(Color.WHITE,
                                                                  DEFAULT_STATUS_BAR_ALPHA)
        }
    }


    /**
     * 计算状态栏颜色
     *
     * @param color color值
     * @param alpha alpha值
     * @return 最终的状态栏颜色
     */
    private fun calculateStatusColor(@ColorInt color: Int, alpha: Int): Int {
        if (alpha == 0) {
            return color
        }
        val a = 1 - alpha / 255f
        var red = color shr 16 and 0xff
        var green = color shr 8 and 0xff
        var blue = color and 0xff
        red = (red * a + 0.5).toInt()
        green = (green * a + 0.5).toInt()
        blue = (blue * a + 0.5).toInt()
        return 0xff shl 24 or (red shl 16) or (green shl 8) or blue
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private fun MIUISetStatusBarLightMode(window: Window?, dark: Boolean): Boolean {
        var result = false
        if (window != null) {
            val clazz = window.javaClass
            try {
                var darkModeFlag = 0
                val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                darkModeFlag = field.getInt(layoutParams)
                val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType,
                                                     Int::class.javaPrimitiveType)
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag) //状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag) //清除黑色字体
                }
                result = true
            } catch (e: Exception) {

            }

        }
        return result
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private fun FlymeSetStatusBarLightMode(window: Window?, dark: Boolean): Boolean {
        var result = false
        if (window != null) {
            try {
                val lp = window.attributes
                val darkFlag = WindowManager.LayoutParams::class.java.getDeclaredField(
                        "MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                val meizuFlags = WindowManager.LayoutParams::class.java.getDeclaredField(
                        "meizuFlags")
                darkFlag.isAccessible = true
                meizuFlags.isAccessible = true
                val bit = darkFlag.getInt(null)
                var value = meizuFlags.getInt(lp)
                if (dark) {
                    value = value or bit
                } else {
                    value = value and bit.inv()
                }
                meizuFlags.setInt(lp, value)
                window.attributes = lp
                result = true
            } catch (e: Exception) {

            }

        }
        return result
    }


    /**
     * 9.0以上 进入刘海区域
     */
    fun inStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val window = activity.window
            val lp = window.attributes
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = lp
        }
    }


    /**
     * 全透状态栏
     */
    fun setStatusBarFullTransparent(activity: Activity) {
        val window = activity.window
        if (Build.VERSION.SDK_INT >= 21) { //21表示5.0
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= 19) { //19表示4.4
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 半透明状态栏
     */
    fun setHalfTransparent(activity: Activity) {

        val window = activity.window
        if (Build.VERSION.SDK_INT >= 21) { //21表示5.0
            val decorView = window.decorView
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        } else if (Build.VERSION.SDK_INT >= 19) { //19表示4.4
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //虚拟键盘也透明
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    fun setFitSystemWindow(activity: Activity, fitSystemWindow: Boolean) {
        if (contentViewGroup == null) {
            contentViewGroup = (activity.findViewById<View>(
                    android.R.id.content) as ViewGroup).getChildAt(0)
        }
        contentViewGroup!!.fitsSystemWindows = fitSystemWindow
    }

    /**
     * 为了兼容4.4的抽屉布局->透明状态栏
     */
    fun setDrawerLayoutFitSystemWindow(activity: Activity) {
        if (Build.VERSION.SDK_INT == 19) { //19表示4.4
            val statusBarHeight = BarUtils.getStatusBarHeight()
            if (contentViewGroup == null) {
                contentViewGroup = (activity.findViewById<View>(
                        android.R.id.content) as ViewGroup).getChildAt(0)
            }
            if (contentViewGroup is DrawerLayout) {
                val drawerLayout = contentViewGroup as DrawerLayout?
                drawerLayout!!.clipToPadding = true
                drawerLayout.fitsSystemWindows = false
                for (i in 0 until drawerLayout.childCount) {
                    val child = drawerLayout.getChildAt(i)
                    child.fitsSystemWindows = false
                    child.setPadding(0, statusBarHeight, 0, 0)
                }

            }
        }
    }
}
