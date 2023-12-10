package com.c3.lokerapp.Home;

import android.annotation.SuppressLint;
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
import com.c3.lokerapp.koneksi.ambil_data_det_post;
import com.c3.lokerapp.simpan.FavoritRespon;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adapter1 extends RecyclerView.Adapter<adapter1.viewholder1> {

    private Context context;

    private List<ambil_data_det_post> itemslist;

    public adapter1(Context context, List<ambil_data_det_post> itemslist) {
        if (itemslist == null) {
            this.itemslist = new ArrayList<>();
        } else {
            this.itemslist = itemslist;
        }
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View flater = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rc, parent, false);
        return new viewholder1(flater);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder1 holder, @SuppressLint("RecyclerView") int position) {
        ambil_data_det_post listitem = itemslist.get(position);

        if (listitem != null) {
            holder.txt_nama.setText(listitem.getNama());
            holder.pekerjaan.setText(listitem.getJudul_post());
            holder.kategori_pekerjaan.setText(listitem.getId_kategori());
        }

        Glide.with(context)
                .load( listitem.getFoto())
                .centerCrop()
                .placeholder(R.drawable.logo)
                .into(holder.logo);

        // Mendapatkan SharedPreferences
        SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("prefLogin", holder.itemView.getContext().MODE_PRIVATE);
        String id_pelamar = sharedPreferences.getString("id_pelamar", "");

        // Melakukan pengecekan untuk status favorit
        RetrofitClient.getInstance().cekfavorit(id_pelamar, listitem.getId_post()).enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("alreadyex")) {
                    holder.btn_simpan1.setImageResource(R.drawable.love_red);
                    Toast.makeText(holder.itemView.getContext(), listitem.getId_post(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        // Listener untuk menyimpan ke favorit
        // Listener untuk btn_simpan1
        // Listener untuk btn_simpan1
        // Listener untuk btn_simpan1
        // Listener untuk btn_simpan1
        //Listener untuk btn_simpan1
        holder.btn_simpan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mengambil shared preferences
                SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("prefLogin", holder.itemView.getContext().MODE_PRIVATE);
                String id_pelamar = sharedPreferences.getString("id_pelamar", "");


                // Melakukan pengecekan untuk status favorit
                RetrofitClient.getInstance().cekfavorit(id_pelamar, listitem.getId_post()).enqueue(new Callback<DetailResponse>() {
                    @Override
                    public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                        if (response.body() != null && response.body().getStatus().equalsIgnoreCase("alreadyex")) {
                            // Jika sudah favorit, hapus dari favorit
                            RetrofitClient.getInstance().hapusfavorit(id_pelamar, listitem.getId_post()).enqueue(new Callback<FavoritRespon>() {
                                @Override
                                public void onResponse(Call<FavoritRespon> call, Response<FavoritRespon> response) {
                                    if (response.body().getStatus().equals("success")) {
                                        holder.btn_simpan1.setImageResource(R.drawable.love_white);
                                        Toast.makeText(holder.itemView.getContext(), "Dihapus dari favorit", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<FavoritRespon> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        } else {
                            // Jika belum favorit, tambahkan ke favorit
                            RetrofitClient.getInstance().simpanfavorit(id_pelamar, listitem.getId_post()).enqueue(new Callback<DetailResponse>() {
                                @Override
                                public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                                    if (response.body() != null && response.body().getMessage().equals("success")) {
                                        holder.btn_simpan1.setImageResource(R.drawable.love_red);
                                        Toast.makeText(holder.itemView.getContext(), "Disimpan di favorit", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<DetailResponse> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        }
                        // itemslist.remove(position); berfungsi untuk menghapus dari recyclerview
                       // itemslist.remove(position);
                        notifyItemChanged(position);
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
                String nama = listitem.getNama();
                String pekerjaan = listitem.getJudul_post();
                String kategori = listitem.getId_kategori();
                String deskripsi = listitem.getDeskripsi_post();
                String id_post = listitem.getId_post();
                String link_lamar = listitem.getLink_lamar();
                String foto = listitem.getFoto();

                // Membuat Intent untuk pindah ke halaman detail_pekerjaan
                Intent intent = new Intent(v.getContext(), detail_pekerjaan.class);
                intent.putExtra("nama", nama);
                intent.putExtra("judul_post", pekerjaan);
                intent.putExtra("id_kategori", kategori);
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
        return itemslist != null ? itemslist.size() : 0;
    }

    public static class viewholder1 extends RecyclerView.ViewHolder {
        ImageView btn_simpan1,logo;
        TextView txt_nama, pekerjaan, kategori_pekerjaan;

        public viewholder1(@NonNull View itemView) {
            super(itemView);
            btn_simpan1 = itemView.findViewById(R.id.btn_simpan_home);
            txt_nama = itemView.findViewById(R.id.nama_peru);
            pekerjaan = itemView.findViewById(R.id.pekerjaan);
            kategori_pekerjaan = itemView.findViewById(R.id.kategori);
            logo = itemView.findViewById(R.id.logo);
        }
    }
}
