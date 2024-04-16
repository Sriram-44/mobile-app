package com.example.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Start Lottie animation
        LottieAnimationView animationView = findViewById(R.id.lottieAnimationView);
        animationView.setAnimation(R.raw.splashani);
        animationView.playAnimation();


        // Wait for 3 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start login activity
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000); // 3000 milliseconds = 3 seconds
    }
}
