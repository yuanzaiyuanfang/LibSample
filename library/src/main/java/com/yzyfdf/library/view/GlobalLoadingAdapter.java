package com.yzyfdf.library.view;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.billy.android.loading.Gloading;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yzyfdf.library.R;
import com.yzyfdf.library.base.BaseApplication;

import static com.billy.android.loading.Gloading.STATUS_EMPTY_DATA;
import static com.billy.android.loading.Gloading.STATUS_LOADING;
import static com.billy.android.loading.Gloading.STATUS_LOAD_FAILED;
import static com.billy.android.loading.Gloading.STATUS_LOAD_SUCCESS;

/**
 * @author sjj , 2019/4/25 13:35
 * 全局加载
 */
public class GlobalLoadingAdapter implements Gloading.Adapter {
    @Override
    public View getView(Gloading.Holder holder, View convertView, int status) {
        GlobalLoadingStatusView loadingStatusView = null;
        //convertView为可重用的布局
        //Holder中缓存了各状态下对应的View
        //	如果status对应的View为null，则convertView为上一个状态的View
        //	如果上一个状态的View也为null，则convertView为null
        if (convertView != null && convertView instanceof GlobalLoadingStatusView) {
            loadingStatusView = (GlobalLoadingStatusView) convertView;
        }
        if (loadingStatusView == null) {
            loadingStatusView = new GlobalLoadingStatusView(holder.getContext(), holder.getRetryTask());
        }
        loadingStatusView.setStatus(status);
        return loadingStatusView;
    }

    public class GlobalLoadingStatusView extends LinearLayout implements View.OnClickListener {

        private final TextView mTextView;
        private final Runnable mRetryTask;
        private final ImageView mImageView;

        public GlobalLoadingStatusView(Context context, Runnable retryTask) {
            super(context);
            setOrientation(VERTICAL);
            setGravity(Gravity.CENTER_HORIZONTAL);
            LayoutInflater.from(context).inflate(R.layout.view_global_loading_status, this, true);
            mImageView = findViewById(R.id.image);
            mTextView = findViewById(R.id.text);
            this.mRetryTask = retryTask;
            setBackgroundColor(Color.parseColor("#eeeeee"));
        }

        public void setMsgViewVisibility(boolean visible) {
            mTextView.setVisibility(visible ? VISIBLE : GONE);
        }

        public void setStatus(int status) {
            boolean show = true;
            View.OnClickListener onClickListener = null;
            int image = R.drawable.loading;
            int str = R.string.str_none;
            switch (status) {
                case STATUS_LOAD_SUCCESS:
                    show = false;
                    break;
                case STATUS_LOADING:
                    str = R.string.loading;
                    Glide.with(BaseApplication.getAppContext()).load(image)
                            .apply(RequestOptions.overrideOf(SizeUtils.dp2px(50), SizeUtils.dp2px(50)))
                            .into(mImageView);
                    break;
                case STATUS_LOAD_FAILED:
                    str = R.string.load_failed;
                    image = R.mipmap.icon_failed;
                    boolean networkConn = NetworkUtils.isConnected();
                    if (!networkConn) {
                        str = R.string.load_no_net;
                        image = R.mipmap.icon_no_wifi;
                    }
                    onClickListener = this;
                    mImageView.setImageResource(image);
                    break;
                case STATUS_EMPTY_DATA:
                    str = R.string.empty;
                    image = R.mipmap.icon_empty;
                    onClickListener = this;
                    mImageView.setImageResource(image);
                    break;
                default:
                    break;
            }


            setOnClickListener(onClickListener);
            mTextView.setText(str);
            setVisibility(show ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
            if (mRetryTask != null) {
                mRetryTask.run();
            }
        }
    }
}
