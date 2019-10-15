package com.yzyfdf.library.view.globalloading;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.NetworkUtils;
import com.yzyfdf.library.R;

import static com.yzyfdf.library.view.globalloading.Gloading.STATUS_EMPTY_DATA;
import static com.yzyfdf.library.view.globalloading.Gloading.STATUS_LOADING;
import static com.yzyfdf.library.view.globalloading.Gloading.STATUS_LOAD_FAILED;
import static com.yzyfdf.library.view.globalloading.Gloading.STATUS_LOAD_SUCCESS;

/**
 * Created by Administrator .
 * 描述
 */
public class GlobalLoadingStatusView extends LinearLayout implements View.OnClickListener {

    private final TextView mTextView;
    private Runnable mRetryTask;
    private final ImageView mImageView;

    public GlobalLoadingStatusView(Context context) {
        this(context, null);
    }

    public GlobalLoadingStatusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        setOrientation(VERTICAL);
//        setGravity(Gravity.CENTER_HORIZONTAL);
        View.inflate(context, R.layout.layout_global_loading_status, this);
        mImageView = findViewById(R.id.image);
        mTextView = findViewById(R.id.text);
    }

    public void setMsgViewVisibility(boolean visible) {
        mTextView.setVisibility(visible ? VISIBLE : GONE);
    }

    public void setRetryTask(Runnable mRetryTask) {
        this.mRetryTask = mRetryTask;
    }

    public void setStatus(int status, int icon, int str) {
        boolean show = true;
        View.OnClickListener onClickListener = null;
        int image = R.drawable.loading;
        int msg = R.string.str_none;
        switch (status) {
            case STATUS_LOAD_SUCCESS:
                show = false;
                break;
            case STATUS_LOADING:
                msg = R.string.loading;
                image = R.drawable.loading;
                break;
            case STATUS_LOAD_FAILED:
                msg = R.string.load_failed;
                image = R.mipmap.icon_failed;
                boolean networkConn = NetworkUtils.isConnected();
                if (!networkConn) {
                    msg = R.string.load_no_net;
                    image = R.mipmap.icon_no_wifi;
                }
                onClickListener = this;
                break;
            case STATUS_EMPTY_DATA:
                msg = R.string.empty;
                image = R.mipmap.icon_empty;
                onClickListener = this;
                break;
            default:
                break;
        }

        if (icon != 0) {
            image = icon;
        }
        if (str != 0) {
            msg = str;
        }
        mTextView.setText(msg);
        setOnClickListener(onClickListener);
        mImageView.setImageResource(image);
        setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (mRetryTask != null) {
            mRetryTask.run();
        }
    }
}