package cn.timeface.timekit.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import cn.timeface.timekit.R;
import cn.timeface.timekit.activity.TfBaseActivity;

/**
 * Created by zhangsheng on 2017/9/17.
 */

public class TfWebViewActivity extends TfBaseActivity {
    public static void start(Context context, String title, String url) {
        Intent starter = new Intent(context, TfWebViewActivity.class);
        starter.putExtra("title", title);
        starter.putExtra("url", url);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_webview);
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        setTitle(title);
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl(url);
    }
}
