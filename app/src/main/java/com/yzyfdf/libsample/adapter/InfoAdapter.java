package com.yzyfdf.libsample.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.yzyfdf.library.base.BaseRvAdapter;
import com.yzyfdf.library.base.BaseRvHolder;
import com.yzyfdf.library.rx.RxManager;
import com.yzyfdf.libsample.R;

/**
 * @author sjj , 2019/4/25 14:19
 */
public class InfoAdapter extends BaseRvAdapter<BaseRvHolder, String> {


    private RxManager mRxManager;

    public InfoAdapter(Context context) {
        super(context);
    }

    public InfoAdapter(Context context, RxManager rxManager) {
        super(context);
        mRxManager = rxManager;
    }


    @NonNull
    @Override
    public BaseRvHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BaseRvHolder(mInflater.inflate(R.layout.setting_item, viewGroup, false));
    }

    @Override
    public void onBindVH(BaseRvHolder holder, int position) {
        holder.setText(R.id.tv_lefttext, mList.get(position));
    }

}
