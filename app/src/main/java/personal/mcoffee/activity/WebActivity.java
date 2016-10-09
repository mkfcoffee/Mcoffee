package personal.mcoffee.activity;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import personal.mcoffee.R;

/**
 * Created by Mcoffee on 2016/10/8.
 * http://mp.weixin.qq.com/s?__biz=MzI3NDM3Mjg5NQ==&mid=2247483682&idx=1&sn=b1e03bfb789f75467c351a8ed7dfc156&scene=0#rd
 */

public class WebActivity extends AppCompatActivity {

    @BindView(R.id.webview_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.webview_container)
    WebView mWebView;
    @BindView(R.id.webview_progressbar)
    ProgressBar mProgressBar;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        initToolbar();
        url = getIntent().getStringExtra("url");
        initWebView(url);
        initWebSettings();
        initWebViewClient();
        initWebChromeClient();
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        mToolbar.setTitle("载入中...");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    /**
     * 初始化WebView
     *
     * @param url
     */
    private void initWebView(String url) {
        if(TextUtils.isEmpty(url)) return;
        mWebView.loadUrl(url);
    }

    /**
     * 初始化WebSettings
     */
    private void initWebSettings() {
        WebSettings webSettings = mWebView.getSettings();
        //支持获取手势焦点
        mWebView.requestFocusFromTouch();
        //支持js
        webSettings.setJavaScriptEnabled(true);
        //适应屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        //支持缩放
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);
        //支持内容重新布局
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.supportMultipleWindows();
        webSettings.setSupportMultipleWindows(true);
        //设置缓存模式
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(mWebView.getContext().getCacheDir().getAbsolutePath());
        //设置可访问文件
        webSettings.setAllowFileAccess(true);
        //当webView调用requestFocus时为webView设置节点
        webSettings.setNeedInitialFocus(true);
        //支持自动加载图片
        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setLoadsImagesAutomatically(true);
        } else {
            webSettings.setLoadsImagesAutomatically(true);
        }
        //设置编码格式
        webSettings.setDefaultTextEncodingName("UTF-8");
    }

    /**
     * 初始化WebViewClient
     */
    private void initWebViewClient() {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
            }
        });
    }

    /**
     * 初始化WebChromeClient
     */
    private void initWebChromeClient() {
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setToolbarTitle(title);
            }
        });
    }

    /**
     * 设置Toolbar标题
     *
     * @param title
     */
    private void setToolbarTitle(final String title) {
        if (mToolbar != null) {
            mToolbar.post(new Runnable() {
                @Override
                public void run() {
                    mToolbar.setTitle(TextUtils.isEmpty(title) ? getString(R.string.loading) : title);
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
