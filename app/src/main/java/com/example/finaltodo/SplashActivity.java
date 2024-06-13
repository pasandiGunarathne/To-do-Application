package com.example.finaltodo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    Animation topAnime, bottomAnime, scaleUp, fadeIn;
    ImageView gCircle, d_it, tick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        topAnime = AnimationUtils.loadAnimation(this, R.anim.move_top_to_center);
        bottomAnime = AnimationUtils.loadAnimation(this, R.anim.move_bottom_to_center);
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        gCircle = findViewById(R.id.topCircleImage);
        d_it = findViewById(R.id.bottomImage);
        tick = findViewById(R.id.tickImage);

        tick.setVisibility(View.VISIBLE);

        gCircle.setVisibility(View.VISIBLE);
        d_it.setVisibility(View.VISIBLE);

        gCircle.startAnimation(topAnime);
        d_it.startAnimation(bottomAnime);

        topAnime.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Do nothing
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tick.setAnimation(fadeIn);
                gCircle.startAnimation(scaleUp);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Do nothing
            }
        });

        scaleUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Do nothing
            }


            @Override
            public void onAnimationEnd(Animation animation) {
                // Start the next activity after the animation
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Do nothing
            }
        });
    }
}

