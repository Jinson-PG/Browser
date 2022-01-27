package com.android.care_browser;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class HomePage_Activity extends AppCompatActivity implements View.OnClickListener {


    private ImageView searchbtnid;
    private EditText serachurl, email;
    private LinearLayout googlelinear, binglinear, yahoolinear, duckducklinear, youtubelinear, faceboooklinear, instagramlinear, whatsapplinear, twitterlinear;
    //    private ProgressDialog pd;
    private String pEmail;
    private Button getbtn;
    private FirebaseFirestore dbRoot;
    private AdView mAdView;
    private WebView webView;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_home_page_);


        MobileAds.initialize (this, new OnInitializationCompleteListener () {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView=findViewById (R.id.adView);
        AdRequest adRequest=new AdRequest.Builder ().build ();
        mAdView.loadAd (adRequest);

//        pd=new ProgressDialog (this);


        googlelinear=findViewById (R.id.lineargoogle);

        email=findViewById (R.id.emailedit);
        getbtn=findViewById (R.id.getbtnid);


        binglinear=findViewById (R.id.linearbing);
        yahoolinear=findViewById (R.id.linearyahooid);
        duckducklinear=findViewById (R.id.linearduckduckgoid);
        youtubelinear=findViewById (R.id.linearyoutubeid);
        faceboooklinear=findViewById (R.id.linearfacebook);
        instagramlinear=findViewById (R.id.linearinstagramId);
        whatsapplinear=findViewById (R.id.linearwhatsappid);
        twitterlinear=findViewById (R.id.lineartwitter);
        serachurl=findViewById (R.id.urlsearchID);
        searchbtnid=findViewById (R.id.searchimageID);
        webView=findViewById (R.id.homeWbeviewid);
        progressBar=findViewById (R.id.progressbarssid);

        webView.getSettings ().setJavaScriptEnabled (true);
        webView.getSettings ().setSupportZoom (false);
        webView.getSettings ().setDomStorageEnabled (true);

        webView.setWebViewClient (new myWebViewClient ());
        webView.loadUrl ("https://carehelpers.blogspot.com/");


//
//        pd.setTitle ("Care Browser");
//        pd.setMessage ("Welcome to your privacy world...");
//        pd.show ();


        getbtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
//                pd.dismiss ();o

                Offerpage ();

            }
        });


        searchbtnid.setOnClickListener (this);
        googlelinear.setOnClickListener (this);
        binglinear.setOnClickListener (this);
        yahoolinear.setOnClickListener (this);
        duckducklinear.setOnClickListener (this);
        youtubelinear.setOnClickListener (this);
        faceboooklinear.setOnClickListener (this);
        instagramlinear.setOnClickListener (this);
        whatsapplinear.setOnClickListener (this);
        twitterlinear.setOnClickListener (this);


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
            Toast.makeText (HomePage_Activity.this, "No internet connection", Toast.LENGTH_SHORT).show ();
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


    private void Offerpage() {
        Map<String, String> items=new HashMap<> ();

        items.put ("Email", email.getText ().toString ().trim ());

        pEmail=email.getText ().toString ();


        if (TextUtils.isEmpty (pEmail)) {
            Toast.makeText (this, "Please enter email", Toast.LENGTH_SHORT).show ();
        } else {


            dbRoot=FirebaseFirestore.getInstance ();

            dbRoot.collection ("Emails").add (items).addOnCompleteListener (new OnCompleteListener<DocumentReference> () {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {

                    email.setText ("");

                    Toast.makeText (HomePage_Activity.this, "Registerd Successfully", Toast.LENGTH_SHORT).show ();
//                Intent intent=new Intent (HomePage_Activity.this,Sales_Activiy.class);
//                startActivity (intent);

                }


            });

        }
    }

    @Override
    public void onClick(View v) {
//
//        pd.setTitle ("Loading...");
//        pd.show ();

        if (v == searchbtnid) {

            Empty ();


        }
        if (v == googlelinear) {
            Intent google_View=new Intent (HomePage_Activity.this, Web_View_Activity.class);
            google_View.putExtra ("url_address", "https://www.google.com");
            startActivity (google_View);

        }
        if (v == binglinear) {
            Intent bing_view=new Intent (HomePage_Activity.this, Web_View_Activity.class);
            bing_view.putExtra ("url_address", "https://www.bing.com");

            startActivity (bing_view);

        }
        if (v == yahoolinear) {
            Intent yahoo_view=new Intent (HomePage_Activity.this, Web_View_Activity.class);
            yahoo_view.putExtra ("url_address", "https://www.yahoo.com");

            startActivity (yahoo_view);

        }
        if (v == duckducklinear) {
            Intent duck_view=new Intent (HomePage_Activity.this, Web_View_Activity.class);
            duck_view.putExtra ("url_address", "https://www.duckduckgo.com");

            startActivity (duck_view);

        }
        if (v == youtubelinear) {

            Intent youtube_view=new Intent (HomePage_Activity.this, Web_View_Activity.class);
            youtube_view.putExtra ("url_address", "https://www.youtube.com");

            startActivity (youtube_view);

        }
        if (v == faceboooklinear) {
            Intent facebook_view=new Intent (HomePage_Activity.this, Web_View_Activity.class);
            facebook_view.putExtra ("url_address", "https://www.facebook.com");

            startActivity (facebook_view);

        }
        if (v == instagramlinear) {
            Intent instagram_view=new Intent (HomePage_Activity.this, Web_View_Activity.class);
            instagram_view.putExtra ("url_address", "https://www.instagram.com");

            startActivity (instagram_view);

        }
        if (v == whatsapplinear) {
            Intent whatsapp_view=new Intent (HomePage_Activity.this, Web_View_Activity.class);
            whatsapp_view.putExtra ("url_address", "https://pinterest.com/");
//            pd.show ();
            startActivity (whatsapp_view);

        }
        if (v == twitterlinear) {
            Intent twitter_view=new Intent (HomePage_Activity.this, Web_View_Activity.class);
            twitter_view.putExtra ("url_address", "https://twitter.com");
//            pd.show ();
            startActivity (twitter_view);

        }

//        pd.cancel ();

    }

    private void Empty() {

//
//        pd.setTitle ("Loading...");
//        pd.setMessage ("Please wait...");
//        pd.show ();

        String edittext=serachurl.getText ().toString ();


        if (TextUtils.isEmpty (edittext)) {
            Toast.makeText (this, "Please enter valid URL", Toast.LENGTH_SHORT).show ();
        } else {
            String urlview=serachurl.getText ().toString ();
            Intent google_View=new Intent (HomePage_Activity.this, Web_View_Activity.class);
            google_View.putExtra ("url_address", "https://www." + urlview);

            serachurl.setText ("");
            serachurl.requestFocus ();
            startActivity (google_View);

        }
    }


}