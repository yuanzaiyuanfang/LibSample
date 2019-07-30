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

    /**
     * @param list 刷新
     */
    public void refresh(List<? extends T> list) {
        if (list == null) {
            return;
        }
        refresh(list, true);
    }

    /**
     * @param list 刷新
     * @param force 删除原来数据或者增加在最前面
     */
    public void refresh(List<? extends T> list, boolean force) {
        if (list == null) {
            return;
        }
        if (force) {
            //强制刷新 清楚原来所有数据
            mList.clear();
            mList.addAll(list);
        } else {
            mList.addAll(0, list);
        }
        notifyDataSetChanged();
    }

    /**
     * @param list 增加集合
     */
    public void add(List<? extends T> list) {
        if (list == null) {
            return;
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * @param t 增加
     */
    public void add(T t) {
        insert(t, mList.size());
    }

    /**
     * @param t 对象
     * @param i 位置
     */
    public void insert(T t, int i) {
        if (t == null) {
            return;
        }
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


    /**
     * @param t 对象
     */
    public void remove(T t) {
        if (t == null) {
            return;
        }
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i) == t) {
                remove(i);
                return;
            }
        }
    }

    public List<T> getList() {
        return mList;
    }


    /**
     * @param <T> 条目被点击
     */
    public interface OnItemClickListener<T> {
        void onItemClick(T t, int i);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * 全部删除，没有条目了
     */
    public interface OnNoItemCallback {
        void noItem();
    }

    public void setOnNoItemCallback(OnNoItemCallback onNoItemCallback) {
        mOnNoItemCallback = onNoItemCallback;
    }


}
