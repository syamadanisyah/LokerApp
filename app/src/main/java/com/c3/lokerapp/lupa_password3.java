package com.c3.lokerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.c3.lokerapp.koneksi.RetrofitClient;
import com.c3.lokerapp.koneksi.UsersResponse;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class lupa_password3 extends AppCompatActivity {


    public static String EMAIL = "email";
    private String dataemail;


    EditText sandibaru,konfirmasisandi;
    MaterialButton btn_simpan;

    private String dataEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password3);

        dataEmail = getIntent().getStringExtra("email");

        sandibaru = findViewById(R.id.txtsandibaru);
        konfirmasisandi = findViewById(R.id.txtkonfirmasisandi);
        btn_simpan = findViewById(R.id.btnlp3);


        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sandibaru.getText().toString().equals(konfirmasisandi.getText().toString())){
                    RetrofitClient.getInstance().updatePassword(dataEmail, sandibaru.getText().toString())
                            .enqueue(new Callback<UsersResponse>() {
                                @Override
                                public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                                    if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {
                                        startActivity(
                                                new Intent(lupa_password3.this, Login.class)

                                        );

                                    }else {
                                        Toast.makeText(lupa_password3.this,"gagal",Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<UsersResponse> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });


                }else {
                    Toast.makeText(lupa_password3.this, "Sandi Tidak Cocok", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}