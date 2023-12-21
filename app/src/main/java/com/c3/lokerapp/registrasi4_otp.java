package com.c3.lokerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.c3.lokerapp.koneksi.RetrofitClient;
import com.c3.lokerapp.koneksi.RetrofitEndPoint;
import com.c3.lokerapp.koneksi.UsersResponse;
import com.otpview.OTPListener;
import com.otpview.OTPTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class registrasi4_otp extends AppCompatActivity {

    public OTPTextView otpTextView;

    public static String OTP = "otp", USERNAME = "username", PASS = "pass", EMAIL = "email", FULLNAME = "nama_lengkap", NO_TELP = "no_telp", ALAMAT = "alamat";

    private String dataotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi4_otp);

        otpTextView = findViewById(R.id.votp_inp_otp);
        dataotp = getIntent().getStringExtra(OTP);

        //berfungsi untuk mengecek otp dan pindah ke registrasi pilih

        otpTextView.setOtpListener(new OTPListener() {


            @Override
            public void onInteractionListener() {

            }

            @Override
            public void onOTPComplete(@NonNull String s) {

                if (dataotp.equals(otpTextView.getOtp())) {
                    Toast.makeText(registrasi4_otp.this, "otp valid", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(registrasi4_otp.this, registrasi_kategori.class));
                }

            }

        });

    }
    public void onBackPressed(){

    }
}