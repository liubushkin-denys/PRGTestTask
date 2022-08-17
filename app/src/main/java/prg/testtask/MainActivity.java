package prg.testtask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;
import com.appsflyer.AppsFlyerLib;



public class MainActivity extends AppCompatActivity {
    private static final AppsFlyerLib AF_DEV_KEY = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //AppsFlyerLib.getInstance().init(<AF_DEV_KEY>, null, this);
        //AppsFlyerLib.getInstance().start(this);
        SharedPreferences mPrefs = getSharedPreferences("label", 0);
        Boolean agreedToPrivacyPolicy = mPrefs.getBoolean("agreed", false);
        super.onCreate(savedInstanceState);
        if (agreedToPrivacyPolicy == false) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, StartFragment.class, null)
                    .commit();
        }
        else {
            launchWebView();
        }
        setContentView(R.layout.activity_main);
    }

    public void exit(View view) {
        finish();
    }

    public void agree(View view) {
        SharedPreferences mPrefs = getSharedPreferences("label", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putBoolean("agreed", true).commit();
        launchWebView();
    }

    public void launchWebView() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, WebViewFragment.class, null)
                    .setReorderingAllowed(true)
                    .commit();
        }
        else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, NoInternetFragment.class, null)
                    .setReorderingAllowed(true)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        WebView mWebView = (WebView) findViewById(R.id.webview);
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        }
        else {
            super.onBackPressed();
        }
    }
}