package com.c3.lokerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.c3.lokerapp.koneksi.RetrofitClient;
import com.google.android.material.button.MaterialButton;
import com.otpview.OTPListener;
import com.otpview.OTPTextView;

public class lupa_password2 extends AppCompatActivity {

    OTPTextView otp;
    MaterialButton btnotp;

    public static String EMAIL = "email", OTP = "otp";
    private String dataemail;
    private String dataotp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password2);
        dataemail = getIntent().getStringExtra(EMAIL);
        dataotp = getIntent().getStringExtra(OTP);


        otp = findViewById(R.id.votp_inp_otp);
        btnotp = findViewById(R.id.btnlp2otp);

        otp.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {


            }

            @Override
            public void onOTPComplete(@NonNull String s) {
                if (otp.getOtp().toString().equals(dataotp)) {
                    startActivity(
                            new Intent(lupa_password2.this, lupa_password3.class)
                                    .putExtra(EMAIL,dataemail)
                    );

                }else {
                    Toast.makeText(lupa_password2.this, "OTP salah", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}