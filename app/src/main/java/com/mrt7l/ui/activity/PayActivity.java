package com.mrt7l.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mrt7l.R;
import com.mrt7l.helpers.DialogsHelper;

public class PayActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pay);
        DialogsHelper.showProgressDialog(this,getString(R.string.redirecting_to_pay));
        String url = getIntent().getStringExtra("url");
        FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        FrameLayout frameLayout = findViewById(R.id.webView);
        frameLayout.setLayoutParams(fl);
          webView = new WebView(this);
        webView.setScrollContainer(false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(params);
         setupWebView();
        frameLayout.addView(webView);
        webView.loadUrl(url);
        new Handler().postDelayed(DialogsHelper::removeProgressDialog, 5000);
    }
    private void setupWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
//                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//        webView.setLayoutParams(params);
//        webView.setScrollContainer(true);
        webView.setHorizontalScrollBarEnabled(true);
        webView.setVerticalScrollBarEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();

                // Check for specific URL pattern
                if (url.contains("tap_id")) {
                    returnResult(url);
                    return true; // Intercept the URL
                }
                return false; // Let WebView handle the URL
            }
        });
    }

    private void returnResult(String url) {
        Intent resultIntent = new Intent();

        // Extract data from URL
//        Uri uri = Uri.parse(url);
//        String resultData = uri.getQueryParameter("data");

        resultIntent.putExtra("result_data", "resultData");
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            setResult(RESULT_CANCELED);
            super.onBackPressed();
        }
    }
}