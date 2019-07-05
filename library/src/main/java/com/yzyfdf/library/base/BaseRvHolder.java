package com.yzyfdf.library.base;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


public class BaseRvHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViewSparseArray = new SparseArray<>();


    public BaseRvHolder(View itemView) {
        super(itemView);
    }

    //根据id找控件
    public View getViewById(@IdRes int id) {
        View view = mViewSparseArray.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            mViewSparseArray.put(id, view);
        }
        //返回view
        return view;
    }

    //返回TextView
    public TextView getTextViewById(int id) {
        return (TextView) getViewById(id);
    }

    //设置text
    public void setText(int textViewId, CharSequence str) {
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        getTextViewById(textViewId).setText(str);
    }

    //EditText
    public EditText getEditTextById(int id) {
        return (EditText) getViewById(id);
    }

    //设置text
    public void setEditText(int editTextId, CharSequence str) {
        getEditTextById(editTextId).setText(str);
    }

    //返回ImageView
    public ImageView getImageViewById(int id) {
        return (ImageView) getViewById(id);
    }

    public void setImage(int imageViewId, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(BaseApplication.getAppContext())
                .load(url)
                .thumbnail(0.1f)
                .into(getImageViewById(imageViewId));
    }

    public void setImage(int imageViewId, String url, @DrawableRes int defaultPic) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(BaseApplication.getAppContext())
                .load(url)
                .apply(RequestOptions.errorOf(defaultPic).placeholder(defaultPic))
                .thumbnail(0.1f)
                .into(getImageViewById(imageViewId));
    }

    public void setCircleImage(int imageViewId, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(BaseApplication.getAppContext())
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .into(getImageViewById(imageViewId));
    }

    //返回RecyclerView
    public RecyclerView getRecyclerView(int id) {
        return (RecyclerView) getViewById(id);
    }

    public void setOnClickListener(int id, View.OnClickListener listener) {
        View view = getViewById(id);
        view.setOnClickListener(listener);
    }

    public void setOnClickListener(int id, String text, View.OnClickListener listener) {
        TextView view = getTextViewById(id);
        view.setText(text);
        view.setOnClickListener(listener);
    }

    public <D> void refreshData(D data) {

    }

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
        return view.getText().toString().replaceAll(" ", "").trim();
    }
}

