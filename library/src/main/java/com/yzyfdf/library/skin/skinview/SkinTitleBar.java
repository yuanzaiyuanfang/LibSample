package com.yzyfdf.library.skin.skinview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.yzyfdf.library.R;

import skin.support.content.res.SkinCompatResources;
import skin.support.widget.SkinCompatHelper;
import skin.support.widget.SkinCompatSupportable;

import static skin.support.widget.SkinCompatHelper.INVALID_ID;

/**
 * Created by Administrator .
 * 描述
 */
public class SkinTitleBar extends CommonTitleBar implements SkinCompatSupportable {
    private int mStatusBarColor;
    private int mTitleBarColor;

    public SkinTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CommonTitleBar);
        mStatusBarColor = array.getResourceId(R.styleable.CommonTitleBar_statusBarColor, INVALID_ID);
        mTitleBarColor = array.getResourceId(R.styleable.CommonTitleBar_titleBarColor, INVALID_ID);

        array.recycle();
        setBkg();
    }

    @Override
    public void applySkin() {
        setBkg();
    }

    private void setBkg() {
        mStatusBarColor = SkinCompatHelper.checkResourceId(mStatusBarColor);
        if (mStatusBarColor != INVALID_ID) {
            setStatusBarColor(SkinCompatResources.getColor(getContext(), mStatusBarColor));
        }
        mTitleBarColor = SkinCompatHelper.checkResourceId(mTitleBarColor);
        if (mTitleBarColor != INVALID_ID) {
            setBackgroundColor(SkinCompatResources.getColor(getContext(), mTitleBarColor));
        }
    }
}

