package com.yzyfdf.libsample.ui

import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.yzyfdf.library.base.BaseActivity
import com.yzyfdf.library.base.BaseModel
import com.yzyfdf.library.base.BasePresenter
import com.yzyfdf.libsample.R
import kotlinx.android.synthetic.main.activity_base_web.*

/**
 * @author sjj , 2019/4/24 13:40
 * //嵌套网页
 */
class BaseWebActivity : BaseActivity<BasePresenter<*, *>, BaseModel>() {

    companion object {
        fun go(context: Context, url: String) {
            context.startActivity(Intent(context, BaseWebActivity::class.java)
                    .putExtra("url", url))
        }
    }

    protected lateinit var mPreAgentWeb: AgentWeb.PreAgentWeb
    protected var mAgentWeb: AgentWeb? = null

    protected var mWebViewClient: WebViewClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }
    }

    protected var mWebChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            base_web_titlebar.centerTextView.text = title
        }
    }


    override val layoutId: Int
        get() = R.layout.activity_base_web


    override fun initPresenter() {

    }

    override fun initView() {

        mPreAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(base_web_content, LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(ContextCompat.getColor(this, R.color.colorPrimary))
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK) //是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()

        val url = intent.getStringExtra("url")
        mAgentWeb = mPreAgentWeb.go(url)

        val webSettings = mAgentWeb!!.agentWebSettings.webSettings
        //        webSettings.setUserAgentString("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
        webSettings.useWideViewPort = true
        webSettings.setSupportZoom(true)
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false
        //        webSettings.setLoadWithOverviewMode(true);

        init()
    }

    protected fun init() {}


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (mAgentWeb != null && mAgentWeb!!.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        if (mAgentWeb == null) {
            super.onBackPressed()
            return
        }
        if (!mAgentWeb!!.back()) {
            super.onBackPressed()
        }
    }

    override fun onPause() {
        if (mAgentWeb != null) {
            mAgentWeb!!.webLifeCycle.onPause()
        }
        super.onPause()

    }

    override fun onResume() {
        if (mAgentWeb != null) {
            mAgentWeb!!.webLifeCycle.onResume()
        }
        super.onResume()
    }

    public override fun onDestroy() {
        if (mAgentWeb != null) {
            mAgentWeb!!.webLifeCycle.onDestroy()
        }
        super.onDestroy()
    }

}
