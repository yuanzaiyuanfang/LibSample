package com.yzyfdf.library.quick

import android.content.Context
import android.view.ViewGroup

import androidx.annotation.LayoutRes

import com.yzyfdf.library.base.BaseRvAdapter
import com.yzyfdf.library.base.BaseRvHolder

/**
 * Created by Administrator .
 * 描述
 */
abstract class QuickRvAdapter<E>(context: Context, @LayoutRes
private val layout: Int) : BaseRvAdapter<BaseRvHolder, E>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRvHolder {
        return BaseRvHolder(mInflater.inflate(layout, parent, false))
    }
}
