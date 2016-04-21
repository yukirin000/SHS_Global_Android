package com.shs.global.ui.activity;

import android.webkit.WebSettings;
import android.webkit.WebView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.shs.global.R;

/**
 * Created by wenhai on 2016/4/19.
 */
public class AboutUsActivity extends BaseActivityWithTopBar {
    @ViewInject(R.id.about_web)
    private WebView webView;
    @Override
    public int setLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void setUpView() {
        setBarText("关于我们");
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl("http://c.eqxiu.com/s/27ULUB5f");
    }
}
