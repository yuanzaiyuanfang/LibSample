package com.yzyfdf.library.rx;

import android.content.Context;

import com.blankj.utilcode.util.NetworkUtils;
import com.yzyfdf.library.R;
import com.yzyfdf.library.base.BaseApplication;
import com.yzyfdf.library.view.CustomProgressDialog;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;


public abstract class BaseRxSubscriber<T> implements Observer<T> {

    protected Context mContext;
    private RxManager mRxManager;
    private   boolean   showDialog = true;
    private String msg;

    private CustomProgressDialog dialog;

    /**
     * 不需要显示进度框
     */
    public BaseRxSubscriber(RxManager rxManager) {
        this(null, rxManager, false);
    }

    /**
     * 显示进度框 无文字
     */
    public BaseRxSubscriber(Context context, RxManager rxManager, boolean showDialog) {
        this(context, rxManager, showDialog, "");
    }


    /**
     * 显示进度框 文字
     */
    public BaseRxSubscriber(Context context, RxManager rxManager, boolean showDialog, String msg) {
        this.mContext = context;
        this.mRxManager = rxManager;
        this.showDialog = showDialog;
        this.msg = msg;
    }


    @Override
    public void onSubscribe(Disposable d) {
        if (showDialog) {
            if (dialog == null) {
                dialog = new CustomProgressDialog(mContext, msg);
            }
            dialog.show();
        }
        if (mRxManager != null) {
            mRxManager.add(d);
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }


    @Override
    public void onComplete() {
        if (showDialog) {
            if (dialog != null) {
                dialog.dismiss();
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        if (showDialog) {
            if (dialog != null) {
                dialog.dismiss();
            }
        }
        e.printStackTrace();
        //网络
        if (!NetworkUtils.isConnected()) {
            _onError(BaseApplication.getAppContext().getString(R.string.load_no_net));
        }
        //服务器
        else if (e instanceof HttpException) {
            //统一异常处理 自己重写方法
            servicesError(((HttpException) e));
        }
        //其它
        else {
            _onError(BaseApplication.getAppContext().getString(R.string.net_error));
        }
    }

    protected abstract void servicesError(HttpException e);

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);

}