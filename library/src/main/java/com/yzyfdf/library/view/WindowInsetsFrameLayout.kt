package com.yzyfdf.library.view

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.WindowInsets
import android.widget.FrameLayout

/**
 * Created by Administrator .
 * 描述
 */
class WindowInsetsFrameLayout @TargetApi(Build.VERSION_CODES.LOLLIPOP)
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {

    init {
        //当层次结构发生改变时回调
        setOnHierarchyChangeListener(object : OnHierarchyChangeListener {
            override fun onChildViewAdded(parent: View, child: View) {
                requestApplyInsets()
            }

            override fun onChildViewRemoved(parent: View, child: View) {

            }
        })
    }

    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun onApplyWindowInsets(insets: WindowInsets): WindowInsets {
        for (index in 0 until childCount)
        //对子View进行遍历，逐个分发状态栏空间的适配事件
            getChildAt(index).dispatchApplyWindowInsets(insets)
        return insets
    }
}