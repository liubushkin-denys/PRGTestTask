package prg.testtask;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;

public class WebViewFragment extends Fragment {
    String link;
    View v;
    public WebView mWebView;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mWebView.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    public WebViewFragment() {
        super(R.layout.fragment_web_view);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        v = inflater.inflate(R.layout.fragment_web_view, container, false);
        SharedPreferences mPrefs = v.getContext().getSharedPreferences("link", 0);
        link = mPrefs.getString("link", "google.com");
        mWebView = v.findViewById(R.id.webview);
        mWebView.loadUrl(link);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                SharedPreferences.Editor mEditor = mPrefs.edit();
                link = url;
                mEditor.putString("link", link).apply();
            }
        });
        return v;
    }
}