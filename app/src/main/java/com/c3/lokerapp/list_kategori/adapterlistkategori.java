package com.c3.lokerapp.list_kategori;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c3.lokerapp.R;
import com.c3.lokerapp.kategori.kategori;
import com.c3.lokerapp.koneksi.DetailResponse;
import com.c3.lokerapp.koneksi.RetrofitClient;
import com.c3.lokerapp.koneksi.ambil_data_det_post;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adapterlistkategori extends RecyclerView.Adapter<adapterlistkategori.viewholderlistkategori> {

    private final ArrayList<KategoriLainnyaModel> modelLainnya;
    private final Context context;

    public adapterlistkategori(Context context,ArrayList<KategoriLainnyaModel> modelLainnya) {
        this.modelLainnya = modelLainnya;
        this.context = context;
        Toast.makeText(context, "Constructor " + this. modelLainnya.size(), Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public adapterlistkategori.viewholderlistkategori onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new adapterlistkategori.viewholderlistkategori(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_list_kategori, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull adapterlistkategori.viewholderlistkategori holder, int position) {
        KategoriLainnyaModel datanya = modelLainnya.get(position);

        holder.LabelKategori.setText(datanya.getKategori());
        holder.LabelKategori.setOnClickListener(v -> {
            String id_kategori = datanya.getId_kategori();
           // String nama_kategori = datanya.getKategori();

            // Kirim data ke aktivitas selanjutnya
            Intent intent = new Intent(v.getContext(), kategori.class);
            intent.putExtra(kategori.KATEGORI, id_kategori);
//            intent.putExtra("kategori", nama_kategori);
            v.getContext().startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return modelLainnya != null ? modelLainnya.size() : 0;
    }

    public static class viewholderlistkategori extends RecyclerView.ViewHolder {

        TextView LabelKategori;

        public viewholderlistkategori(@NonNull View itemView) {
            super(itemView);
            LabelKategori = itemView.findViewById(R.id.txtkategori);

        }
    }
}
