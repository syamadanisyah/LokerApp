package com.c3.lokerapp.cari;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.c3.lokerapp.R;
import com.c3.lokerapp.detail_pekerjaan;
import com.c3.lokerapp.koneksi.DetailResponse;
import com.c3.lokerapp.koneksi.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adaptersearching extends RecyclerView.Adapter<adaptersearching.viewholdersearching> {
    private Context context;

    private List<model_data_searching> viewitem;

    public adaptersearching(Context context, List<model_data_searching> viewitem) {
        if (viewitem == null) {
            this.viewitem = new ArrayList<>();
        } else {
            this.viewitem = viewitem;
        }
        this.context = context;
    }


    @NonNull
    @Override
    public adaptersearching.viewholdersearching onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View flater = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rc_searching, parent, false);

        return new viewholdersearching(flater);
    }

    @Override
    public void onBindViewHolder(@NonNull adaptersearching.viewholdersearching holder, int position) {
        model_data_searching sq = viewitem.get(position);

        if (viewitem != null) {
            holder.nama_perusahaan_searching.setText(sq.getNama());
            holder.pekerjaan_searching.setText(sq.getJudul_post());
            holder.kategori_seaching.setText(sq.getKategori());

        }

        Glide.with(context)
                .load(RetrofitClient.BASE_URL+sq.getFoto())
                .centerCrop()
                .placeholder(R.drawable.logo)
                .into(holder.logo_perusahaan_searching);

        SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("prefLogin", holder.itemView.getContext().MODE_PRIVATE);
        String id_pelamar = sharedPreferences.getString("id_pelamar", "");

        RetrofitClient.getInstance().cekfavorit(id_pelamar, sq.getId_post()).enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("alreadyex")) {
                    holder.btn_simpan_searching.setImageResource(R.drawable.love_red);
                    Toast.makeText(holder.itemView.getContext(), sq.getId_post(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        holder.btn_simpan_searching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btn_simpan_searching.setImageResource(R.drawable.love_red);
                RetrofitClient.getInstance().simpanfavorit(id_pelamar, sq.getId_post()).enqueue(new Callback<DetailResponse>() {
                    @Override
                    public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                        if (response.body() != null && response.body().getMessage().equals("success")) {
                            Toast.makeText(holder.itemView.getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(holder.itemView.getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            }
        });
        // Listener untuk menampilkan detail pekerjaan
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Data dari item yang diklik
                String nama = sq.getNama();
                String pekerjaan = sq.getJudul_post();
                String kategori = sq.getKategori();
                String deskripsi = sq.getDeskripi_post();
                String id_post = sq.getId_post();
                String link_lamar = sq.getLink_lamar();
                String foto = sq.getFoto();

                // Membuat Intent untuk pindah ke halaman detail_pekerjaan
                Intent intent = new Intent(v.getContext(), detail_pekerjaan.class);
                intent.putExtra("nama", nama);
                intent.putExtra("judul_post", pekerjaan);
                intent.putExtra("kategori", kategori);
                intent.putExtra("deskripsi_post", deskripsi);
                intent.putExtra("id_post", id_post);
                intent.putExtra("link_lamar",link_lamar);
                intent.putExtra("foto",foto);
                Toast.makeText(v.getContext(), "deskripsi nde adapter : " + deskripsi, Toast.LENGTH_SHORT).show();
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return viewitem != null ? viewitem.size():0;
    }

    public class viewholdersearching extends RecyclerView.ViewHolder {
        ImageView logo_perusahaan_searching, btn_simpan_searching;
        TextView nama_perusahaan_searching, kategori_seaching, pekerjaan_searching;

        public viewholdersearching(@NonNull View itemView) {
            super(itemView);


            logo_perusahaan_searching = itemView.findViewById(R.id.logo_perusahaan_searching);
            btn_simpan_searching = itemView.findViewById(R.id.btnsimpansearching);
            nama_perusahaan_searching = itemView.findViewById(R.id.nama_peru_searching);
            pekerjaan_searching = itemView.findViewById(R.id.pekerjaan_searching);
            kategori_seaching = itemView.findViewById(R.id.kategori_searching);
        }



    }
}
