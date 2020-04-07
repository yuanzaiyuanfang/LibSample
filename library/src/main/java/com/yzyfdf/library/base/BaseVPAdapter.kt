package com.yzyfdf.library.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.viewpager.widget.PagerAdapter

abstract class BaseVPAdapter<T>(private var mContext: Context, private var mList: List<T>?)
    : PagerAdapter() {

    protected val mInflater: LayoutInflater = LayoutInflater.from(mContext)
    protected lateinit var mOnItemClickListener: (position: Int) -> Unit

    override fun getCount(): Int {
        return if (mList == null) {
            0
        } else mList!!.size
    }

    override fun isViewFromObject(view: View, any: Any): Boolean {
        return view === any
    }

    /**
     * PhotoView view = (PhotoView) mInflater.inflate(R.layout.item_big_pic, container, false);
     * Glide.with(mContext).load(mList.get(position)).into(view);
     * container.addView(view);
     *
     * if (this::mOnItemClickListener.isInitialized) {
     *     view.setOnClickListener {
     *         mOnItemClickListener(position)
     *     }
     * }
     * return view;
     */
    abstract override fun instantiateItem(container: ViewGroup, position: Int): Any

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        container.removeView(any as View)
    }

}

