package com.example.asean_shipping;

import android.content.res.AssetManager;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.asean_shipping.R;
import com.example.asean_shipping.utils.AppConstants;

public class BOLActivity extends AppCompatActivity {
    private WebView webView;
    String shipmentId;


    @Override
    public AssetManager getAssets() {
        return getResources().getAssets();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bol_webview_layout);

        shipmentId = getIntent().getExtras().getString("shipmentId");
        String url = AppConstants.BASE_URL+"transaction/html_bol/"+shipmentId+"/";

        getBill(url);
        webView.setVisibility(View.VISIBLE);
    }

//    @Override
//    public void onBackPressed() {
//        onBackPress();
//    }

    private void getBill(String url) {
        webView = findViewById(R.id.bol_webview);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });

        webView.loadUrl(url);

    }

//    private void onBackPress() {
//        Intent intent = new Intent(ActivityTutorial.this, ActivityFarmer.class);
//        ActivityTutorial.this.startActivity(intent);
//        ActivityTutorial.this.finish();
//
//        super.onBackPressed();
//        }
//    }
}
