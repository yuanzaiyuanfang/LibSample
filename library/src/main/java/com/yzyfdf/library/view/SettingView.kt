package com.yzyfdf.library.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.SwitchCompat
import com.yzyfdf.library.R

/**
 * @author sjj , 2019/4/23 15:13
 * 设置item
 */
class SettingView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {
    /*左侧显示文本*/
    private var mLeftText: String? = null
    /*左侧图标*/
    private var mLeftIcon: Drawable? = null
    /*右侧图标*/
    private var mRightIcon: Drawable? = null
    /*左侧显示文本大小*/
    private val mTextSize: Int = 0
    /*左侧显示文本颜色*/
    private var mTextColor: Int = 0
    /*右侧显示文本大小*/
    private var mRightTextSize: Float = 0.toFloat()
    /*右侧显示文本颜色*/
    private var mRightTextColor: Int = 0
    /*整体根布局view*/
    private var mView: View? = null
    /*根布局*/
    private var mRootLayout: RelativeLayout? = null
    /*左侧文本控件*/
    private var mTvLeftText: TextView? = null
    /*右侧文本控件*/
    private var mTvRightText: TextView? = null
    /*分割线*/
    private var mUnderLine: View? = null
    /*左侧图标控件*/
    private var mIvLeftIcon: ImageView? = null
    /*左侧图标大小*/
    private var mLeftIconSzie: Int = 0
    /*右侧图标控件区域,默认展示图标*/
    private var mRightLayout: FrameLayout? = null
    /*右侧图标控件,默认展示图标*/
    private var mIvRightIcon: ImageView? = null
    /*右侧图标控件,选择样式图标*/
    private var mRightIcon_check: AppCompatCheckBox? = null
    /*右侧图标控件,开关样式图标*/
    private var mRightIcon_switch: SwitchCompat? = null
    /*右侧图标展示风格*/
    private var mRightStyle = 0
    /*选中状态*/
    private var mChecked: Boolean = false
    /*点击事件*/
    private lateinit var mOnSettingItemClick: (isChecked: Boolean) -> Unit

    init {
        initView(context)
        getCustomStyle(context, attrs)
        //获取到右侧展示风格，进行样式切换
        switchRightStyle(mRightStyle)
        mRootLayout!!.setOnClickListener { clickOn() }
        mRightIcon_check!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (::mOnSettingItemClick.isInitialized) {
                mOnSettingItemClick(isChecked)
            }
        }
        mRightIcon_switch!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (::mOnSettingItemClick.isInitialized) {
                mOnSettingItemClick(isChecked)
            }
        }
    }

    fun setOnSettingItemClick(click: (isChecked: Boolean) -> Unit) {
        mOnSettingItemClick = click
    }

    /**
     * 初始化自定义属性
     *
     * @param context
     * @param attrs
     */
    fun getCustomStyle(context: Context, attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SettingView)
        val n = a.indexCount
        for (i in 0 until n) {
            val attr = a.getIndex(i)
            if (attr == R.styleable.SettingView_left_text) {
                mLeftText = a.getString(attr)
                mTvLeftText!!.text = mLeftText
            } else if (attr == R.styleable.SettingView_left_icon) {
                // 左侧图标
                mLeftIcon = a.getDrawable(attr)
                if (null != mLeftIcon) {
                    mIvLeftIcon!!.setImageDrawable(mLeftIcon)
                    mIvLeftIcon!!.visibility = View.VISIBLE
                }
            } else if (attr == R.styleable.SettingView_left_iconSize) {
                mLeftIconSzie = a.getDimension(attr, 16f).toInt()
                val layoutParams = mIvLeftIcon!!.layoutParams as RelativeLayout.LayoutParams
                layoutParams.width = mLeftIconSzie
                layoutParams.height = mLeftIconSzie
                mIvLeftIcon!!.layoutParams = layoutParams
            } else if (attr == R.styleable.SettingView_left_text_margin_left) {
                val leftMargin = a.getDimension(attr, 8f).toInt()
                val layoutParams = mTvLeftText!!.layoutParams as RelativeLayout.LayoutParams
                layoutParams.leftMargin = leftMargin
                mTvLeftText!!.layoutParams = layoutParams
            } else if (attr == R.styleable.SettingView_right_icon) {
                // 右侧图标
                mRightIcon = a.getDrawable(attr)
                mIvRightIcon!!.setImageDrawable(mRightIcon)
            } else if (attr == R.styleable.SettingView_right_icon_size) {
                val rightIconSzie = a.getDimension(attr, 24f).toInt()
                val layoutParams = mIvRightIcon!!.layoutParams as FrameLayout.LayoutParams
                layoutParams.width = rightIconSzie
                layoutParams.height = rightIconSzie
                mIvRightIcon!!.layoutParams = layoutParams
            } else if (attr == R.styleable.SettingView_l_text_size) {
                // 默认设置为16sp
                val textSize = a.getDimensionPixelSize(attr, 16).toFloat()
                mTvLeftText!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
            } else if (attr == R.styleable.SettingView_l_text_color) {
                //文字默认灰色
                mTextColor = a.getColor(attr, Color.LTGRAY)
                mTvLeftText!!.setTextColor(mTextColor)
            } else if (attr == R.styleable.SettingView_right_style) {
                mRightStyle = a.getInt(attr, 0)
            } else if (attr == R.styleable.SettingView_is_show_under_line) {
                //默认显示分割线
                if (!a.getBoolean(attr, true)) {
                    mUnderLine!!.visibility = View.GONE
                }
            } else if (attr == R.styleable.SettingView_is_show_right_text) {
                //默认不显示右侧文字
                if (a.getBoolean(attr, false)) {
                    mTvRightText!!.visibility = View.VISIBLE
                }
            } else if (attr == R.styleable.SettingView_right_text) {
                mTvRightText!!.text = a.getString(attr)
            } else if (attr == R.styleable.SettingView_right_text_size) {

                // 默认设置为16sp
                mRightTextSize = a.getDimensionPixelSize(attr, 14).toFloat()
                mTvRightText!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightTextSize)
            } else if (attr == R.styleable.SettingView_right_text_color) {
                //文字默认灰色
                mRightTextColor = a.getColor(attr, Color.GRAY)
                mTvRightText!!.setTextColor(mRightTextColor)
            }
        }
        a.recycle()
    }

    /**
     * 根据设定切换右侧展示样式，同时更新点击事件处理方式
     *
     * @param mRightStyle
     */
    private fun switchRightStyle(mRightStyle: Int) {
        when (mRightStyle) {
            0 -> {
                //默认展示样式，只展示一个图标
                mIvRightIcon!!.visibility = View.VISIBLE
                mRightIcon_check!!.visibility = View.GONE
                mRightIcon_switch!!.visibility = View.GONE
            }
            1 -> {
                //隐藏右侧图标
                mRightLayout!!.visibility = View.INVISIBLE
                mRightLayout!!.layoutParams.width = 38//多加一行这个将文字设置靠右对齐即可
            }
            2 -> {
                //显示选择框样式
                mIvRightIcon!!.visibility = View.GONE
                mRightIcon_check!!.visibility = View.VISIBLE
                mRightIcon_switch!!.visibility = View.GONE
            }
            3 -> {
                //显示开关切换样式
                mIvRightIcon!!.visibility = View.GONE
                mRightIcon_check!!.visibility = View.GONE
                mRightIcon_switch!!.visibility = View.VISIBLE
            }
        }
    }

    private fun initView(context: Context) {
        mView = View.inflate(context, R.layout.layout_setting_view, this)
        mRootLayout = mView!!.findViewById<View>(R.id.rootLayout) as RelativeLayout
        mUnderLine = mView!!.findViewById(R.id.underline) as View
        mTvLeftText = mView!!.findViewById<View>(R.id.tv_lefttext) as TextView
        mTvRightText = mView!!.findViewById<View>(R.id.tv_righttext) as TextView
        mIvLeftIcon = mView!!.findViewById<View>(R.id.iv_lefticon) as ImageView
        mIvRightIcon = mView!!.findViewById<View>(R.id.iv_righticon) as ImageView
        mRightLayout = mView!!.findViewById<View>(R.id.rightlayout) as FrameLayout
        mRightIcon_check = mView!!.findViewById<View>(R.id.rightcheck) as AppCompatCheckBox
        mRightIcon_switch = mView!!.findViewById<View>(R.id.rightswitch) as SwitchCompat
    }

    /**
     * 处理点击事件
     */
    fun clickOn() {
        when (mRightStyle) {
            0, 1 -> if (::mOnSettingItemClick.isInitialized) {
                mOnSettingItemClick(mChecked)
            }
            2 -> {
                //选择框切换选中状态
                mRightIcon_check!!.isChecked = !mRightIcon_check!!.isChecked
                mChecked = mRightIcon_check!!.isChecked
            }
            3 -> {
                //开关切换状态
                mRightIcon_switch!!.isChecked = !mRightIcon_switch!!.isChecked
                mChecked = mRightIcon_check!!.isChecked
            }
        }
    }

    /**
     * 获取根布局对象
     *
     * @return
     */
    fun getmRootLayout(): RelativeLayout? {
        return mRootLayout
    }

    /**
     * 更改左侧文字
     */
    fun setLeftText(info: String) {
        mTvLeftText!!.text = info
    }

    /**
     * 更改右侧文字
     */
    fun setRightText(info: String) {
        mTvRightText!!.text = info
    }

    interface OnSettingItemClick {
        fun click(isChecked: Boolean)
    }
}
