package com.c3.lokerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.c3.lokerapp.shared.DataShared;
import com.c3.lokerapp.util.UserUtil;

public class splashScreen extends AppCompatActivity {


    private void cekLogin(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("prefLogin", Context.MODE_PRIVATE);
        String id_pelamar = sharedPreferences.getString("id_pelamar", "");
        String nama_lengkap = sharedPreferences.getString("nama_lengkap", "");

        if (!id_pelamar.isEmpty()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Pindah ke activity berikutnya setelah tampilan splash selesai
                    Intent mainIntent = new Intent(splashScreen.this, dashboard.class);
                    startActivity(mainIntent);
                    finish();
                    Toast.makeText(splashScreen.this, "Anda Masuk Sebagai\n "+nama_lengkap, Toast.LENGTH_SHORT).show();

                }
            }, 3000);

        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Pindah ke activity berikutnya setelah tampilan splash selesai
                    Intent mainIntent = new Intent(splashScreen.this, Login.class);
                    startActivity(mainIntent);
                    finish();

                }
            }, 3000);
        }


    }



    private static final int SPLASH_SCREEN_DURATION = 1500; // 1,5 detik
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // inisialisi user util untuk mendapatkan data local
        UserUtil util = new UserUtil(this);

        // Membuat Handler untuk mengatur delay dan berpindah ke aktivitas utama
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cekLogin();
//                // cek sudah login atau belum
//                if (util.isSignIn()){
//                    // jika sudah login
//                    Intent intent = new Intent(splashScreen.this, dashboard.class);
//                    startActivity(intent);
//                    finish();
//                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                }else {
//                    // jika belum login
//                    Intent intent = new Intent(splashScreen.this, Login.class);
//                    startActivity(intent);
//                    finish();
//                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                }


            }
        }, SPLASH_SCREEN_DURATION);





    }
}