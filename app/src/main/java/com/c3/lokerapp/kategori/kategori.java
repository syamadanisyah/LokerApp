package com.c3.lokerapp.kategori;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.c3.lokerapp.R;
import com.c3.lokerapp.koneksi.DetailResponse;
import com.c3.lokerapp.koneksi.RetrofitClient;
import com.c3.lokerapp.koneksi.RetrofitEndPoint;
import com.c3.lokerapp.koneksi.ambil_data_det_post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class kategori extends AppCompatActivity {

    public static String KATEGORI = "kategori";

    public static String GURU = "5", ADMIN = "10", STAFFRESTURANT = "8", BANK = "9", KURIR = "7", PENJAGA = "4",
            TEKNOLOGI_INFORMASI = "3", PENYANYI = "6",TENAGA_KESEHATAN = "11";

    private RecyclerView rv_kategori;

    private View view;
    private String dataKategori;

    private List<ambil_data_det_post> modelitem;


    public void ambildataserver() {

        RetrofitEndPoint end = RetrofitClient.getInstance();
        Call<DetailResponse> dr = end.ambildataspesifik(dataKategori);
        dr.enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                if (response.isSuccessful()) {
                    List<ambil_data_det_post> data__ = response.body().getData();
                    adapterKategori ak = new adapterKategori(kategori.this, data__);
                    rv_kategori.setAdapter(ak);

                    if (data__.size() <= 0){
                        Toast.makeText(kategori.this, "data tidak ada", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(kategori.this, "erorr" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    Spinner sk;
    TextView tv_kategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        tv_kategori = findViewById(R.id.tv_kategori);
        dataKategori = getIntent().getStringExtra(KATEGORI);
        rv_kategori = findViewById(R.id.recyclerview_kategori);
        dataKategori = getIntent().getStringExtra(KATEGORI);
        ambildataserver();

       /* sk = findViewById(R.id.spin_kategori);

        ArrayAdapter<CharSequence> ardap1 = ArrayAdapter.createFromResource(getApplication(), R.array.kategori,
                android.R.layout.simple_spinner_item);

        ardap1.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sk.setAdapter(ardap1);*/




    }


}