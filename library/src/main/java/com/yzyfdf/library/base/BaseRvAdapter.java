package com.yzyfdf.library.base;

import android.animation.AnimatorSet;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseRvAdapter<VH extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter<VH> {

    public Context mContext;
    public LayoutInflater mInflater;
    public List<T> mList;
    public OnItemClickListener mOnItemClickListener;
    public OnNoItemCallback mOnNoItemCallback;

    private boolean showEnterAnim;
    private int mLastPosition = -1;

    public BaseRvAdapter(Context context) {
        this(context, new ArrayList<T>());
    }

    public BaseRvAdapter(Context context, List<T> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

        //第一次显示动画
        if (showEnterAnim && position > mLastPosition) {
            AnimatorSet set = getAnim(holder);
            set.start();
            mLastPosition = position;
        }

        //onBindViewHolder
        onBindVH(holder, position);
    }

    /**
     * onBindViewHolder
     */
    public abstract void onBindVH(VH holder, int position);

    /**
     * 是否显示条目进入动画
     */
    public void setShowEnterAnim(boolean showEnterAnim) {
        this.showEnterAnim = showEnterAnim;
    }

    /**
     * 换成自己想要的动画
     */
    public AnimatorSet getAnim(VH holder) {
        return RvAnimator.getInstance().getAnim(holder.itemView);
    }

    //方法
    public void refresh(List<? extends T> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        refresh(list, true);
    }

    public void refresh(List<? extends T> list, boolean force) {
        if (force) {
            //强制刷新 清楚原来所有数据
            mList.clear();
            mList.addAll(list);
        } else {
            mList.addAll(0, list);
        }
        notifyDataSetChanged();
    }

    public void add(List<? extends T> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void add(T t) {
        mList.add(t);
        notifyDataSetChanged();
    }

    public void add(T t, int i) {
        if (i > mList.size()) {
            i = mList.size();
        }
        mList.add(i, t);
        notifyDataSetChanged();
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    /**
     * @param index 索引
     */
    public void remove(int index) {
        mList.remove(index);
        notifyItemRemoved(index);
        notifyItemRangeChanged(index, mList.size() - index);
    }

    public List<T> getList() {
        return mList;
    }


    //接口
    public interface OnItemClickListener<T> {
        void onItemClick(T t, int i);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnNoItemCallback {
        void noItem();
    }

    public void setOnNoItemCallback(OnNoItemCallback onNoItemCallback) {
        mOnNoItemCallback = onNoItemCallback;
    }


}
