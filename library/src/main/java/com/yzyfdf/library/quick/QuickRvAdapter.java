package com.yzyfdf.library.quick;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.yzyfdf.library.base.BaseRvAdapter;
import com.yzyfdf.library.base.BaseRvHolder;

/**
 * Created by Administrator .
 * 描述
 */
public abstract class QuickRvAdapter<E> extends BaseRvAdapter<BaseRvHolder, E> {

    private @LayoutRes
    int layout;

    public QuickRvAdapter(Context context, int layout) {
        super(context);
        this.layout = layout;
    }

    @NonNull
    @Override
    public BaseRvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseRvHolder(mInflater.inflate(layout, parent, false));
    }
}
