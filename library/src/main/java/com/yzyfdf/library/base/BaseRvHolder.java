package com.yzyfdf.library.base;

import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


public class BaseRvHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViewSparseArray = new SparseArray<>();


    public BaseRvHolder(View itemView) {
        super(itemView);
    }

    /**
     * @param id
     * @return 根据id找控件
     */
    public View getViewById(@IdRes int id) {
        View view = mViewSparseArray.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            mViewSparseArray.put(id, view);
        }
        return view;
    }

    /**
     * @param id
     * @return 返回TextView
     */
    public TextView getTextView(int id) {
        return (TextView) getViewById(id);
    }

    /**
     * @param textViewId
     * @param str 设置text
     */
    public void setText(int textViewId, CharSequence str) {
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        getTextView(textViewId).setText(str);
    }

    /**
     * @param id
     * @return EditText
     */
    public EditText getEditText(int id) {
        return (EditText) getViewById(id);
    }

    /**
     * @param editTextId
     * @param str 设置text
     */
    public void setEditText(int editTextId, CharSequence str) {
        getEditText(editTextId).setText(str);
    }

    /**
     * @param id
     * @return 返回ImageView
     */
    public ImageView getImageView(int id) {
        return (ImageView) getViewById(id);
    }

    public void setImage(int imageViewId, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(BaseApplication.getAppContext())
                .load(url)
                .thumbnail(0.1f)
                .into(getImageView(imageViewId));
    }

    public void setImage(int imageViewId, String url, @DrawableRes int defaultPic) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(BaseApplication.getAppContext())
                .load(url)
                .apply(RequestOptions.errorOf(defaultPic).placeholder(defaultPic))
                .thumbnail(0.1f)
                .into(getImageView(imageViewId));
    }

    public void setCircleImage(int imageViewId, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(BaseApplication.getAppContext())
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .into(getImageView(imageViewId));
    }

    /**
     * @param id
     * @return 返回RecyclerView
     */
    public RecyclerView getRecyclerView(int id) {
        return (RecyclerView) getViewById(id);
    }

    /**
     * @param id
     * @param listener 点击事件
     */
    public void setOnClickListener(int id, View.OnClickListener listener) {
        View view = getViewById(id);
        view.setOnClickListener(listener);
    }

    /**
     * view 是否显示
     *
     * @return
     */
    public BaseRvHolder setViewVisible(@IdRes int viewId, boolean visible) {
        View view = getViewById(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * @param isVisible 显示/隐藏
     */
    public void setVisibility(boolean isVisible) {
        RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        if (isVisible) {
            param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            param.width = LinearLayout.LayoutParams.MATCH_PARENT;
            itemView.setVisibility(View.VISIBLE);
        } else {
            itemView.setVisibility(View.GONE);
            param.height = 0;
            param.width = 0;
        }
        itemView.setLayoutParams(param);
    }

    /**
     * 获取TextView、EditText、Button控件内容
     *
     * @param view
     * @return
     */
    protected String getWidgetContent(TextView view) {
        return view.getText().toString().trim();
    }


    /**
     * @param data 数据处理，子类实现
     */
    public <D> void refreshData(D data) {

    }

}

