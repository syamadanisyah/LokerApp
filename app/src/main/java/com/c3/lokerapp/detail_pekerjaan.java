package com.c3.lokerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.c3.lokerapp.koneksi.DetailResponse;
import com.c3.lokerapp.koneksi.RetrofitClient;
import com.c3.lokerapp.koneksi.ambil_data_det_post;
import com.c3.lokerapp.util.UserUtil;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class detail_pekerjaan extends AppCompatActivity {
    private TextView nama_perusahaan, pekerjaan_det_data, kategori, diskripsi;

    private MaterialButton btnsimpan, btnlamar;

    private String link;

    private ImageView kembali, logo_perusahaan_detail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pekerjaan);

        btnlamar = findViewById(R.id.lamar);
        String idpost = getIntent().getStringExtra("id_post");

        nama_perusahaan = findViewById(R.id.nama_perusahaan);
        pekerjaan_det_data = findViewById(R.id.pekerjaan_det_data);
        kategori = findViewById(R.id.kategori_det_data);
        diskripsi = findViewById(R.id.tv_diskripsi);
        logo_perusahaan_detail = findViewById(R.id.logo_perusahaan_detail);


        kembali = findViewById(R.id.kembali);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.layout_in, R.anim.layout_out);
            }
        });


        btnsimpan = findViewById(R.id.btnsimpan_det_pekerjaan);

        SharedPreferences sharedPreferences = this.getSharedPreferences("prefLogin", this.MODE_PRIVATE);
        String id_pelamar = sharedPreferences.getString("id_pelamar", "");
        btnsimpan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                RetrofitClient.getInstance().cekfavorit(id_pelamar, idpost)
                        .enqueue(new Callback<DetailResponse>() {
                            @Override
                            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("alreadyex")) {

                                }else {
                                    RetrofitClient.getInstance().simpanfavorit(id_pelamar, getIntent().getStringExtra("id_post"))
                                            .enqueue(new Callback<DetailResponse>() {
                                                @Override
                                                public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                                                    if (response.body() != null && response.body().getMessage() == "success") {
                                                        Toast.makeText(detail_pekerjaan.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    } else {
                                                        Toast.makeText(detail_pekerjaan.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<DetailResponse> call, Throwable t) {
                                                    t.printStackTrace();
                                                }
                                            });                                }


                            }

                            @Override
                            public void onFailure(Call<DetailResponse> call, Throwable t) {

                            }
                        });
            }

        });


        ShowData();

//tak aku sisan gpp santai

        btnlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(link);

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


    }

    private void ShowData() {
        //dah sesuaikan ae trus fungsi ne panggil ke onCreate oke seyk
        nama_perusahaan.setText(getIntent().getStringExtra("nama"));
        pekerjaan_det_data.setText(getIntent().getStringExtra("judul_post"));
        kategori.setText(getIntent().getStringExtra("id_kategori"));
        diskripsi.setText(getIntent().getStringExtra("deskripsi_post"));
        link = getIntent().getStringExtra("link_lamar");

        Glide.with(detail_pekerjaan.this)
                .load(getIntent().getStringExtra("foto"))
                .placeholder(R.drawable.logo)
                .into(logo_perusahaan_detail);


//cek en toast e kosong po ngga "diskipsi nde adapter:null" ngnu tulisanne

    }
}