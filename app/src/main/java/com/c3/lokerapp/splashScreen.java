package com.c3.lokerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splashScreen extends AppCompatActivity {

    private static final int SPLASH_SCREEN_DURATION = 1500; // 1,5 detik
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Membuat Handler untuk mengatur delay dan berpindah ke aktivitas utama
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // Membuat intent untuk memulai aktivitas utama
                Intent intent = new Intent(splashScreen.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN_DURATION);





    }
}