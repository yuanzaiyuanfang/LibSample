package com.yzyfdf.library.base;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import com.blankj.utilcode.util.ToastUtils;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.noober.background.BackgroundLibrary;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.yzyfdf.library.R;
import com.yzyfdf.library.rx.RxManager;
import com.yzyfdf.library.utils.TUtil;
import com.yzyfdf.library.view.CustomProgressDialog;

import butterknife.ButterKnife;

/**
 * 基类
 */
public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel> extends AppCompatActivity /*implements BGASwipeBackHelper.Delegate*/ {
    public T mPresenter;
    public E mModel;
    public Context mContext;
    protected Resources mResources;
    public RxManager mRxManager;
    private CustomProgressDialog dialog;

//    private BGASwipeBackHelper mSwipeBackHelper;

    public static final String TAG = BaseActivity.class.getSimpleName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        //通过标签直接生成shape，无需再写shape.xml # https://github.com/JavaNoober/BackgroundLibrary
        BackgroundLibrary.inject(this);
        super.onCreate(savedInstanceState);
        mRxManager = new RxManager();

        doBeforeSetcontentView();
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        mContext = this;
        mResources = getResources();
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this;
        }
//        //浅色状态栏
//        BarUtil.setWhiteStatus(this);

        this.bindView();
        this.initPresenter();
        this.initView(savedInstanceState);
    }

    /**
     * 设置layout前配置
     */
    private void doBeforeSetcontentView() {
        // 把actvity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回」
        initSwipeBackFinish();
    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
//        mSwipeBackHelper = new BGASwipeBackHelper(this, this);
//
//        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回」
//        // 下面几项可以不配置，这里只是为了讲述接口用法。
//
//        // 设置滑动返回是否可用。默认值为 true
//        mSwipeBackHelper.setSwipeBackEnable(true);
//        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
//        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
//        // 设置是否是微信滑动返回样式。默认值为 true
//        mSwipeBackHelper.setIsWeChatStyle(true);
//        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
//        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
//        // 设置是否显示滑动返回的阴影效果。默认值为 true
//        mSwipeBackHelper.setIsNeedShowShadow(true);
//        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
//        mSwipeBackHelper.setIsShadowAlphaGradient(true);
//        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
//        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
//        // 设置底部导航条是否悬浮在内容上，默认值为 false
//        mSwipeBackHelper.setIsNavigationBarOverlap(false);
    }

//    /**
//     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
//     *
//     * @return
//     */
//    @Override
//    public boolean isSupportSwipeBack() {
//        return true;
//    }
//
//    /**
//     * 正在滑动返回
//     *
//     * @param slideOffset 从 0 到 1
//     */
//    @Override
//    public void onSwipeBackLayoutSlide(float slideOffset) {
//
//    }
//
//    /**
//     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
//     */
//    @Override
//    public void onSwipeBackLayoutCancel() {
//
//    }
//
//    /**
//     * 滑动返回执行完毕，销毁当前 Activity
//     */
//    @Override
//    public void onSwipeBackLayoutExecuted() {
//        mSwipeBackHelper.swipeBackward();
//    }
//
//
//    @Override
//    public void onBackPressed() {
//        // 正在滑动返回的时候取消返回按钮事件
//        if (mSwipeBackHelper.isSliding()) {
//            return;
//        }
//        mSwipeBackHelper.backward();
//    }


    //*********************子类实现*****************************

    /**
     * 获取布局文件
     */
    public abstract int getLayoutId();


    /**
     * 手动绑定控件
     */
    public void bindView() {
    }

    /**
     * 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
     */
    public abstract void initPresenter();

    /**
     * 初始化view
     */
    //    public abstract void initView();
    public abstract void initView(Bundle savedInstanceState);
    //*********************子类实现*****************************


    /**
     * 开启浮动加载进度条
     */
    public void startProgressDialog() {
        startProgressDialog("");
    }

    /**
     * 开启浮动加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(String msg) {
        if (dialog == null) {
            dialog = new CustomProgressDialog(this, msg);
        }
        dialog.show();
    }

    /**
     * 停止浮动加载进度条
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
     * 短暂显示Toast提示(来自String)
     **/
    public void showShortToast(String text) {
        ToastUtils.showShort(text);
    }

    /**
     * 短暂显示Toast提示(id)
     **/
    public void showShortToast(int resId) {
        ToastUtils.showShort(resId);
    }

    /**
     * 长时间显示Toast提示(来自res)
     **/
    public void showLongToast(int resId) {
        ToastUtils.showLong(resId);
    }

    /**
     * 长时间显示Toast提示(来自String)
     **/
    public void showLongToast(String text) {
        ToastUtils.showLong(text);
    }


    public void initToolbar(Toolbar mToolbar, String title) {
        this.setSupportActionBar(mToolbar);
        ActionBar bar = this.getSupportActionBar();
        bar.setTitle(title);
        bar.setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 默认的titlebar 左边返回 中间title
     *
     * @param titleBar
     * @param title
     */
    public void initTitleBar(CommonTitleBar titleBar, String title) {
        titleBar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                    finish();
                }
            }
        });
        titleBar.getCenterTextView().setText(title);
    }

    /**
     * 默认样式的LuRecyclerView
     *
     * @param mRecyclerView
     * @param layoutManager
     */
    protected void initLuRecyclerView(LuRecyclerView mRecyclerView, RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setFooterViewColor(R.color.text_color2, R.color.text_color2, R.color.white);
        mRecyclerView.setFooterViewHint(getResources().getString(R.string.loading),
                getResources().getString(R.string.load_no_more),
                getResources().getString(R.string.load_failed));
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null)
            mPresenter.onDestroy();
        if (mRxManager != null) {
            mRxManager.clear();
        }
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        ButterKnife.unbind(this);
    }

}
