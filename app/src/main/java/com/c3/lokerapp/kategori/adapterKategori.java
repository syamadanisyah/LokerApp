package com.c3.lokerapp.kategori;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
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
import com.c3.lokerapp.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adapterKategori extends RecyclerView.Adapter<adapterKategori.viewholderKategori> {
    private List<ambil_data_det_post> modelitem;

    private Context context;

    public adapterKategori(Context context, List<ambil_data_det_post> modelitem) {
        if (modelitem == null) {
            this.modelitem = new ArrayList<>();
        } else {
            this.modelitem = modelitem;

        }
        this.context = context;
    }


    //mempunyai kode fungsi yang sama semua seperti adapter1

    @NonNull
    @Override
    public adapterKategori.viewholderKategori onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View flater = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lv_kategori, parent, false);


        return new viewholderKategori(flater);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterKategori.viewholderKategori holder, @SuppressLint("RecyclerView") int position) {
        ambil_data_det_post listitem = modelitem.get(position);

        holder.txt_nama.setText(listitem.getNama());
        holder.txt_pekerjaan.setText(listitem.getJudul_post());
        holder.txt_kategori.setText(listitem.getId_kategori());


        Glide.with(context)
                .load(listitem.getFoto())
                .centerCrop()
                .placeholder(R.drawable.logo)
                .into(holder.logo_kategori);


        SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("prefLogin", holder.itemView.getContext().MODE_PRIVATE);
        String id_pelamar = sharedPreferences.getString("id_pelamar", "");

        RetrofitClient.getInstance().cekfavorit(id_pelamar, listitem.getId_post()).enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("alreadyex")) {
                    holder.btn_simpan_k.setImageResource(R.drawable.love_red);
                }
            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        holder.btn_simpan_k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, listitem.getId_post(), Toast.LENGTH_SHORT).show();
                // Mengambil shared preferences
                SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("prefLogin", holder.itemView.getContext().MODE_PRIVATE);
                String id_pelamar = sharedPreferences.getString("id_pelamar", "");

                // Melakukan pengecekan untuk status favorit
                RetrofitClient.getInstance().cekfavorit(id_pelamar, listitem.getId_post()).enqueue(new Callback<DetailResponse>() {
                    @Override
                    public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {

                        if (response.isSuccessful()) {
                            DetailResponse detailResponse = response.body();

                            if (detailResponse != null) {
                                String status = detailResponse.getStatus();

                                // Jika sudah favorit, hapus dari favorit
                                if ("alreadyex".equalsIgnoreCase(status)) {
                                    Toast.makeText(context, "Dua Hapus", Toast.LENGTH_SHORT).show();
                                    RetrofitClient.getInstance().hapusfavorit(id_pelamar, listitem.getId_post()).enqueue(new Callback<FavoritRespon>() {
                                        @Override
                                        public void onResponse(Call<FavoritRespon> call, Response<FavoritRespon> response) {
                                            Toast.makeText(context, "Dua simpan reto", Toast.LENGTH_SHORT).show();
                                            if (response.isSuccessful() && response.body() != null && "success".equals(response.body().getStatus())) {
                                                holder.btn_simpan_k.setImageResource(R.drawable.love_white);
                                                Toast.makeText(holder.itemView.getContext(), "Dihapus dari favorit", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<FavoritRespon> call, Throwable t) {
                                            t.printStackTrace();
                                        }
                                    });
                                } else {
                                    Toast.makeText(context, "Dua simpan", Toast.LENGTH_SHORT).show();
                                    // Jika belum favorit, tambahkan ke favorit
                                    RetrofitClient.getInstance().simpanfavorit(id_pelamar, listitem.getId_post()).enqueue(new Callback<DetailResponse>() {
                                        @Override
                                        public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                                            Toast.makeText(context, "Dua simpan retro", Toast.LENGTH_SHORT).show();
                                            if (response.isSuccessful() && response.body() != null && "success".equals(response.body().getMessage())) {
                                                holder.btn_simpan_k.setImageResource(R.drawable.love_red);
                                                Toast.makeText(holder.itemView.getContext(), "Disimpan di favorit", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<DetailResponse> call, Throwable t) {
                                            t.printStackTrace();
                                        }
                                    });
                                }
                                notifyItemChanged(position);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nama = listitem.getNama();
                String pekerjaan = listitem.getJudul_post();
                String kategori = listitem.getId_kategori();
                String diskripsi = listitem.getDeskripsi_post();
                String id_post = listitem.getId_post();
                String link_lamar = listitem.getLink_lamar();
                String foto = listitem.getFoto();

                Intent intent = new Intent(v.getContext(), detail_pekerjaan.class);
                intent.putExtra("nama", nama);
                intent.putExtra("judul_post", pekerjaan);
                intent.putExtra("id_kategori", kategori);
                intent.putExtra("deskripsi_post", diskripsi);
                intent.putExtra("id_post", id_post);
                intent.putExtra("link_lamar", link_lamar);
                intent.putExtra("foto", foto);
                Toast.makeText(v.getContext(), "deskripsi nde adapter : " + diskripsi, Toast.LENGTH_SHORT).show();
                v.getContext().startActivity(intent);

            }

        });


    }

    @Override
    public int getItemCount() {
        return modelitem != null ? modelitem.size() : 0;
    }

    public class viewholderKategori extends RecyclerView.ViewHolder {

        ImageView btn_simpan_k, logo_kategori;

        TextView txt_nama, txt_pekerjaan, txt_kategori;

        public viewholderKategori(@NonNull View itemView) {
            super(itemView);
            btn_simpan_k = itemView.findViewById(R.id.btn_simpan_kategori);
            txt_nama = itemView.findViewById(R.id.nama_tempat_pekerjaan_kategori);
            txt_pekerjaan = itemView.findViewById(R.id.pekerjaan_kategori);
            txt_kategori = itemView.findViewById(R.id.kategori_kategori);
            logo_kategori = itemView.findViewById(R.id.logo_perusahaan_kategori);
        }
    }
}
