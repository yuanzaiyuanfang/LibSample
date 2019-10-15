package com.yzyfdf.libsample.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

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
        return new BaseRvHolder(mInflater.inflate(R.layout.layout_setting_view, viewGroup, false));
    }

    @Override
    public void onBindVH(BaseRvHolder holder, int position) {
        String str = mList.get(position);
        holder.setText(R.id.tv_lefttext, str);
        holder.itemView.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(str, position);
            }
        });
    }

}
