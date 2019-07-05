

package com.yzyfdf.library.view;


import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.progressindicator.AVLoadingIndicatorView;
import com.yzyfdf.library.R;

public class CustomProgressDialog extends Dialog {
    private Context context;
    private TextView mTvMsg;
    private String mMsg;
    private AVLoadingIndicatorView mIvLoading;

    public CustomProgressDialog(Context context, String msg) {
        super(context, R.style.CustomProgressDialog);
        this.context = context;
        mMsg = msg;
        initView(context);
    }

    public void initView(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.layout_loading_dialog, null);
        setContentView(root);

        mIvLoading = root.findViewById(R.id.iv_loading);
//        Glide.with(context).load(R.drawable.loading)
//                .apply(RequestOptions.overrideOf(SizeUtils.dp2px(60), SizeUtils.dp2px(60)))
//                .into(ivLoading);

        mTvMsg = findViewById(R.id.tv_msg);
        setMessage(mMsg);

        getWindow().getAttributes().gravity = Gravity.CENTER;
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    /**
     * setMessage 提示内容
     *
     * @param strMessage
     * @return
     */
    public CustomProgressDialog setMessage(String strMessage) {
        if (mTvMsg != null) {
            if (TextUtils.isEmpty(strMessage)) {
                mTvMsg.setVisibility(View.GONE);
            } else {
                mTvMsg.setText(strMessage);
                mTvMsg.setVisibility(View.VISIBLE);
            }
        }
        return this;
    }

    @Override
    public void show() {
        super.show();
        mIvLoading.smoothToShow();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mIvLoading.smoothToHide();
    }
}
