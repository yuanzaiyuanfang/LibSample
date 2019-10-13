package com.yzyfdf.libsample.ui;

import android.os.Bundle;
import android.view.View;

import com.yzyfdf.library.base.BaseFragment;
import com.yzyfdf.libsample.R;

import butterknife.OnClick;
import skin.support.SkinCompatManager;

/**
 * Created by Administrator .
 * 描述
 */
public class ThemeFragment extends BaseFragment {

    private SkinCompatManager.SkinLoaderListener listener = new SkinCompatManager.SkinLoaderListener() {
        @Override
        public void onStart() {
            System.out.println("ThemeFragment.onStart");
        }

        @Override
        public void onSuccess() {
            System.out.println("ThemeFragment.onSuccess");
        }

        @Override
        public void onFailed(String errMsg) {
            System.out.println("errMsg = [" + errMsg + "]");
        }
    };

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_theme;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

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
