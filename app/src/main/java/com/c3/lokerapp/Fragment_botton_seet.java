package com.c3.lokerapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.c3.lokerapp.koneksi.RetrofitClient;
import com.c3.lokerapp.koneksi.RetrofitEndPoint;
import com.c3.lokerapp.list_kategori.KategoriLainnyaModel;
import com.c3.lokerapp.list_kategori.ResponseModelKategoriLainnya;
import com.c3.lokerapp.list_kategori.adapterlistkategori;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_botton_seet extends Fragment {

    private RecyclerView recyclerViewKategori;
    private adapterlistkategori adapterKategori;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_botton_seet, container, false);

        Tampilkan();

        return view;
    }

    private void Tampilkan() {
        RetrofitEndPoint endpoint = RetrofitClient.getInstance();

        Call<ResponseModelKategoriLainnya> ambil = endpoint.KategoriLainnya();
        ambil.enqueue(new Callback<ResponseModelKategoriLainnya>() {
            @Override
            public void onResponse(Call<ResponseModelKategoriLainnya> call, Response<ResponseModelKategoriLainnya> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(requireContext(), " ada", Toast.LENGTH_SHORT).show();

                    ArrayList<KategoriLainnyaModel> data = response.body().getData();
                    adapterKategori = new adapterlistkategori(requireContext(), data);
                    recyclerViewKategori.setAdapter(adapterKategori);
                } else {
                    Toast.makeText(getContext(), "erorr" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModelKategoriLainnya> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(requireContext(), "tidak ada", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
