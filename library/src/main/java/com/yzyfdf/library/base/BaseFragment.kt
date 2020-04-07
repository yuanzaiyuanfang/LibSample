package com.yzyfdf.library.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yzyfdf.library.rx.RxManager
import com.yzyfdf.library.utils.TUtil
import com.yzyfdf.library.view.CustomProgressDialog

abstract class BaseFragment<T : BasePresenter<*, *>, E : BaseModel> : Fragment() {

    protected var rootView: View? = null
    var mPresenter: T? = null
    var mModel: E? = null
    var mRxManager: RxManager? = null
    var mContext: Context? = null

    var isFirst = true
        private set
    var isLoad = false
        private set

    private var dialog: CustomProgressDialog? = null

    //获取布局文件
    protected abstract val layoutResource: Int

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            rootView = inflater.inflate(layoutResource, container, false)
        }

        mRxManager = RxManager()
        mPresenter = TUtil.getT<T>(this, 0)
        mModel = TUtil.getT<E>(this, 1)
        if (mPresenter != null) {
            mPresenter!!.mContext = this.context
        }
        mContext = context

        initPresenter()

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initListener()
        initData()
    }

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    abstract fun initPresenter()

    //初始化view
    abstract fun initView()

    open fun initListener() {}

    open fun initData() {}

    /**
     * framlayout中的fg懒加载
     */
    open fun lazyInit4Fl() {}

    /**
     * viewpager中的fg懒加载
     */
    open fun lazyInit4Vp() {}

    /**
     * fragment被重复选择
     */
    open fun onPagerReSelected() {

    }

    /**
     * 开启加载进度条
     *
     * @param msg
     */
    @JvmOverloads
    fun startProgressDialog(msg: String = "") {
        if (dialog == null && mContext != null) {
            dialog = CustomProgressDialog(mContext!!, msg)
        }
        dialog?.show()
    }

    /**
     * 停止加载进度条
     */
    fun stopProgressDialog() {
        dialog?.dismiss()
    }

    val isProgressLoading: Boolean
        get() = if (dialog == null) {
            false
        } else dialog!!.isShowing

    override fun onResume() {
        super.onResume()
        if (isFirst) {
            lazyInit4Vp()
            isFirst = false
        }
    }

    override fun onDestroyView() {
        mPresenter?.onDestroy()
        mRxManager?.clear()
        super.onDestroyView()
        isFirst = true
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!isLoad && !hidden) {
            lazyInit4Fl()
            isLoad = true
        }
    }


}
