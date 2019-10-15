package com.yzyfdf.libsample.ui;

import android.content.Intent;
import android.os.Build;
import android.view.WindowManager;

import com.blankj.utilcode.util.BarUtils;
import com.yzyfdf.library.base.BaseActivity;
import com.yzyfdf.libsample.R;

public class SpalshActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_spalsh;
    }

    @Override
    public void initPresenter() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);
        }
        BarUtils.setNavBarVisibility(this, false);
    }

    @Override
    public void initView() {
        init();
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(mContext, MainActivity.class));
                finish();
            }
        }, 1000);
    }

    /**
     * 初始化模块
     */
    private void init() {
        //分享
    }

    @Override
    public void onBackPressed() {

    }
}
