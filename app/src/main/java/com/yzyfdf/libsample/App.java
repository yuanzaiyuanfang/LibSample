package com.yzyfdf.libsample;

import android.support.v7.app.AppCompatDelegate;

import com.yzyfdf.library.base.BaseApplication;
import com.yzyfdf.library.skin.loader.CustomSDCardLoader;
import com.yzyfdf.library.skin.loader.SkinCustomerInflater;
import com.yzyfdf.library.skin.loader.ZipSDCardLoader;

import skin.support.SkinCompatManager;
import skin.support.app.SkinCardViewInflater;
import skin.support.constraint.app.SkinConstraintViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;

public class App extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        initMultipleTheme();
    }

    private void initMultipleTheme() {
        SkinCompatManager.withoutActivity(this)
                .addStrategy(new CustomSDCardLoader())          // 自定义加载策略，指定SDCard路径
                .addStrategy(new ZipSDCardLoader())             // 自定义加载策略，获取zip包中的资源
                .addInflater(new SkinMaterialViewInflater())    // material design
                .addInflater(new SkinConstraintViewInflater())  // ConstraintLayout
                .addInflater(new SkinCardViewInflater())        // CardView v7
                .addHookInflater(new SkinCustomerInflater())
                .setSkinStatusBarColorEnable(true)              // 关闭状态栏换肤
                //                .setSkinWindowBackgroundEnable(false)           // 关闭windowBackground换肤
                //                .setSkinAllActivityEnable(false)                // true: 默认所有的Activity都换肤; false: 只有实现SkinCompatSupportable接口的Activity换肤
                .loadSkin();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);    }
}
