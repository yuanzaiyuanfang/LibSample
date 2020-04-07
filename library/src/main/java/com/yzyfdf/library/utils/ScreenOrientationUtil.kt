package com.yzyfdf.library.utils

import android.app.Activity
import android.content.pm.ActivityInfo
import android.util.Log
import android.view.OrientationEventListener

/**
 * @author sjj , 2019/5/24 9:32
 * 屏幕方向
 */
class ScreenOrientationUtil {

    private var mOrientation: Int = 0
    private var mOrEventListener: OrientationEventListener? = null

    private var mOrientation1: Int = 0
    private var mOrEventListener1: OrientationEventListener? = null

    private var mActivity: Activity? = null

    val isPortrait: Boolean
        get() = mOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT || mOrientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT

    fun start(activity: Activity) {
        this.mActivity = activity
        if (mOrEventListener == null) {
            initListener()
        }
        mOrEventListener!!.enable()
    }

    fun stop() {
        if (mOrEventListener != null) {
            mOrEventListener!!.disable()
        }
        if (mOrEventListener1 != null) {
            mOrEventListener1!!.disable()
        }
    }

    private fun initListener() {
        mOrEventListener = object : OrientationEventListener(mActivity) {
            override fun onOrientationChanged(rotation: Int) {
                Log.e("test", "" + rotation)
                if (rotation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                    return
                }
                val orientation = convert2Orientation(rotation)
                // 方向没有变化,跳过
                if (orientation == mOrientation) {
                    return
                }
                mOrientation = orientation
                //                mActivity.setRequestedOrientation(mOrientation);

            }
        }

        mOrEventListener1 = object : OrientationEventListener(mActivity) {
            override fun onOrientationChanged(rotation: Int) {
                if (rotation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                    return
                }
                val orientation = convert2Orientation(rotation)
                // 方向没有变化,跳过
                if (orientation == mOrientation1) {
                    return
                }
                mOrientation1 = orientation
                if (mOrientation1 == mOrientation) {
                    mOrEventListener1!!.disable()
                    mOrEventListener!!.enable()
                }
            }
        }
    }

    fun toggleScreen() {
        mOrEventListener!!.disable()
        mOrEventListener1!!.enable()
        var orientation = 0
        if (mOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
        } else if (mOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else if (mOrientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
            orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else if (mOrientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
            orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
        }
        mOrientation = orientation
        mActivity!!.requestedOrientation = mOrientation
    }

    private fun convert2Orientation(rotation: Int): Int {
        var orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        if (rotation >= 0 && rotation <= 45 || rotation > 315) {
            orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else if (rotation > 45 && rotation <= 135) {
            orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
        } else if (rotation > 135 && rotation <= 225) {
            orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
        } else if (rotation > 225 && rotation <= 315) {
            orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        return orientation
    }

    companion object {

        val instance = ScreenOrientationUtil()
    }
}
