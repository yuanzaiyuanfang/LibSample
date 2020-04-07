package com.yzyfdf.libsample.adapter

import android.content.Context
import com.yzyfdf.library.base.BaseRvHolder
import com.yzyfdf.library.quick.QuickRvAdapter
import com.yzyfdf.libsample.R

/**
 * @author sjj , 2019/4/25 14:19
 */
class InfoAdapter(context: Context) :
    QuickRvAdapter<String>(context, R.layout.layout_setting_view) {

    override fun onBindViewHolder(holder: BaseRvHolder, position: Int, itemBean: String) {

        holder.setText(R.id.tv_lefttext, itemBean)

    }
}
