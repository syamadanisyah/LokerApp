package com.c3.lokerapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.c3.lokerapp.koneksi.RetrofitClient;
import com.c3.lokerapp.koneksi.UsersResponse;
import com.c3.lokerapp.koneksi.VerifyResponse;
import com.c3.lokerapp.shared.DataShared;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class registrasi2 extends AppCompatActivity {

    MaterialButton btn2, kembali2;
    EditText nama_lengkap, no_telp, alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi2);


        btn2 = findViewById(R.id.btnregis2);
        nama_lengkap = findViewById(R.id.txtnama_lengkap);
        no_telp = findViewById(R.id.txtno_telp);
        alamat = findViewById(R.id.txtalamat);
kembali2 = findViewById(R.id.btnkembali2);

kembali2.setOnClickListener(view -> {
    onBackPressed();
    overridePendingTransition(R.anim.layout_in,R.anim.layout_out);
});

        //dari activity pertama simpan dulu menggunakan key code "SAVE" lalu di panggil di activity kedua menggunakan DataShared,
        //kalau manggil di kelas ini menggunakan component TextView aja
        btn2.setOnClickListener(v -> {


            String namaLengkapInput = nama_lengkap.getText().toString().trim();
            String noTelpInput = no_telp.getText().toString().trim();
            String alamatInput = alamat.getText().toString().trim();

            if (namaLengkapInput.isEmpty() || noTelpInput.isEmpty() || alamatInput.isEmpty()) {
                Toast.makeText(registrasi2.this, "Nama Lengkap tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }


// Validasi TextView nama_lengkap
            if (namaLengkapInput.isEmpty()) {
                Toast.makeText(registrasi2.this, "Nama Lengkap tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return; // Berhenti eksekusi jika validasi tidak terpenuhi
            }

// Validasi TextView no_telp
            else if (noTelpInput.isEmpty()) {
                Toast.makeText(registrasi2.this, "Nomor Telepon tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

// Validasi TextView alamat
            else if (alamatInput.isEmpty()) {
                Toast.makeText(registrasi2.this, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            } else {


            }

// Menampilkan AlertDialog setelah semua validasi terpenuhi
            new AlertDialog.Builder(registrasi2.this)
                    .setTitle("Konfirmasi Data")
                    .setMessage("Apakah data yang Anda masukkan sudah benar?")
                    .setPositiveButton("Benar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // Tutup dialog setelah logika selesai
                            dialog.dismiss();


                            DataShared dataShared = new DataShared(registrasi2.this);

                            //memanggil endpoint registrasiCaker dan menyimpan username,password,email yang di regis pertama lalu /n
                            // data di regis pertama di simpan di regis kedua  lalu baru di simpan di database
                            RetrofitClient.getInstance().registerCaker(
                                    dataShared.getData(DataShared.KEY.SAVED_USERNAME), dataShared.getData(DataShared.KEY.SAVED_PASSWORD),
                                    nama_lengkap.getText().toString(), no_telp.getText().toString(), alamat.getText().toString(),
                                    dataShared.getData(DataShared.KEY.SAVED_EMAIL)
                            ).enqueue(new Callback<UsersResponse>() {
                                @Override
                                public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                                    if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {

                                        Toast.makeText(registrasi2.this, "masuk otp", Toast.LENGTH_SHORT).show();

                                        RetrofitClient.getInstance().sendEmail(
                                                dataShared.getData(DataShared.KEY.SAVED_EMAIL), "SignUp", "new"
                                        ).enqueue(new Callback<VerifyResponse>() {
                                            @Override
                                            public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {

                                                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {
                                                    Toast.makeText(registrasi2.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                    startActivity(
                                                            new Intent(registrasi2.this, registrasi4_otp.class)
                                                                    .putExtra(registrasi4_otp.EMAIL, dataShared.getData(DataShared.KEY.SAVED_EMAIL))
                                                                    .putExtra(registrasi4_otp.OTP, response.body().getData().getOtp())

                                                    );


                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<VerifyResponse> call, Throwable t) {

                                            }
                                        });


                                    } else {
                                        Toast.makeText(registrasi2.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<UsersResponse> call, Throwable t) {
                                    Toast.makeText(registrasi2.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    })
                    .setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Logika yang dijalankan jika pengguna memilih "Kembali"
                            // Misalnya, tidak melakukan apa-apa atau menggulir ke bagian sebelumnya
                            // ...

                            // Tutup dialog setelah logika selesai
                            dialog.dismiss();
                        }
                    })
                    .show();


        });


    }
    public void onBackPressed(){

    }
}