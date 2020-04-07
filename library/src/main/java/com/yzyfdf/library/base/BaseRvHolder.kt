package com.yzyfdf.library.base

import android.util.SparseArray
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class BaseRvHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val mViewSparseArray = SparseArray<View>()

    /**
     * @param id
     * @return 根据id找控件
     */
    @Suppress("UNCHECKED_CAST")
    fun <V : View> getView(@IdRes id: Int): V {
        var view = mViewSparseArray.get(id)
        if (view == null) {
            view = itemView.findViewById(id)
            mViewSparseArray.put(id, view)
        }
        return view as V
    }

    /**
     * @param id
     * @param str 设置text
     */
    fun setText(@IdRes id: Int, str: CharSequence?): BaseRvHolder {
        getView<TextView>(id).text = str
        return this
    }

    /**
     * @param id
     * @param str 设置text
     */
    fun setEditText(@IdRes id: Int, str: CharSequence?): BaseRvHolder {
        getView<EditText>(id).setText(str)
        return this
    }

    fun setImage(@IdRes imageViewId: Int, url: String?): BaseRvHolder {
        url?.let {
            Glide.with(BaseApplication.appContext)
                    .load(it)
                    .thumbnail(0.1f)
                    .into(getView(imageViewId))
        }
        return this
    }

    fun setImage(@IdRes imageViewId: Int, url: String?, @DrawableRes defaultPic: Int): BaseRvHolder {
        url?.let {
            Glide.with(BaseApplication.appContext)
                    .load(it)
                    .apply(RequestOptions.errorOf(defaultPic).placeholder(defaultPic))
                    .thumbnail(0.1f)
                    .into(getView(imageViewId))
        }
        return this
    }

    fun setCircleImage(imageViewId: Int, url: String?): BaseRvHolder {
        url?.let {
            Glide.with(BaseApplication.appContext)
                    .load(it)
                    .apply(RequestOptions.circleCropTransform())
                    .into(getView(imageViewId))
        }
        return this
    }

    /**
     * @param id
     * @param listener 点击事件
     */
    fun setOnClickListener(@IdRes id: Int, listener: () -> Unit): BaseRvHolder {
        getView<View>(id).setOnClickListener { listener() }
        return this
    }

    /**
     * view 是否显示
     *
     * @return
     */
    fun setViewGone(@IdRes viewId: Int, visible: Boolean): BaseRvHolder {
        getView<View>(viewId).visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    /**
     * view 是否显示
     *
     * @return
     */
    fun setViewInvisible(@IdRes viewId: Int, visible: Boolean): BaseRvHolder {
        getView<View>(viewId).visibility = if (visible) View.VISIBLE else View.INVISIBLE
        return this
    }

    /**
     * @param isVisible 显示/隐藏
     */
    fun setVisibility(isVisible: Boolean) {
        val param = itemView.layoutParams as RecyclerView.LayoutParams
        if (isVisible) {
            param.height = LinearLayout.LayoutParams.WRAP_CONTENT
            param.width = LinearLayout.LayoutParams.MATCH_PARENT
            itemView.visibility = View.VISIBLE
        } else {
            itemView.visibility = View.GONE
            param.height = 0
            param.width = 0
        }
        itemView.layoutParams = param
    }

    /**
     * @param data 数据处理，子类实现
     */
    fun <D> refreshData(data: D) {

    }

}

