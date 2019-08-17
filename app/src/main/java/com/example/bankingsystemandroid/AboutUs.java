package com.example.bankingsystemandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AboutUs extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        webView=findViewById(R.id.web);
        WebViewClient mWebViewClient = new WebViewClient();


        webView.setWebViewClient(mWebViewClient);
        webView.loadUrl("https://prometheusbox.com/index2.html");
    }
}
