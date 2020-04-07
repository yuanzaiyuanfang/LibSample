package com.yzyfdf.library.view

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView


class DividerDecoration private constructor(
    private val mHeight: Int, private val mLPadding: Int, private val mRPadding: Int, colour: Int,
    private val mTNum: Int, private val mBNum: Int, private val mDrawTop1: Boolean
) : RecyclerView.ItemDecoration() {

    private val mPaint: Paint = Paint()

    init {
        mPaint.color = colour
    }

    /**
     * {@inheritDoc}
     */
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {

        val adapter = parent.adapter

        val count = parent.childCount

        if (mDrawTop1 && count > 0) {
            val child = parent.getChildAt(0)
            val top = child.top
            val bottom = top + mHeight
            val left = child.left + mLPadding
            val right = child.right - mRPadding
            c.save()
            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
            c.restore()
        }

        for (i in 0 + mTNum until count - mBNum) {
            val child = parent.getChildAt(i)
            val top = child.bottom
            val bottom = top + mHeight

            val left = child.left + mLPadding
            val right = child.right - mRPadding

            val position = parent.getChildAdapterPosition(child)

            c.save()

            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)

            c.restore()
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val adapter = parent.adapter

        val position = parent.getChildAdapterPosition(view)

        outRect.set(0, 0, 0, mHeight)
    }

    /**
     * A basic builder for divider decorations. The default builder creates a 1px thick black divider decoration.
     */
    class Builder(private val mContext: Context) {
        private val mResources: Resources
        private var mHeight: Int = 0
        private var mLPadding: Int = 0
        private var mRPadding: Int = 0
        private var mColour: Int = 0
        private var mTNum: Int = 0
        private var mBNum: Int = 0
        private var mDrawTop1: Boolean = false

        init {
            mResources = mContext.resources
            mHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 1f,
                    mContext.resources.displayMetrics)
                .toInt()
            mLPadding = 0
            mRPadding = 0
            mColour = Color.BLACK
        }

        /**
         * Set the divider height in pixels
         *
         * @param pixels height in pixels
         * @return the current instance of the Builder
         */
        fun setHeight(pixels: Float): DividerDecoration.Builder {
            mHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pixels,
                    mResources.displayMetrics)
                .toInt()

            return this
        }

        /**
         * Set the divider height in dp
         *
         * @param resource height resource id
         * @return the current instance of the Builder
         */
        fun setHeight(@DimenRes resource: Int): DividerDecoration.Builder {
            mHeight = mResources.getDimensionPixelSize(resource)
            return this
        }

        /**
         * Sets both the left and right padding in pixels
         *
         * @param pixels padding in pixels
         * @return the current instance of the Builder
         */
        fun setPadding(pixels: Float): DividerDecoration.Builder {
            setLeftPadding(pixels)
            setRightPadding(pixels)

            return this
        }

        /**
         * Sets the left and right padding in dp
         *
         * @param resource padding resource id
         * @return the current instance of the Builder
         */
        fun setPadding(@DimenRes resource: Int): DividerDecoration.Builder {
            setLeftPadding(resource)
            setRightPadding(resource)
            return this
        }

        /**
         * Sets the left padding in pixels
         *
         * @param pixelPadding left padding in pixels
         * @return the current instance of the Builder
         */
        fun setLeftPadding(pixelPadding: Float): DividerDecoration.Builder {
            mLPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pixelPadding,
                    mResources.displayMetrics)
                .toInt()

            return this
        }

        /**
         * Sets the right padding in pixels
         *
         * @param pixelPadding right padding in pixels
         * @return the current instance of the Builder
         */
        fun setRightPadding(pixelPadding: Float): DividerDecoration.Builder {
            mRPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pixelPadding,
                    mResources.displayMetrics)
                .toInt()

            return this
        }

        /**
         * Sets the left padding in dp
         *
         * @param resource left padding resource id
         * @return the current instance of the Builder
         */
        fun setLeftPadding(@DimenRes resource: Int): DividerDecoration.Builder {
            mLPadding = mResources.getDimensionPixelSize(resource)

            return this
        }

        /**
         * Sets the right padding in dp
         *
         * @param resource right padding resource id
         * @return the current instance of the Builder
         */
        fun setRightPadding(@DimenRes resource: Int): DividerDecoration.Builder {
            mRPadding = mResources.getDimensionPixelSize(resource)

            return this
        }

        /**
         * Sets the divider colour
         *
         * @param resource the colour resource id
         * @return the current instance of the Builder
         */
        fun setColorResource(@ColorRes resource: Int): DividerDecoration.Builder {
            setColor(ContextCompat.getColor(mContext, resource))

            return this
        }

        /**
         * Sets the divider colour
         *
         * @param color the colour
         * @return the current instance of the Builder
         */
        fun setColor(@ColorInt color: Int): DividerDecoration.Builder {
            mColour = color

            return this
        }

        /**
         * 前面跳过几个不画分割线
         *
         * @param tNum
         * @return
         */
        fun skipTopNum(tNum: Int): DividerDecoration.Builder {
            mTNum = tNum

            return this
        }

        /**
         * 后面少画几个分割线
         *
         * @param bNum
         * @return
         */
        fun skipBottomNum(bNum: Int): DividerDecoration.Builder {
            mBNum = bNum

            return this
        }

        /**
         * 最顶上是否画线
         *
         * @param drawTop1
         * @return
         */
        fun drawTop1(drawTop1: Boolean): DividerDecoration.Builder {
            mDrawTop1 = drawTop1

            return this
        }

        /**
         * Instantiates a DividerDecoration with the specified parameters.
         *
         * @return a properly initialized DividerDecoration instance
         */
        fun build(): DividerDecoration {
            return DividerDecoration(mHeight, mLPadding, mRPadding, mColour, mTNum, mBNum,
                    mDrawTop1)
        }
    }
}
