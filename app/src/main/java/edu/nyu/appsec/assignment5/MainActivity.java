package edu.nyu.appsec.assignment5;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    //private static final String SPELL_CHECK_URL = "http://appsecclass.report:8080/";
    //private static final String SPELL_CHECK_URL = "http://127.0.0.1:8080/";
    //private static final String KNOWN_HOST = "appsecclass.report";
    //private static final String KNOWN_HOST = "127.0.0.1";
    // Kubernetes cluster URL
    // for some reason this works but just using a docker container doesn't
    private static final String SPELL_CHECK_URL = "http://192.168.99.100:31966/";
    private static final String KNOWN_HOST = "192.168.99.100";

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = String.valueOf(request.getUrl());
            String host = Uri.parse(url).getHost();

            if (KNOWN_HOST.equals(host)) {
                return false;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView view = new WebView(this);
        view.setWebViewClient(new MyWebViewClient());

        WebSettings settings = view.getSettings();
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setJavaScriptEnabled(true);
        settings.setAllowUniversalAccessFromFileURLs(true);

        setContentView(view);
        view.loadUrl(SPELL_CHECK_URL + "register");
    }
}
