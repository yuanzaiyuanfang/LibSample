package com.yzyfdf.library.skin.loader;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import com.yzyfdf.library.skin.skinview.SkinCommonTabLayout;
import com.yzyfdf.library.skin.skinview.SkinTitleBar;

import skin.support.app.SkinLayoutInflater;

/**
 * Created by Administrator .
 * 描述
 */
public class SkinCustomerInflater implements SkinLayoutInflater {
    @Override
    public View createView(@NonNull Context context, String name, @NonNull AttributeSet attrs) {
        View view = null;
        switch (name) {
            case "com.wuhenzhizao.titlebar.widget.CommonTitleBar":
                view = new SkinTitleBar(context, attrs);
                break;
//            case "com.flyco.tablayout.SlidingTabLayout":
//                view = new SkinSlidingTabLayout(context, attrs);
//                break;
            case "com.flyco.tablayout.CommonTabLayout":
                view = new SkinCommonTabLayout(context, attrs);
                break;
//            case "com.flyco.tablayout.SegmentTabLayout":
//                view = new SkinSegmentTabLayout(context, attrs);
//                break;
//            case "com.flyco.tablayout.widget.MsgView":
//                view = new SkinMsgView(context, attrs);
//                break;
        }
        return view;
    }
}
