package com.c3.lokerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.c3.lokerapp.koneksi.DetailResponse;
import com.c3.lokerapp.koneksi.RetrofitClient;
import com.c3.lokerapp.koneksi.RetrofitEndPoint;
import com.c3.lokerapp.koneksi.UsersResponse;
import com.c3.lokerapp.shared.DataShared;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class registrasi_kategori extends AppCompatActivity {
    MaterialButton reg3;


    Spinner pilih1, pilih2;

    private String idPelamar = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi_kategori);

        pilih1 = findViewById(R.id.pilihan1);
        pilih2 = findViewById(R.id.pilihan2);
        reg3 = findViewById(R.id.btnregis3);


        ArrayAdapter<CharSequence> ardap1 = ArrayAdapter.createFromResource(this, R.array.kategori,
                android.R.layout.simple_spinner_item);

        ardap1.setDropDownViewResource(android.R.layout.simple_spinner_item);
        pilih1.setAdapter(ardap1);

        pilih1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter<CharSequence> ardap2 = ArrayAdapter.createFromResource(this, R.array.kategori,
                android.R.layout.simple_spinner_item);
        ardap2.setDropDownViewResource(android.R.layout.simple_spinner_item);
        pilih2.setAdapter(ardap2);

        pilih2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        reg3.setOnClickListener(view -> {

            SharedPreferences sharedPreferences = this.getSharedPreferences("prefLogin", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            DataShared dataShared = new DataShared(registrasi_kategori.this);

            editor.putString("spiner1", Integer.toString(pilih1.getSelectedItemPosition()));
            editor.putString("spiner2", Integer.toString(pilih2.getSelectedItemPosition()));
            editor.apply();


            RetrofitEndPoint spinx = RetrofitClient.getInstance();
            Call<UsersResponse> idpelamar = spinx.ambil_id_pelamar(dataShared.getData(DataShared.KEY.SAVED_EMAIL));
            idpelamar.enqueue(new Callback<UsersResponse>() {
                @Override
                public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                    if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {
                        idPelamar = response.body().getData().getId_pelamar();

                        RetrofitEndPoint spinx2 = RetrofitClient.getInstance();
                        Call<DetailResponse> chose = spinx2.data_spinner(
                                idPelamar,
                                pilih1.getSelectedItem().toString(),
                                pilih2.getSelectedItem().toString()
                        );
                        chose.enqueue(new Callback<DetailResponse>() {
                            @Override
                            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                                if (response.isSuccessful()) {
                                    startActivity(new Intent(registrasi_kategori.this, Login.class));
                                }else {
                                    Toast.makeText(registrasi_kategori.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<DetailResponse> call, Throwable t) {
                                t.printStackTrace();
                                Toast.makeText(registrasi_kategori.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }

                @Override
                public void onFailure(Call<UsersResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        });




    }
}