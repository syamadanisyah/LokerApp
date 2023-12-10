package com.c3.lokerapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.c3.lokerapp.Home.google.GoogleUsers;
import com.c3.lokerapp.koneksi.RetrofitClient;
import com.c3.lokerapp.koneksi.UserModel;
import com.c3.lokerapp.koneksi.UsersResponse;
import com.c3.lokerapp.util.UserUtil;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {


    EditText user, pass;
    MaterialButton btnlogin;

    private GoogleUsers googleUsers;

    TextView lupapass;

    ImageView btngoogle;

TextView tvReg;

    boolean passwordvisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sharedPreferences = getSharedPreferences("prefLogin", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        googleUsers = new GoogleUsers(this);

        user = findViewById(R.id.txtusernamelog);
        pass = findViewById(R.id.txtpasswordlog);
        btnlogin = findViewById(R.id.btnlogin);
        tvReg = findViewById(R.id.tv_registrasi);

        lupapass = findViewById(R.id.txtlupapassword);


        lupapass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,lupa_password1.class));
            }
        });



        tvReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pindah();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Login.this, user.getText().toString() + " // " + pass.getText().toString(), Toast.LENGTH_SHORT).show();

                RetrofitClient.getInstance().loginCaker(user.getText().toString(), pass.getText().toString())
                        .enqueue(new Callback<UsersResponse>() {
                            @Override
                            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                                Toast.makeText(Login.this, "ON RESPONSE", Toast.LENGTH_SHORT).show();
                                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {

                                    Toast.makeText(getApplicationContext(), "Login berhasil", Toast.LENGTH_SHORT).show();

                                    // meyimpan data ke local agar tidak login lagi (session)
//                                    UserUtil util = new UserUtil(Login.this, response.body().getData());
//                                    UserModel  om = new UserModel();
//                                    om.setId_pelamar(response.body().getData().getId_pelamar());
                                    Toast.makeText(Login.this, "id = "+response.body().getData().getId_pelamar(), Toast.LENGTH_SHORT).show();
                                    Intent pindah = new Intent(Login.this, dashboard.class);
                                    UserModel user = response.body().getData();
                                    editor.putString("id_pelamar", user.getId_pelamar());
                                    editor.putString("nama_lengkap", user.getNama_lengkap());
                                    editor.putString("username", user.getUsername());
                                    editor.putString("password", user.getPassword());
                                    editor.putString("no_telp", user.getNo_telp());
                                    editor.putString("Alamat", user.getAlamat());
                                    editor.putString("Email", user.getEmail());
                                    editor.apply();
                                    // jajalen login
                                    startActivity(pindah); // iki ne sg salah
                                } else {
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UsersResponse> call, Throwable t) {
                                t.printStackTrace();
                                Toast.makeText(Login.this, "ON FAILURE " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

//                Toast.makeText(Login.this, "END", Toast.LENGTH_SHORT).show();

            }
        });


        btngoogle = findViewById(R.id.google);


        btngoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(googleUsers.getIntent(), GoogleUsers.REQUEST_CODE);
            }
        });


        pass.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int right = 2;

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                    if (motionEvent.getRawX() >= pass.getRight() - pass.getCompoundDrawables()[right].getBounds().width()) {
                        int pilih = pass.getSelectionEnd();
                        if (passwordvisible) {

// menaruh gambar lain disini
                            pass.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibility_off, 0);

                            //fungsi untuk hiden password
                            pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordvisible = false;


                        } else {

                            // menaruh gambar lain disini
                            pass.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibility, 0);

                            //fungsi untuk hiden password
                            pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordvisible = true;


                        }

                        pass.setSelection(pilih);

                        return true;
                    }
                }


                return false;
            }
        });


    }

    private void pindah() {
        Intent i = new Intent(this, registrasi.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        googleUsers.onActivityResult(requestCode, resultCode, data);

        if (googleUsers.isAccountSelected()){

            RetrofitClient.getInstance().google_login(googleUsers.getUserData().getEmail()).enqueue(new Callback<UsersResponse>() {
                @Override
                public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                    if(response.body() != null && response.body().getStatus().equalsIgnoreCase("success")){
                        // meyimpan data ke local agar tidak login lagi (session)
                        SharedPreferences sharedPreferences = getSharedPreferences("prefLogin", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        UserModel user = response.body().getData();
                        editor.putString("id_pelamar", user.getId_pelamar());
                        editor.putString("nama_lengkap", user.getNama_lengkap());
                        editor.putString("username", user.getUsername());
                        editor.putString("password", user.getPassword());
                        editor.putString("no_telp", user.getNo_telp());
                        editor.putString("alamat", user.getAlamat());
                        editor.putString("email", user.getEmail());
                        editor.apply();
                        startActivity(new Intent(Login.this, dashboard.class));

                    }else{
                        Toast.makeText(Login.this, "Email yang anda masukan belum terdaftar", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UsersResponse> call, Throwable t) {

                }
            });
        }
    }
}