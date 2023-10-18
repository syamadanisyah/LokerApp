package com.c3.lokerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.c3.lokerapp.koneksi.RetrofitClient;
import com.c3.lokerapp.koneksi.UsersResponse;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class registrasi extends AppCompatActivity {


    MaterialButton btn;
    EditText user,passw,nama,noTelp,alamat,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        user = findViewById(R.id.txtusername);
        passw = findViewById(R.id.txtpassword);
        nama = findViewById(R.id.txtnama_lengkap);
        noTelp = findViewById(R.id.txtno_telp);
        alamat = findViewById(R.id.txtalamat);
        email = findViewById(R.id.txtemail);


        btn = findViewById(R.id.btnregis);

        btn.setOnClickListener(v -> {
            RetrofitClient.getInstance().registerCaker(
                    "id34", user.getText().toString(), passw.getText().toString(), nama.getText().toString(),
                    noTelp.getText().toString(), alamat.getText().toString(),email.getText().toString()
            ).enqueue(new Callback<UsersResponse>() {
                @Override
                public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                    if(response.body() != null && response.body().getStatus().equalsIgnoreCase("success")){
                        Toast.makeText(getApplicationContext(), "Register berhasil", Toast.LENGTH_SHORT).show();

                        // jajalen register terus login oke

                        startActivity(new Intent(registrasi.this, Login.class)); // iki ne sg salah
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