package com.yzyfdf.library.view.globalloading

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.blankj.utilcode.util.NetworkUtils
import com.yzyfdf.library.R
import com.yzyfdf.library.view.globalloading.Gloading.Companion.STATUS_EMPTY_DATA
import com.yzyfdf.library.view.globalloading.Gloading.Companion.STATUS_LOADING
import com.yzyfdf.library.view.globalloading.Gloading.Companion.STATUS_LOAD_FAILED
import com.yzyfdf.library.view.globalloading.Gloading.Companion.STATUS_LOAD_SUCCESS


/**
 * Created by Administrator .
 * 描述
 */
class GlobalLoadingStatusView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val mTextView: TextView
    private val mImageView: ImageView
    lateinit var onClickListener: () -> Unit

    init {
        //        setOrientation(VERTICAL);
//                setGravity(Gravity.CENTER_HORIZONTAL);
        View.inflate(context, R.layout.layout_global_loading_status, this)
        mImageView = findViewById(R.id.image)
        mTextView = findViewById(R.id.text)
    }

    fun setMsgViewVisibility(visible: Boolean) {
        mTextView.visibility = if (visible) View.VISIBLE else View.GONE
    }

    fun setRetryTask(mRetryTask: () -> Unit) {
        onClickListener = mRetryTask
    }

    fun setStatus(status: Int, icon: Int, str: Int) {
        var show = true
        var image = R.drawable.loading
        var msg = R.string.str_none
        var listener = false

        when (status) {
            STATUS_LOAD_SUCCESS -> show = false

            STATUS_LOADING -> {
                msg = R.string.loading
                image = R.drawable.loading
            }

            STATUS_LOAD_FAILED -> {
                msg = R.string.load_failed
                image = R.mipmap.icon_failed
                val networkConn = NetworkUtils.isConnected()
                if (!networkConn) {
                    msg = R.string.load_no_net
                    image = R.mipmap.icon_no_wifi
                }
                listener = true
            }

            STATUS_EMPTY_DATA -> {
                msg = R.string.empty
                image = R.mipmap.icon_empty
                listener = true
            }

            else -> {
            }
        }

        if (icon != 0) {
            image = icon
        }
        if (str != 0) {
            msg = str
        }

        visibility = if (show) View.VISIBLE else View.GONE
        mTextView.setText(msg)
        mImageView.setImageResource(image)
        if (listener && this::onClickListener.isInitialized) {
            setOnClickListener { onClickListener() }
        } else {
            removeCallbacks(onClickListener)
        }
    }

}