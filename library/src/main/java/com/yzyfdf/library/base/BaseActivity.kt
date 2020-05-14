package com.yzyfdf.library.base


import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Resources
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.noober.background.BackgroundLibrary
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import com.yzyfdf.library.rx.RxManager
import com.yzyfdf.library.utils.TUtil
import com.yzyfdf.library.view.CustomProgressDialog

/**
 * 基类
 */
abstract class BaseActivity<T : BasePresenter<*, *>, E : BaseModel> : AppCompatActivity() {

    companion object {
        val TAG = BaseActivity::class.java.simpleName
    }

    var mPresenter: T? = null
    var mModel: E? = null
    lateinit var mRxManager: RxManager
    lateinit var mContext: Context
    protected lateinit var mResources: Resources
    private var dialog: CustomProgressDialog? = null

    //*********************子类实现*****************************

    /**
     * 获取布局文件
     */
    abstract val layoutId: Int

    public override fun onCreate(savedInstanceState: Bundle?) {
        //通过标签直接生成shape，无需再写shape.xml # https://github.com/JavaNoober/BackgroundLibrary
        BackgroundLibrary.inject(this)
        super.onCreate(savedInstanceState)
        mRxManager = RxManager()

        doBeforeSetcontentView()
        setContentView(layoutId)

        mContext = this
        mResources = resources

        mPresenter = TUtil.getT<T>(this, 0)
        mModel = TUtil.getT<E>(this, 1)
        if (mPresenter != null) {
            mPresenter!!.mContext = this
        }
        //        //浅色状态栏
        //        BarUtil.setWhiteStatus(this);

        this.initPresenter()
        this.initView()
        this.initListener()
        this.initData()
    }

    /**
     * 设置layout前配置
     */
    private fun doBeforeSetcontentView() {
        // 把actvity放到application栈中管理
        AppManager.instance.addActivity(this)
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        // 设置竖屏
        try {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } catch (e: Exception) {

        }
    }

    /**
     * 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
     */
    abstract fun initPresenter()

    /**
     * 初始化view
     */
    abstract fun initView()

    fun initListener() {}

    protected fun initData() {}

    /**
     * 开启浮动加载进度条
     *
     * @param msg
     */
    @JvmOverloads
    fun startProgressDialog(msg: String = "") {
        if (dialog == null) {
            dialog = CustomProgressDialog(this, msg)
        }
        dialog!!.show()
    }

    /**
     * 停止浮动加载进度条
     */
    fun stopProgressDialog() {
        dialog?.dismiss()
    }

    val isProgressLoading: Boolean
        get() = if (dialog == null) {
            false
        } else dialog!!.isShowing


    fun initToolbar(mToolbar: Toolbar, title: String) {
        this.setSupportActionBar(mToolbar)
        val bar = this.supportActionBar
        bar?.title = title
        bar?.setDisplayHomeAsUpEnabled(true)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
    }

    /**
     * 默认的titlebar 左边返回 中间title
     *
     * @param titleBar
     * @param title
     */
    fun initTitleBar(titleBar: CommonTitleBar, title: String) {
        titleBar.setListener { v, action, extra ->
            if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                onBackPressed()
            }
        }
        titleBar.centerTextView.text = title
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()

    }

    override fun onDestroy() {
        mPresenter?.onDestroy()
        super.onDestroy()
        AppManager.instance.removeActivity(this)
    }
}
