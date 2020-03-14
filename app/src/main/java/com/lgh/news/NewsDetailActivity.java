package com.lgh.news;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class NewsDetailActivity extends BaseActivity {

    private WebView webView;
    private ProgressBar progressBar;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        webView = findViewById(R.id.webview);
        progressBar = findViewById(R.id.webview_loading);
        button = findViewById(R.id.btn_text_size);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持JavaScript
        webSettings.setBuiltInZoomControls(true);//放大缩小，不支持已适配的页面
        webSettings.setUseWideViewPort(true);//双击缩放

        webView.setWebViewClient(new WebViewClient() {
            //开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
            }

            /**
             *  跳转
             * @param view this
             * @param request 跳转的链接
             * @return
             */
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                Log.e("12345678", "shouldOverrideUrlLoading: " + request.getUrl().toString());
                //所以跳转都在当前webview加载
                webView.loadUrl(request.getUrl().toString());
                return true;
            }

            //加载结束
            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {

            //标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            //进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });

        webView.loadUrl("https://www.baidu.com");

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();//webview返回

        } else {
            finish();
        }
////        跳转下一界面
//        if (webView.canGoForward()) {
//            webView.goForward();
//        }
    }

    private int choose;
    private int currentChoose = 1;

    public void judgeTextSize(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("字体设置");
        String[] items = new String[]{"1", "2", "3"};
        //单选 参1：数据 参2：默认选中的位置 参3：监听
        builder.setSingleChoiceItems(items, currentChoose, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choose = which;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WebSettings settings = webView.getSettings();
                switch (choose) {
                    case 0:
                        settings.setTextZoom(30);
                        break;
                    case 1:
                        settings.setTextZoom(20);
                        break;
                    case 2:
                        settings.setTextZoom(10);
                        break;
                }
                currentChoose = choose;
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();

    }

    public void share(View view) {
        OnekeyShare oks = new OnekeyShare(); //关闭sso授权 oks.disableSSOWhenAuthorize();
        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("分享");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网使用
        oks.setComment("我是测试评论文本");
       // 启动分享GUI
        oks.show(this);
    }
}
