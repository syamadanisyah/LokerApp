package com.c3.lokerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.c3.lokerapp.Home.adapter2;
import com.c3.lokerapp.cari.ResponseSearching;
import com.c3.lokerapp.cari.adaptersearching;
import com.c3.lokerapp.cari.model_data_searching;
import com.c3.lokerapp.cari.searching;
import com.c3.lokerapp.koneksi.DetailResponse;
import com.c3.lokerapp.koneksi.RetrofitClient;
import com.c3.lokerapp.koneksi.RetrofitEndPoint;
import com.c3.lokerapp.koneksi.ambil_data_det_post;
import com.c3.lokerapp.shared.DataShared;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class lengkap extends AppCompatActivity {


    private RecyclerView rv_lengkap;


    public static String LENGKAP = "lengkap";
    private String datalengkap;



    private void getDataFromAPILengkap() {
        RetrofitEndPoint endpoint2 = RetrofitClient.getInstance();
        rv_lengkap = findViewById(R.id.horizontalRecyclerView2);
        Call<DetailResponse> ambil2 = endpoint2.gunakadataspinner(new DataShared(lengkap.this).getData(DataShared.KEY.ACC_ID_USER));
        ambil2.enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                if (response.isSuccessful()) {
                    List<ambil_data_det_post> data2 = response.body().getData();
                    adapterlengkap adapterkedua = new adapterlengkap(lengkap.this,data2);
                    rv_lengkap.setAdapter(adapterkedua);
                } else {
                    Toast.makeText(lengkap.this, "erorr" + response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    /*public void ambilDataLengkapSpinner() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("prefLogin",MODE_PRIVATE);
        String id_pelamar = sharedPreferences.getString("id_pelamar", "");

        RetrofitEndPoint endpointL = RetrofitClient.getInstance();
        Call<DetailResponse> leng = endpointL.gunakadataspinner(id_pelamar);
        leng.enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                if (response.isSuccessful()) {
                    List<ambil_data_det_post> datalengkap = response.body().getData();
                    adapterlengkap al = new adapterlengkap(datalengkap);
                    rv_lengkap.setAdapter(al);
                }else {
                    Toast.makeText(lengkap.this, "erorr", Toast.LENGTH_SHORT).show();



                }
            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }*/

    public void ambilDataLengkapTerbaru() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("prefLogin",MODE_PRIVATE);
        String id_pelamar = sharedPreferences.getString("id_pelamar", "");

        RetrofitEndPoint endpointL = RetrofitClient.getInstance();
        Call<DetailResponse> leng = endpointL.ambildatabawah(id_pelamar);
        leng.enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                if (response.isSuccessful()) {
                    List<ambil_data_det_post> datalengkap = response.body().getData();
                    adapterlengkap al = new adapterlengkap(lengkap.this,datalengkap);
                    rv_lengkap.setAdapter(al);
                }else {
                    Toast.makeText(lengkap.this, "erorr", Toast.LENGTH_SHORT).show();



                }
            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    private void ambildataspinner(){
        RetrofitEndPoint EP = RetrofitClient.getInstance();
        Call<DetailResponse> cd = EP.gunakadataspinner(new DataShared(getApplicationContext()).getData(DataShared.KEY.ACC_ID_USER));
        cd.enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                if (response.isSuccessful()) {
                    List<ambil_data_det_post> data2 = response.body().getData();
                    adapterlengkap adapterkedua = new adapterlengkap(lengkap.this,data2);
                    rv_lengkap.setAdapter(adapterkedua);
                } else {
                    Toast.makeText(getApplicationContext(), "erorr" + response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {

            }
        });
    }


    private void searching(){
        searchingLengkap.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_FLAG_NO_ENTER_ACTION) {
                // Hide the keyboard
                InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                if (!Objects.requireNonNull(searchingLengkap.getText()).toString().isEmpty()) {
                    RetrofitClient.getInstance().cari(searchingLengkap.getText().toString()).enqueue(new Callback<ResponseSearching>() {
                        @Override
                        public void onResponse(Call<ResponseSearching> call, Response<ResponseSearching> response) {
        //                    if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")){

                            List<model_data_searching> cari = response.body().getData();
                            adaptersearching ads = new adaptersearching(lengkap.this, cari);
                            rv_lengkap.setAdapter(ads);
//                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseSearching> call, Throwable t) {

                        }
                    });

                }

                return true;
            }
            return false;
        });


    }

    private TextInputEditText searchingLengkap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lengkap);

        searchingLengkap = findViewById(R.id.searching_lengkap);
        rv_lengkap = findViewById(R.id.recyclerview_lengkap);
//        getDataFromAPILengkap();
//        ambildataspinner();




searching();

        ambilDataLengkapTerbaru();
    }
}