package com.yzyfdf.library.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.billy.android.loading.Gloading;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.yzyfdf.library.R;
import com.yzyfdf.library.rx.RxManager;
import com.yzyfdf.library.utils.TUtil;
import com.yzyfdf.library.view.CustomProgressDialog;

import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public abstract class BaseFragment<T extends BasePresenter, E extends BaseModel> extends Fragment {
    protected View rootView;
    public T mPresenter;
    public E mModel;
    public RxManager mRxManager;

    private CustomProgressDialog dialog;
    public Context mContext;
    protected Gloading.Holder mHolder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutResource(), container, false);
            mHolder = Gloading.getDefault().wrap(rootView).withRetry(new Runnable() {
                @Override
                public void run() {
                    retry();
                }
            });
        }
        mRxManager = new RxManager();
        ButterKnife.bind(this, rootView);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this.getActivity();
        }
        mContext = getContext();
        initPresenter();
        initView(savedInstanceState);
        return mHolder.getWrapper();
//        return rootView;
    }

    //获取布局文件
    protected abstract int getLayoutResource();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    //初始化view
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 页面加载失败重试，显示加载中，子类继承
     */
    protected void retry() {
        mHolder.showLoading();
    }

    /**
     * fragment被重复选择
     */
    public void onPagerReSelected() {

    }


    /**
     * 开启加载进度条
     */
    public void startProgressDialog() {
        startProgressDialog("");
    }

    /**
     * 开启加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(String msg) {
        if (dialog == null) {
            dialog = new CustomProgressDialog(mContext, msg);
        }
        dialog.show();
    }

    /**
     * 停止加载进度条
     */
    public void stopProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public boolean isProgressLoading() {
        if (dialog == null) {
            return false;
        }
        return dialog.isShowing();
    }

    /**
     * 默认样式的LuRecyclerView
     *
     * @param mRecyclerView
     * @param layoutManager
     */
    protected void initLuRecyclerView(LuRecyclerView mRecyclerView, RecyclerView.LayoutManager layoutManager) {
//        mRecyclerView.setFooterViewColor(R.color.text_color2, R.color.text_color2, R.color.white);
        mRecyclerView.setFooterViewHint(getResources().getString(R.string.loading),
                getResources().getString(R.string.load_no_more),
                getResources().getString(R.string.load_failed));
        mRecyclerView.setLayoutManager(layoutManager);
    }


    /**
     * 短暂显示Toast提示(来自String)
     **/
    public void showShortToast(String text) {
        Toasty.normal(mContext, text).show();
    }

    /**
     * 短暂显示Toast提示(id)
     **/
    public void showShortToast(int resId) {
        Toasty.normal(mContext, resId).show();
    }

    /**
     * 长提示
     **/
    public void showLongToast(String text) {
        Toasty.normal(mContext, text, Toasty.LENGTH_LONG).show();
    }

    /**
     * 错误提示
     */
    public void showErrTip(String msg) {
        Toasty.error(mContext, msg).show();
    }

    /**
     * 操作成功提示
     */
    public void showSuccessTip(String msg) {
        Toasty.success(mContext, msg).show();
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null)
            mPresenter.onDestroy();
        if (mRxManager != null) {
            mRxManager.clear();
        }
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
