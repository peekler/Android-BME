package demo.android.app.hu.webviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


public class MainActivity extends Activity {

    private WebView webView;
    private ProgressBar progressBarWebLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBarWebLoad = (ProgressBar) findViewById(R.id.progressBarWebLoad);
        progressBarWebLoad.setMax(100);

        webView = (WebView) findViewById(R.id.webViewContent);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                loadSite(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBarWebLoad.setProgress(100);
                progressBarWebLoad.setVisibility(View.GONE);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBarWebLoad.setProgress(newProgress);
            }
        });

        webView.getSettings().setBuiltInZoomControls(true);

        loadSite("http://m.hwsw.hu");
    }

    private void loadSite(String url) {
        progressBarWebLoad.setVisibility(View.VISIBLE);
        progressBarWebLoad.setProgress(0);
        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
