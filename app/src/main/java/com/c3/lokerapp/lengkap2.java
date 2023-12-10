package com.c3.lokerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.c3.lokerapp.koneksi.DetailResponse;
import com.c3.lokerapp.koneksi.RetrofitClient;
import com.c3.lokerapp.koneksi.RetrofitEndPoint;
import com.c3.lokerapp.koneksi.ambil_data_det_post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class lengkap2 extends AppCompatActivity {
    public void ambilDataLengkapSpinner() {
        SharedPreferences sharedPreferences = getSharedPreferences("prefLogin", MODE_PRIVATE);
        String id_pelamar = sharedPreferences.getString("id_pelamar", "");

        Toast.makeText(this, "id pelamar "+id_pelamar, Toast.LENGTH_SHORT).show();
        RetrofitEndPoint endpointL = RetrofitClient.getInstance();
        Call<DetailResponse> leng = endpointL.gunakadataspinnerlengkap(id_pelamar);

        TextView kosong = findViewById(R.id.kosong);
        leng.enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                if (response.isSuccessful()) {
                    List<ambil_data_det_post> datalengkap = response.body().getData();
                    adapterlengkap2 al = new adapterlengkap2(lengkap2.this, datalengkap);
                    rv_lengkap2.setAdapter(al);

                    if (datalengkap.size() < 1){
                        kosong.setVisibility(View.VISIBLE);
                        rv_lengkap2.setVisibility(View.GONE);
                    }else {
                        kosong.setVisibility(View.GONE);
                        rv_lengkap2.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(lengkap2.this, "erorr", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    RecyclerView rv_lengkap2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lengkap2);

        Toast.makeText(this, "lengkap 2", Toast.LENGTH_SHORT).show();
        rv_lengkap2 = findViewById(R.id.recyclerview_lengkap2);
        ambilDataLengkapSpinner();

    }
}