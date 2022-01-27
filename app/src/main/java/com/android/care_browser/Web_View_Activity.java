package com.android.care_browser;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Web_View_Activity extends AppCompatActivity {

    private ImageView searchbtnid;
    private EditText serachurl;
    private WebView webView;
    String urls;
    //    private ProgressDialog pd;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_web__view_);


        urls=getIntent ().getExtras ().get ("url_address").toString ();

        webView=findViewById (R.id.webViewID);
        swipeRefreshLayout=findViewById (R.id.swiperefreshid);
        progressBar=findViewById (R.id.progressbarid);

        webView.getSettings ().setJavaScriptEnabled (true);
        webView.getSettings ().setSupportZoom (false);
        webView.getSettings ().setDomStorageEnabled (true);
        webView.setWebViewClient (new myWebViewClient ());

        webView.setWebChromeClient (new MyChrome ());

        webView.loadUrl (urls);


        webView.setDownloadListener (new DownloadListener () {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                DownloadManager.Request myrequest=new DownloadManager.Request (Uri.parse (url));
                myrequest.allowScanningByMediaScanner ();
                myrequest.setNotificationVisibility (DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                DownloadManager myManager=(DownloadManager) getSystemService (DOWNLOAD_SERVICE);
                myManager.enqueue (myrequest);
                Toast.makeText (Web_View_Activity.this, "Downloading...", Toast.LENGTH_SHORT).show ();
            }
        });


        swipeRefreshLayout.setOnRefreshListener (new SwipeRefreshLayout.OnRefreshListener () {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing (true);
                new Handler ().postDelayed (new Runnable () {
                                                @Override
                                                public void run() {

                                                    swipeRefreshLayout.setRefreshing (false);
                                                    webView.loadUrl (urls);

                                                }
                                            }, 3000
                );
            }
        });
        swipeRefreshLayout.setColorSchemeColors (getResources ().getColor (android.R.color.holo_blue_bright), getResources ()
                .getColor (android.R.color.holo_orange_dark), getResources ().getColor (android.R.color.holo_green_dark), getResources ()
                .getColor (android.R.color.holo_red_dark));


    }


    public class myWebViewClient extends WebViewClient {
        @RequiresApi(api=Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {


            view.loadUrl (url);
            return true;

        }


        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Toast.makeText (Web_View_Activity.this, "No internet connection", Toast.LENGTH_SHORT).show ();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted (view, url, favicon);

            progressBar.setVisibility (View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished (view, url);

            progressBar.setVisibility (View.GONE);
        }
    }


    @Override
    public void onBackPressed() {

        if (webView.canGoBack ()) {
            webView.goBack ();
        } else {
            super.onBackPressed ();
//    }
        }
    }

    private class MyChrome extends WebChromeClient {

        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        MyChrome() {
        }

        public Bitmap getDefaultVideoPoster() {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource (getApplicationContext ().getResources (), 2130837573);
        }

        public void onHideCustomView() {
            ((FrameLayout) getWindow ().getDecorView ()).removeView (this.mCustomView);
            this.mCustomView=null;
            getWindow ().getDecorView ().setSystemUiVisibility (this.mOriginalSystemUiVisibility);
            setRequestedOrientation (this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden ();
            this.mCustomViewCallback=null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback) {
            if (this.mCustomView != null) {
                onHideCustomView ();
                return;
            }
            this.mCustomView=paramView;
            this.mOriginalSystemUiVisibility=getWindow ().getDecorView ().getSystemUiVisibility ();
            this.mOriginalOrientation=getRequestedOrientation ();
            this.mCustomViewCallback=paramCustomViewCallback;
            ((FrameLayout) getWindow ().getDecorView ()).addView (this.mCustomView, new FrameLayout.LayoutParams (-1, -1));
            getWindow ().getDecorView ().setSystemUiVisibility (3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState (outState);
        webView.saveState (outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState (savedInstanceState);
        webView.restoreState (savedInstanceState);
    }
}