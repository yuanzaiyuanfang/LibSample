package com.yzyfdf.library.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * des:波浪view
 * Created by xsf
 * on 2016.12.02
 */
class WaveView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val mAbovePath: Path
    private val mBelowWavePath: Path
    private val mAboveWavePaint: Paint
    private val mBelowWavePaint: Paint
    private val mDrawFilter: DrawFilter
    private var φ: Float = 0.toFloat()

    private var mWaveAnimationListener: OnWaveAnimationListener? = null

    init {
        //初始化路径
        mAbovePath = Path()
        mBelowWavePath = Path()
        //初始化画笔
        mAboveWavePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mAboveWavePaint.isAntiAlias = true
        mAboveWavePaint.style = Paint.Style.FILL
        mAboveWavePaint.color = Color.WHITE

        mBelowWavePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mBelowWavePaint.isAntiAlias = true
        mBelowWavePaint.style = Paint.Style.FILL
        mBelowWavePaint.color = Color.WHITE
        mBelowWavePaint.alpha = 80
        //画布抗锯齿
        mDrawFilter = PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
    }

    override fun onDraw(canvas: Canvas) {

        canvas.drawFilter = mDrawFilter

        mAbovePath.reset()
        mBelowWavePath.reset()

        φ -= 0.1f

        var y: Float
        var y2: Float

        val ω = 2 * Math.PI / width

        mAbovePath.moveTo(left.toFloat(), bottom.toFloat())
        mBelowWavePath.moveTo(left.toFloat(), bottom.toFloat())
        var x = 0f
        while (x <= width) {
            /**
             * y=Asin(ωx+φ)+k
             * A—振幅越大，波形在y轴上最大与最小值的差值越大
             * ω—角速度， 控制正弦周期(单位角度内震动的次数)
             * φ—初相，反映在坐标系上则为图像的左右移动。这里通过不断改变φ,达到波浪移动效果
             * k—偏距，反映在坐标系上则为图像的上移或下移。
             */
            y = (rangeY * Math.cos(ω * x + φ) + 8).toFloat()
            y2 = (rangeY * Math.sin(ω * x + φ)).toFloat()
            mAbovePath.lineTo(x, y)
            mBelowWavePath.lineTo(x, y2)
            //回调 把y坐标的值传出去(在activity里面接收让小机器人随波浪一起摇摆)
            if (mWaveAnimationListener != null) mWaveAnimationListener!!.OnWaveAnimation(y)
            x += 20f
        }
        mAbovePath.lineTo(right.toFloat(), bottom.toFloat())
        mBelowWavePath.lineTo(right.toFloat(), bottom.toFloat())

        canvas.drawPath(mAbovePath, mAboveWavePaint)
        canvas.drawPath(mBelowWavePath, mBelowWavePaint)

        postInvalidateDelayed(20)

    }

    fun setOnWaveAnimationListener(l: OnWaveAnimationListener) {
        this.mWaveAnimationListener = l
    }

    interface OnWaveAnimationListener {
        fun OnWaveAnimation(y: Float)
    }

    companion object {
        private var rangeY = 8 //Y轴波动幅度

        /**
         * 设置波动幅度
         *
         * @param rangeY
         */
        fun setRangeY(rangeY: Int) {
            WaveView.rangeY = rangeY
        }
    }
}


