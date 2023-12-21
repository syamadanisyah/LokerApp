package com.c3.lokerapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
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


    boolean passwordvisible;

    public static String EMAIL = "email";
    private String dataemail;


    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle("Error")
                .setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    EditText sandibaru, konfirmasisandi;
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

//berfungsi untuk menampilkan atau menyembunyikan text password
        sandibaru.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int right = 2;

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                    if (motionEvent.getRawX() >= sandibaru.getRight() - sandibaru.getCompoundDrawables()[right].getBounds().width()) {
                        int pilih = sandibaru.getSelectionEnd();
                        if (passwordvisible) {

// menaruh gambar lain disini
                            sandibaru.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibility_off, 0);

                            //fungsi untuk hiden password
                            sandibaru.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordvisible = false;


                        } else {

                            // menaruh gambar lain disini
                            sandibaru.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibility, 0);

                            //fungsi untuk hiden password
                            sandibaru.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordvisible = true;


                        }

                        sandibaru.setSelection(pilih);

                        return true;
                    }
                }


                return false;
            }
        });


        konfirmasisandi.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int right = 2;

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                    if (motionEvent.getRawX() >= konfirmasisandi.getRight() - konfirmasisandi.getCompoundDrawables()[right].getBounds().width()) {
                        int pilih = konfirmasisandi.getSelectionEnd();
                        if (passwordvisible) {

// menaruh gambar lain disini
                            konfirmasisandi.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibility_off, 0);

                            //fungsi untuk hiden password
                            konfirmasisandi.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordvisible = false;


                        } else {

                            // menaruh gambar lain disini
                            konfirmasisandi.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibility, 0);

                            //fungsi untuk hiden password
                            konfirmasisandi.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordvisible = true;


                        }

                        konfirmasisandi.setSelection(pilih);

                        return true;
                    }
                }


                return false;
            }
        });


        //untuk memanggil endpoint updatepassword yang berfungsi untuk mengubah password lama
        btn_simpan.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                String passwordbaru = sandibaru.getText().toString();
                String konfirmpassword = konfirmasisandi.getText().toString();


                if (passwordbaru.isEmpty()) {
                    showAlert("Sandi tidak boleh kosong");
                } else if (passwordbaru.length() < 8 || passwordbaru.length() > 12) {
                    showAlert("Sandi harus memiliki panjang 8 dan 12 karakter");
                } else if (!passwordbaru.matches(".*[A-Z].*")) {
                    showAlert("Sandi harus mengandung huruf besar");
                } else if (!passwordbaru.matches(".*[a-z].*")) {
                    showAlert("Sandi harus mengandung huruf kecil");
                } else if (!passwordbaru.matches(".*[0-9!@#$%^&*()-_=+{};:'\",.<>?/\\|`~].*")) {
                    showAlert("Sandi harus mengandung angka atau karakter spesial");
                } else if (konfirmpassword.isEmpty()) {
                    showAlert("Sandi tidak boleh kosong");
                } else if (konfirmpassword.length() < 8 || passwordbaru.length() > 12) {
                    showAlert("Sandi harus memiliki panjang 8 dan 12 karakter");
                } else if (!konfirmpassword.matches(".*[A-Z].*")) {
                    showAlert("Sandi harus mengandung huruf besar");
                } else if (!konfirmpassword.matches(".*[a-z].*")) {
                    showAlert("Sandi harus mengandung huruf kecil");
                } else if (!konfirmpassword.matches(".*[0-9!@#$%^&*()-_=+{};:'\",.<>?/\\|`~].*")) {
                    showAlert("Sandi harus mengandung angka atau karakter spesial");
                }else if(!passwordbaru.equals(konfirmpassword)){
                    showAlert("Sandi tidak cocok");
                } else {
                    if (sandibaru.getText().toString().equals(konfirmasisandi.getText().toString())) {
                        RetrofitClient.getInstance().updatePassword(dataEmail, sandibaru.getText().toString())
                                .enqueue(new Callback<UsersResponse>() {
                                    @Override
                                    public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                                        if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {
                                            startActivity(
                                                    new Intent(lupa_password3.this, Login.class)

                                            );

                                        } else {
                                            Toast.makeText(lupa_password3.this, "gagal", Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<UsersResponse> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });


                    } else {
                        Toast.makeText(lupa_password3.this, "Sandi Tidak Cocok", Toast.LENGTH_SHORT).show();
                    }
                }


            }


        });

    }
}