package com.yzyfdf.libsample.ui

import android.content.Intent
import android.os.Build
import android.view.WindowManager

import com.blankj.utilcode.util.BarUtils
import com.yzyfdf.library.base.BaseActivity
import com.yzyfdf.library.base.BaseModel
import com.yzyfdf.library.base.BasePresenter
import com.yzyfdf.libsample.R

class SpalshActivity : BaseActivity<BasePresenter<*, *>, BaseModel>() {

    override val layoutId: Int
        get() = R.layout.activity_spalsh

    override fun initPresenter() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val lp = window.attributes
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = lp
        }
        BarUtils.setNavBarVisibility(this, false)
    }

    override fun initView() {
        init()
        window.decorView.postDelayed({
            startActivity(Intent(mContext, MainActivity::class.java))
            finish()
        }, 1000)
    }

    /**
     * 初始化模块
     */
    private fun init() {
        //分享
    }

    override fun onBackPressed() {

    }
}
