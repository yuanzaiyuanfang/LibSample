package com.yzyfdf.libsample.ui;

import android.view.View;

import com.yzyfdf.library.base.BaseActivity;
import com.yzyfdf.libsample.R;

import butterknife.OnClick;
import skin.support.SkinCompatManager;

/**
 * Created by Administrator .
 * 描述
 */
public class ThemeActivity extends BaseActivity {

    private SkinCompatManager.SkinLoaderListener listener = new SkinCompatManager.SkinLoaderListener() {
        @Override
        public void onStart() {
            System.out.println("ThemeActivity.onStart");
        }

        @Override
        public void onSuccess() {
            System.out.println("ThemeActivity.onSuccess");
        }

        @Override
        public void onFailed(String errMsg) {
            System.out.println("errMsg = [" + errMsg + "]");
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_theme;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.change, R.id.moren})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.change:
                SkinCompatManager.getInstance().loadSkin("night",
                        listener,
                        SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN); // 后缀加载

//                SkinCompatUserThemeManager.get().addColorState(R.color.colorPrimary,"#ff111111");
                break;
            case R.id.moren:
                SkinCompatManager.getInstance().restoreDefaultTheme();

//                SkinCompatUserThemeManager.get().clearColors();
                break;
        }
    }
}
