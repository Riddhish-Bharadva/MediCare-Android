package com.rab.medicare;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PaymentWebView extends AppCompatActivity {
    WebView webView;
    String PageDecision;
    LoggedInDecision LID = new LoggedInDecision();
    String Link = null;
    String WebLink = "https://medicare-287205.nw.r.appspot.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_web_view);

        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        WebSettings WS = webView.getSettings();
        WS.setJavaScriptEnabled(true);
        WS.setDomStorageEnabled(true);

        WebStorage.getInstance().deleteAllData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        }
        else
        {
            CookieSyncManager cookieSyncMngr=CookieSyncManager.createInstance(this);
            cookieSyncMngr.startSync();
            CookieManager cookieManager= CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }

        webView.clearCache(true);
        webView.clearFormData();
        webView.clearHistory();
        webView.clearSslPreferences();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            PageDecision = bundle.getString("Function");
            if(PageDecision.compareTo("MakePayment") == 0){
                String OrderID = bundle.getString("OrderID");
                Link = WebLink+"/WebView?Function=MakePayment&SignInAs=User&userEmail="+LID.getLoginStatus()+"&OrderID="+OrderID;
            }
            else {
                Link = WebLink+"/WebView?Function=ShoppingCart&userEmail="+LID.getLoginStatus();
            }
            System.out.println("Link is : "+Link);
            webView.loadUrl(Link);
        }
    }

    @Override
    public void onBackPressed(){
        if(webView.canGoBack()) {
            webView.goBack();
        }
        else{
            super.onBackPressed();
        }
    }
}