package com.android.care_browser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Animation topanimation, bottomanimation;
    ImageView imageView;
    TextView textView, textViews;
    private static int SPLASH_SCREEN=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView (R.layout.activity_main);

        topanimation=AnimationUtils.loadAnimation (this, R.anim.top_animation);
        bottomanimation=AnimationUtils.loadAnimation (this, R.anim.bottom_animation);

        imageView=findViewById (R.id.imageview);
        textView=findViewById (R.id.textviewq);
        imageView.setAnimation (topanimation);
        textView.setAnimation (bottomanimation);
        textViews=findViewById (R.id.textviewq);
        textViews.setAnimation (bottomanimation);

        new Handler ().postDelayed (new Runnable () {
            @Override
            public void run() {
                Intent intent=new Intent (MainActivity.this, HomePage_Activity.class);
                startActivity (intent);
                finish ();
            }
        }, SPLASH_SCREEN);


    }
}