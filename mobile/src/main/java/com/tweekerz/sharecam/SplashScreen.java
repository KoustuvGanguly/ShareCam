package com.tweekerz.sharecam;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;

/**
 * Created by Koustuv Ganguly on 03-Jun-17.
 */

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.splash_screen);
            ImageButton imageButton=(ImageButton)findViewById(R.id.imbt1);

            RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(2499);
            rotate.setInterpolator(new AccelerateInterpolator());
            imageButton.startAnimation(rotate);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        startActivity(new Intent(SplashScreen.this,LandingActivity.class));
                        finish();
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },2500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
