package com.yzyfdf.library.base

import android.app.Application
import android.content.Context
import android.content.res.Resources
import androidx.multidex.MultiDex
import com.blankj.utilcode.util.Utils
import com.yzyfdf.library.view.globalloading.Gloading
import com.yzyfdf.library.view.globalloading.GlobalLoadingAdapter

/**
 * APPLICATION
 */
open class BaseApplication : Application() {

    companion object {

        private lateinit var baseApplication: BaseApplication

        val appContext: Context
            get() = baseApplication

        val appResources: Resources
            get() = baseApplication.resources
    }


    override fun onCreate() {
        super.onCreate()
        baseApplication = this
        Utils.init(this)
        Gloading.initDefault(GlobalLoadingAdapter())
        Gloading.debug(true)
    }

    override fun onTerminate() {
        super.onTerminate()
    }

    /**
     * 分包
     *
     * @param base
     */
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}
