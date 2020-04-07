package com.yzyfdf.library.view


import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

import com.wang.avi.AVLoadingIndicatorView
import com.yzyfdf.library.R

class CustomProgressDialog constructor(
    private val mContext: Context, private val mMsg: String
) : Dialog(mContext, R.style.CustomProgressDialog) {

    private var mTvMsg: TextView? = null
    private var mIvLoading: AVLoadingIndicatorView? = null

    init {
        initView(mContext)
    }

    fun initView(context: Context) {
        val root = LayoutInflater.from(context)
            .inflate(R.layout.layout_loading_dialog, null)
        setContentView(root)

        mIvLoading = root.findViewById(R.id.iv_loading)
        //        Glide.with(context).load(R.drawable.loading)
        //                .apply(RequestOptions.overrideOf(SizeUtils.dp2px(60), SizeUtils.dp2px(60)))
        //                .into(ivLoading);

        mTvMsg = findViewById(R.id.tv_msg)
        setMessage(mMsg)

        window!!.attributes.gravity = Gravity.CENTER
        setCancelable(true)
        setCanceledOnTouchOutside(false)
    }

    /**
     * setMessage 提示内容
     *
     * @param strMessage
     * @return
     */
    fun setMessage(strMessage: String): CustomProgressDialog {
        if (mTvMsg != null) {
            if (TextUtils.isEmpty(strMessage)) {
                mTvMsg!!.visibility = View.GONE
            } else {
                mTvMsg!!.text = strMessage
                mTvMsg!!.visibility = View.VISIBLE
            }
        }
        return this
    }

    override fun show() {
        super.show()
        mIvLoading?.smoothToShow()
    }

    override fun dismiss() {
        super.dismiss()
        mIvLoading?.smoothToHide()
    }
}
