package com.yzyfdf.libsample.adapter;

import android.content.Context;

import com.yzyfdf.library.base.BaseRvHolder;
import com.yzyfdf.library.quick.QuickRvAdapter;
import com.yzyfdf.library.rx.RxManager;
import com.yzyfdf.libsample.R;

/**
 * @author sjj , 2019/4/25 14:19
 */
public class InfoAdapter extends QuickRvAdapter<String> {

    private RxManager mRxManager;

    public InfoAdapter(Context context, RxManager rxManager) {
        super(context,R.layout.layout_setting_view);
        mRxManager = rxManager;
    }

    @Override
    public void onBindVH(BaseRvHolder holder, int position) {
        String str = mList.get(position);
        holder.setText(R.id.tv_lefttext, str);
    }

}
