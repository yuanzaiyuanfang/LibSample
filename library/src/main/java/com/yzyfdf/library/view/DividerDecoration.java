package com.yzyfdf.library.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;


public class DividerDecoration extends RecyclerView.ItemDecoration {

    private int mHeight;
    private int mLPadding;
    private int mRPadding;
    private int mTNum;
    private int mBNum;
    private boolean mDrawTop1;
    private Paint mPaint;

    private DividerDecoration(int height, int lPadding, int rPadding, int colour, int TNum, int BNum, boolean drawTop1) {
        mHeight = height;
        mLPadding = lPadding;
        mRPadding = rPadding;
        mTNum = TNum;
        mBNum = BNum;
        mDrawTop1 = drawTop1;
        mPaint = new Paint();
        mPaint.setColor(colour);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

        RecyclerView.Adapter adapter = parent.getAdapter();

        int count = parent.getChildCount();

        if (mDrawTop1 && count > 0) {
            View child = parent.getChildAt(0);
            int top = child.getTop();
            int bottom = top + mHeight;
            int left = child.getLeft() + mLPadding;
            int right = child.getRight() - mRPadding;
            c.save();
            c.drawRect(left, top, right, bottom, mPaint);
            c.restore();
        }

        for (int i = 0 + mTNum; i < count - mBNum; i++) {
            final View child = parent.getChildAt(i);
            final int top = child.getBottom();
            final int bottom = top + mHeight;

            int left = child.getLeft() + mLPadding;
            int right = child.getRight() - mRPadding;

            int position = parent.getChildAdapterPosition(child);

            c.save();

            c.drawRect(left, top, right, bottom, mPaint);

            c.restore();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.Adapter adapter = parent.getAdapter();

        int position = parent.getChildAdapterPosition(view);

        outRect.set(0, 0, 0, mHeight);
    }

    /**
     * A basic builder for divider decorations. The default builder creates a 1px thick black divider decoration.
     */
    public static class Builder {
        private Context mContext;
        private Resources mResources;
        private int mHeight;
        private int mLPadding;
        private int mRPadding;
        private int mColour;
        private int mTNum;
        private int mBNum;
        private boolean mDrawTop1;

        public Builder(Context context) {
            mContext = context;
            mResources = context.getResources();
            mHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 1f, context.getResources().getDisplayMetrics());
            mLPadding = 0;
            mRPadding = 0;
            mColour = Color.BLACK;
        }

        /**
         * Set the divider height in pixels
         *
         * @param pixels height in pixels
         * @return the current instance of the Builder
         */
        public DividerDecoration.Builder setHeight(float pixels) {
            mHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pixels, mResources.getDisplayMetrics());

            return this;
        }

        /**
         * Set the divider height in dp
         *
         * @param resource height resource id
         * @return the current instance of the Builder
         */
        public DividerDecoration.Builder setHeight(@DimenRes int resource) {
            mHeight = mResources.getDimensionPixelSize(resource);
            return this;
        }

        /**
         * Sets both the left and right padding in pixels
         *
         * @param pixels padding in pixels
         * @return the current instance of the Builder
         */
        public DividerDecoration.Builder setPadding(float pixels) {
            setLeftPadding(pixels);
            setRightPadding(pixels);

            return this;
        }

        /**
         * Sets the left and right padding in dp
         *
         * @param resource padding resource id
         * @return the current instance of the Builder
         */
        public DividerDecoration.Builder setPadding(@DimenRes int resource) {
            setLeftPadding(resource);
            setRightPadding(resource);
            return this;
        }

        /**
         * Sets the left padding in pixels
         *
         * @param pixelPadding left padding in pixels
         * @return the current instance of the Builder
         */
        public DividerDecoration.Builder setLeftPadding(float pixelPadding) {
            mLPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pixelPadding, mResources.getDisplayMetrics());

            return this;
        }

        /**
         * Sets the right padding in pixels
         *
         * @param pixelPadding right padding in pixels
         * @return the current instance of the Builder
         */
        public DividerDecoration.Builder setRightPadding(float pixelPadding) {
            mRPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pixelPadding, mResources.getDisplayMetrics());

            return this;
        }

        /**
         * Sets the left padding in dp
         *
         * @param resource left padding resource id
         * @return the current instance of the Builder
         */
        public DividerDecoration.Builder setLeftPadding(@DimenRes int resource) {
            mLPadding = mResources.getDimensionPixelSize(resource);

            return this;
        }

        /**
         * Sets the right padding in dp
         *
         * @param resource right padding resource id
         * @return the current instance of the Builder
         */
        public DividerDecoration.Builder setRightPadding(@DimenRes int resource) {
            mRPadding = mResources.getDimensionPixelSize(resource);

            return this;
        }

        /**
         * Sets the divider colour
         *
         * @param resource the colour resource id
         * @return the current instance of the Builder
         */
        public DividerDecoration.Builder setColorResource(@ColorRes int resource) {
            setColor(ContextCompat.getColor(mContext, resource));

            return this;
        }

        /**
         * Sets the divider colour
         *
         * @param color the colour
         * @return the current instance of the Builder
         */
        public DividerDecoration.Builder setColor(@ColorInt int color) {
            mColour = color;

            return this;
        }

        /**
         * 前面跳过几个不画分割线
         *
         * @param tNum
         * @return
         */
        public DividerDecoration.Builder skipTopNum(int tNum) {
            mTNum = tNum;

            return this;
        }

        /**
         * 后面少画几个分割线
         *
         * @param bNum
         * @return
         */
        public DividerDecoration.Builder skipBottomNum(int bNum) {
            mBNum = bNum;

            return this;
        }

        /**
         * 最顶上是否画线
         *
         * @param drawTop1
         * @return
         */
        public DividerDecoration.Builder drawTop1(boolean drawTop1) {
            mDrawTop1 = drawTop1;

            return this;
        }

        /**
         * Instantiates a DividerDecoration with the specified parameters.
         *
         * @return a properly initialized DividerDecoration instance
         */
        public DividerDecoration build() {
            return new DividerDecoration(mHeight, mLPadding, mRPadding, mColour, mTNum, mBNum, mDrawTop1);
        }
    }
}
