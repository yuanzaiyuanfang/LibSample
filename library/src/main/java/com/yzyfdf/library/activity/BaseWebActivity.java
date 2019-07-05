package com.yzyfdf.library.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.yzyfdf.library.R;
import com.yzyfdf.library.base.BaseActivity;

/**
 * @author sjj , 2019/4/24 13:40
 * //嵌套网页
 */
public abstract class BaseWebActivity extends BaseActivity {

    protected CommonTitleBar mTitlebar;
    protected LinearLayout mContent;

    protected AgentWeb.PreAgentWeb mPreAgentWeb;
    protected AgentWeb mAgentWeb;// = mPreAgentWeb.go(url);子类自行实现

    protected WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }
    };

    protected WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            mTitlebar.getCenterTextView().setText(title);
        }
    };


    @Override
    public int getLayoutId() {
        return R.layout.activity_base_web;
    }

    @Override
    public void bindView() {
        super.bindView();
        mTitlebar = findViewById(R.id.base_web_titlebar);
        mContent = findViewById(R.id.base_web_content);

        mTitlebar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                    finish();
                }
            }
        });
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mPreAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mContent, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(ContextCompat.getColor(this, R.color.colorPrimary))
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready();

        init();
    }

    protected abstract void init();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb != null && mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (mAgentWeb == null) {
            super.onBackPressed();
            return;
        }
        if (!mAgentWeb.back()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();

    }

    @Override
    protected void onResume() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
        super.onDestroy();
    }

}
