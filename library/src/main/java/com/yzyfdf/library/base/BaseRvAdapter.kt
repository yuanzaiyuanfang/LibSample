package com.yzyfdf.library.base

import android.animation.Animator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.math.min


abstract class BaseRvAdapter<VH : RecyclerView.ViewHolder, T>
@JvmOverloads constructor(var mContext: Context, var mList: MutableList<T> = ArrayList())
    : RecyclerView.Adapter<VH>() {

    var mInflater: LayoutInflater = LayoutInflater.from(mContext)
    lateinit var mOnItemClickListener: (adapter: BaseRvAdapter<VH, T>, view: View, position: Int, item: T) -> Unit
    lateinit var mOnNoItemCallback: () -> Unit

    private var showEnterAnim: Boolean = false
    private var animator: RvAnimator? = null

    private var mLastPosition = -1

    val list: List<T>
        get() = mList

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        //第一次显示动画
        if (showEnterAnim && position > mLastPosition) {
            getAnim(holder)?.forEach {
                it.start()
            }
            mLastPosition = position
        }

        //onBindViewHolder
        onBindViewHolder(holder, position, mList[position])

        if (this::mOnItemClickListener.isInitialized) {
            holder.itemView.setOnClickListener { view ->
                mOnItemClickListener(this, holder.itemView, position, mList[position])
            }
        }

    }

    /**
     * onBindViewHolder
     */
    abstract fun onBindViewHolder(holder: VH, position: Int, itemBean: T)

    /**
     * 是否显示条目进入动画
     */
    fun setShowEnterAnim(showEnterAnim: Boolean, animator: RvAnimator = RvAnimator.SlideInRightAnimation()) {
        this.showEnterAnim = showEnterAnim
        this.animator = animator
    }

    /**
     * 换成自己想要的动画
     */
    fun getAnim(holder: VH): Array<Animator>? {
        return animator?.getAnims(holder.itemView)
    }

    /**
     * @param list 刷新
     */
    fun refresh(list: List<T>?) {
        list?.let {
            refresh(it, true)
        }
    }

    /**
     * @param list  刷新
     * @param force 删除原来数据或者增加在最前面
     */
    fun refresh(list: List<T>?, force: Boolean) {
        list?.let {
            if (force) {
                //强制刷新 清楚原来所有数据
                mList.clear()
                mList.addAll(it)
            } else {
                mList.addAll(0, it)
            }
            notifyDataSetChanged()

        }
    }

    /**
     * @param list 增加集合
     */
    fun add(list: List<T>?) {
        list?.let {
            mList.addAll(it)
            notifyDataSetChanged()
        }
    }

    /**
     * @param t 增加
     */
    fun add(t: T) {
        insert(t, mList.size)
    }

    /**
     * @param t 对象
     * @param i 位置
     */
    fun insert(t: T?, i: Int) {
        t?.let {
            mList.add(min(i, mList.size), it)
            notifyDataSetChanged()
        }
    }

    fun clear() {
        mList.clear()
        notifyDataSetChanged()
    }

    /**
     * @param index 索引
     */
    fun remove(index: Int) {
        if (index >= 0 && index < mList.size) {
            mList.removeAt(index)
            notifyItemRemoved(index)
            notifyItemRangeChanged(index, mList.size - index)
        }
    }


    /**
     * @param t 对象
     */
    fun remove(t: T?) {
        t?.let {
            for (i in mList.indices) {
                if (it == mList[i]) {
                    remove(i)
                    return
                }
            }
        }
    }

    fun setOnItemClickListener(listener: (adapter: BaseRvAdapter<VH, T>, view: View, position: Int, item: T) -> Unit) {
        mOnItemClickListener = listener
    }

    fun setOnNoItemCallback(listener: () -> Unit) {
        mOnNoItemCallback = listener
    }


}
