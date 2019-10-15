package com.yzyfdf.library.skin.skinview;

import android.content.Context;
import android.util.AttributeSet;

import com.yzyfdf.library.view.globalloading.GlobalLoadingStatusView;

import skin.support.widget.SkinCompatBackgroundHelper;
import skin.support.widget.SkinCompatSupportable;

/**
 * Created by Administrator .
 * 描述
 */
public class SkinGlobalLoadingStatusView extends GlobalLoadingStatusView
        implements SkinCompatSupportable {

    private final SkinCompatBackgroundHelper mBackgroundTintHelper;

    public SkinGlobalLoadingStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBackgroundTintHelper = new SkinCompatBackgroundHelper(this);
        mBackgroundTintHelper.loadFromAttributes(null, 0);
    }

    @Override
    public void setBackgroundResource(int resId) {
        super.setBackgroundResource(resId);
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.onSetBackgroundResource(resId);
        }
    }

    @Override
    public void applySkin() {
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.applySkin();
        }
    }
}
