package com.c3.lokerapp.akun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.c3.lokerapp.Login;
import com.c3.lokerapp.R;
import com.c3.lokerapp.dashboard;
import com.c3.lokerapp.koneksi.RetrofitClient;
import com.c3.lokerapp.koneksi.UserModel;
import com.c3.lokerapp.koneksi.UsersResponse;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class edit_akun extends AppCompatActivity {

    ImageView btnkembali;

    MaterialButton btnedit;
    EditText username, nama_lengkap, email, alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_akun);
        btnkembali = findViewById(R.id.btn_kembali_profile);

        //inisiasi component design
        username = findViewById(R.id.et_username);
        nama_lengkap = findViewById(R.id.et_nama_lengkap);
        email = findViewById(R.id.et_email);
        alamat = findViewById(R.id.et_alamat);

        btnedit = findViewById(R.id.btn_edit_akun);


        // Mengambil instance dari SharedPreferences
        SharedPreferences sharedPreferences = this.getSharedPreferences("prefLogin", Context.MODE_PRIVATE);

// Mengambil nilai dari SharedPreferences
        String Username = sharedPreferences.getString("username", "");
        String Nama_lengkap = sharedPreferences.getString("nama_lengkap", "");
        String Email = sharedPreferences.getString("Email", "");
        String Alamat = sharedPreferences.getString("Alamat", "");

        Toast.makeText(this, "Email = " + Email, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Alamat = " + Alamat, Toast.LENGTH_SHORT).show();

// Menetapkan nilai ke TextView
        username.setText(Username);
        nama_lengkap.setText(Nama_lengkap);
        email.setText(Email);
        alamat.setText(Alamat);




        btnkembali.setOnClickListener(view -> {

            onBackPressed();
        });


        SharedPreferences sharedPreferences1 = this.getSharedPreferences("prefLogin", Context.MODE_PRIVATE);
        String idPelamar = sharedPreferences1.getString("id_pelamar", "");
        SharedPreferences.Editor editor = sharedPreferences1.edit();

        //button edit
        btnedit.setOnClickListener(view -> {

            //kode untuk melakukan cek validasi data
            if (username.getText().toString().isEmpty()){
                Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            } else if (nama_lengkap.getText().toString().isEmpty()) {
                Toast.makeText(this, "Nama Lengkap tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            } else if (email.getText().toString().isEmpty()) {
                Toast.makeText(this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            } else if (!email.getText().toString().contains("@") || !email.getText().toString().contains(".") ){
                Toast.makeText(this, "Email harus mengandung karakter @ dan karakter titik '.' ", Toast.LENGTH_SHORT).show();
                return;
            } else if (alamat.getText().toString().isEmpty()) {
                Toast.makeText(this, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            if (nama_lengkap.getText().toString().length() > 15){
                Toast.makeText(this, "Nama tidak boleh lebih dari 15", Toast.LENGTH_SHORT).show();
                return;
            }

            //memanggil endpoint edit akun
            RetrofitClient.getInstance().edit_akun(idPelamar, username.getText().toString(), nama_lengkap.getText().toString(), alamat.getText().toString(), email.getText().toString())
                    .enqueue(new Callback<UsersResponse>() {
                        @Override
                        public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                            if (response != null && response.body().getStatus().equalsIgnoreCase("success")) {
                                Toast.makeText(edit_akun.this, "akun berhasil di edit", Toast.LENGTH_SHORT).show();

                                UserModel user = response.body().getData();
                                editor.putString("nama_lengkap", user.getNama_lengkap());
                                editor.putString("username", user.getUsername());
                                editor.putString("Alamat", user.getAlamat());
                                editor.putString("Email", user.getEmail());
                                editor.apply();
                                onBackPressed();
                            } else {
                                Toast.makeText(edit_akun.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UsersResponse> call, Throwable t) {
                            t.printStackTrace();
                            Toast.makeText(edit_akun.this,t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        });

    }
}