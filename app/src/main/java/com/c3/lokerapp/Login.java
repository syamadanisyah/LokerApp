package com.c3.lokerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.c3.lokerapp.koneksi.RetrofitClient;
import com.c3.lokerapp.koneksi.UsersResponse;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {


    EditText user,pass;
    MaterialButton btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.txtusernamelog);
        pass = findViewById(R.id.txtpasswordlog);
        btnlogin = findViewById(R.id.btnlogin);


        btnlogin.setOnClickListener(v -> {

            RetrofitClient.getInstance().loginCaker(user.getText().toString(), pass.getText().toString())
                    .enqueue(new Callback<UsersResponse>() {
                        @Override
                        public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                            if(response.body() != null && response.body().getStatus().equalsIgnoreCase("success")){

                              Toast.makeText(getApplicationContext(), "Login berhasil", Toast.LENGTH_SHORT).show();


                                // jajalen login
                                startActivity(new Intent(Login.this, dashboard.class)); // iki ne sg salah
                            }else{
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UsersResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        });


    }
}