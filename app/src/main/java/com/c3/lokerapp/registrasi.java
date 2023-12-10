package com.c3.lokerapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.c3.lokerapp.koneksi.Responcekemail;
import com.c3.lokerapp.koneksi.RetrofitClient;
import com.c3.lokerapp.koneksi.RetrofitEndPoint;
import com.c3.lokerapp.koneksi.UsersResponse;
import com.c3.lokerapp.shared.DataShared;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class registrasi extends AppCompatActivity {

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle("Error")
                .setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    MaterialButton btn;
    EditText user, passw, nama, noTelp, alamat, email;

    boolean passwordvisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        user = findViewById(R.id.txtusername);
        passw = findViewById(R.id.txtpassword);

        email = findViewById(R.id.txtemail);


        passw.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int right = 2;

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                    if (motionEvent.getRawX() >= passw.getRight() - passw.getCompoundDrawables()[right].getBounds().width()) {
                        int pilih = passw.getSelectionEnd();
                        if (passwordvisible) {

// menaruh gambar lain disini
                            passw.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibility_off, 0);

                            //fungsi untuk hiden password
                            passw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordvisible = false;


                        } else {

                            // menaruh gambar lain disini
                            passw.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibility, 0);

                            //fungsi untuk hiden password
                            passw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordvisible = true;


                        }

                        passw.setSelection(pilih);

                        return true;
                    }
                }


                return false;
            }
        });


        btn = findViewById(R.id.btnregis);

        // ...

        btn.setOnClickListener(v -> {

            String usernameInput = user.getText().toString().trim();
            String passwordInput = passw.getText().toString().trim();
            String emailInput = email.getText().toString().trim();

            RetrofitClient.getInstance().cekemail(emailInput).enqueue(new Callback<Responcekemail>() {
                @Override
                public void onResponse(Call<Responcekemail> call, Response<Responcekemail> response) {
                    if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {


                        // Validasi data tidak boleh kosong
                        if (usernameInput.isEmpty() || passwordInput.isEmpty() || emailInput.isEmpty()) {
                            showAlert("Data tidak boleh kosong");
                            return;
                        }

                        // Validasi Username
                        if (usernameInput.isEmpty()) {
                            showAlert("Username tidak boleh kosong");
                        } else if (usernameInput.contains(" ")) {
                            showAlert("Username tidak boleh ada karakter spasi");
                        } else if (!Character.isLetter(usernameInput.charAt(0))) {
                            showAlert("Username harus diawali dengan huruf kecil/besar");
                        } else if (usernameInput.length() < 5 || usernameInput.length() > 15) {
                            showAlert("Username harus memiliki minimal 5 dan maksimal 15 karakter");
                        } else if (!usernameInput.matches("[a-zA-Z0-9!@#$%^&*()-_=+{};:'\",.<>?/\\|`~]+")) {
                            showAlert("Username hanya boleh mengandung huruf kecil, huruf kapital, angka, atau karakter khusus");
                        }

                        // Validasi Password
                        else if (passwordInput.isEmpty()) {
                            showAlert("Sandi tidak boleh kosong");
                        } else if (passwordInput.length() < 8 || passwordInput.length() > 12) {
                            showAlert("Sandi harus memiliki panjang 8 dan 12 karakter");
                        } else if (!passwordInput.matches(".*[A-Z].*")) {
                            showAlert("Sandi harus mengandung huruf besar");
                        } else if (!passwordInput.matches(".*[a-z].*")) {
                            showAlert("Sandi harus mengandung huruf kecil");
                        } else if (!passwordInput.matches(".*[0-9!@#$%^&*()-_=+{};:'\",.<>?/\\|`~].*")) {
                            showAlert("Sandi harus mengandung angka atau karakter spesial");
                        }

                        // Validasi Email
                        else if (emailInput.isEmpty()) {
                            showAlert("Email tidak boleh kosong");
                        } else if (!emailInput.contains("@") || !emailInput.contains(".")) {
                            showAlert("Email harus mengandung karakter @ dan menggunakan tanda titik (.)");
                        } else if (emailInput.contains(" ")) {
                            showAlert("Email tidak boleh menggunakan spasi");
                        } else {

                            // Lanjutkan dengan penyimpanan data dan intent jika semua validasi berhasil
                            DataShared dataShared = new DataShared(registrasi.this);
                            dataShared.setData(DataShared.KEY.SAVED_USERNAME, usernameInput);
                            dataShared.setData(DataShared.KEY.SAVED_PASSWORD, passwordInput);
                            dataShared.setData(DataShared.KEY.SAVED_EMAIL, emailInput);

                            startActivity(new Intent(registrasi.this, registrasi2.class));
                        }


                    } else {
                        Toast.makeText(registrasi.this, "erorr", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Responcekemail> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(registrasi.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        });

// ...


    }
}