package com.yzyfdf.library.base

import android.app.Activity
import java.util.*
import kotlin.system.exitProcess

/**
 * activity管理
 */
class AppManager private constructor(var activityStack: Stack<Activity>) {

    companion object {
        val instance: AppManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            AppManager(Stack())
        }
    }


    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity) {
        activityStack = Stack()
        activityStack.add(activity)
    }

    /**
     * 删除已经finish的activity
     *
     * @param activity
     */
    fun removeActivity(activity: Activity?) {
        if (activity != null) {
            activityStack.remove(activity)
        }
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    fun currentActivity(): Activity? {
        return try {
            activityStack.lastElement()
        } catch (e: Exception) {
            null
        }

    }

    /**
     * 获取当前Activity的前一个Activity
     */
    fun preActivity(): Activity? {
        val index = activityStack.size - 2
        return if (index < 0) {
            null
        } else activityStack[index]
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    fun finishActivity() {
        val activity = activityStack.lastElement()
        finishActivity(activity)
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity?) {
        if (activity != null) {
            activityStack.remove(activity)
            activity.finish()
        }
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(cls: Class<Activity>?) {
        if (cls == null) {
            return
        }
        try {
            for (activity in activityStack) {
                if (activity.javaClass == cls) {
                    finishActivity(activity)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 结束其他所有Activity
     */
    fun finishOtherActivitys(cls: Class<out Activity>?) {
        if (cls == null) {
            return
        }
        activityStack.forEach {
            if (null != it && it.javaClass != cls) {
                it.finish()
            }
        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        activityStack.forEach {
            it.finish()
        }
        activityStack.clear()
    }

    /**
     * 返回到指定的activity
     *
     * @param cls
     */
    fun returnToActivity(cls: Class<Activity>) {
        while (activityStack.size != 0) {
            val activity = activityStack.peek()
            if (activity.javaClass == cls) {
                break
            } else {
                finishActivity(activity)
            }
        }
    }


    /**
     * 是否已经打开指定的activity
     *
     * @param cls
     * @return
     */
    fun isOpenActivity(cls: Class<Activity>): Boolean {
        return activityStack.any { cls == it.javaClass }
    }

    /**
     * 退出应用程序
     *
     * @param isBackground 是否开开启后台运行
     */
    fun AppExit(isBackground: Boolean) {
        try {
            finishAllActivity()
            // 注意，如果您有后台程序运行，请不要支持此句子
            if (!isBackground) {
                exitProcess(0)
            }
        } catch (e: Exception) {

        } finally {

        }
    }
}