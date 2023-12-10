package com.c3.lokerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.c3.lokerapp.koneksi.RetrofitClient;
import com.c3.lokerapp.koneksi.VerifyResponse;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class lupa_password1 extends AppCompatActivity {


    MaterialButton btnlp1;

    EditText txtMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password1);

        btnlp1 = findViewById(R.id.btnlp1mail);
        txtMail = findViewById(R.id.txtlp1email);

        btnlp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitClient.getInstance().sendEmail(txtMail.getText().toString(), "ForgotPass", "new").enqueue(new Callback<VerifyResponse>() {
                    @Override
                    public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                        if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {

                            startActivity(
                                    new Intent(lupa_password1.this, lupa_password2.class)
                                            .putExtra(lupa_password2.EMAIL,txtMail.getText().toString())
                                            .putExtra(lupa_password2.OTP,response.body().getData().getOtp())

                            );

                        } else {
                            Toast.makeText(lupa_password1.this, "OTP gagal di kirim", Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onFailure(Call<VerifyResponse> call, Throwable t) {
                        Toast.makeText(lupa_password1.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


    }
}