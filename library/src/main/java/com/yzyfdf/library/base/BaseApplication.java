package com.yzyfdf.library.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import androidx.multidex.MultiDex;

import com.billy.android.loading.Gloading;
import com.blankj.utilcode.util.Utils;
import com.yzyfdf.library.view.GlobalLoadingAdapter;

/**
 * APPLICATION
 */
public class BaseApplication extends Application {

    private static BaseApplication baseApplication;
    private static int             statusBarHeight;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        Utils.init(this);
        Gloading.initDefault(new GlobalLoadingAdapter());
    }

    public static Context getAppContext() {
        return baseApplication;
    }

    public static Resources getAppResources() {
        return baseApplication.getResources();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 分包
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
