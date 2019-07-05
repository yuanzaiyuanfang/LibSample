package com.yzyfdf.library.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.yzyfdf.library.R;
import com.yzyfdf.library.base.BaseActivity;

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
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        init();
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
//                IntentGo.getIt().goHomeActivity(SpalshActivity.this);
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
