package com.yzyfdf.library.base;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseVPAdapter<T> extends PagerAdapter {
    protected final LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mList;
    protected OnItemClickListener mOnItemClickListener;

    public BaseVPAdapter(Context context, List<T> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    /**
     *  PhotoView view = (PhotoView) mInflater.inflate(R.layout.item_big_pic, container, false);
     *  Glide.with(mContext).load(mList.get(position)).into(view);
     *  container.addView(view);
     *
     *  view.setOnClickListener(v -> {
     *      if (mOnItemClickListener != null) {
     *          mOnItemClickListener.onItemClick(position);
     *      }
     *  });
     *  return view;
     */
    public abstract Object instantiateItem(ViewGroup container, int position);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}

