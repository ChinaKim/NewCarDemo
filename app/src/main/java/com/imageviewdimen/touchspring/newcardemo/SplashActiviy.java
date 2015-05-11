package com.imageviewdimen.touchspring.newcardemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by KIM on 2015/5/11 0011.
 */
public class SplashActiviy extends ActionBarActivity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,WindowManager.LayoutParams. FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mIntent = new Intent(SplashActiviy.this, MainActivity.class);
                //动画
                //Animation mAnimation = AnimationUtils.loadAnimation(SplashActiviy.this,R.anim.main_activity_anim);
                startActivity(mIntent);
                overridePendingTransition(R.anim.main_activity_anim, 0);
            }
        }, 3000);

    }
}
