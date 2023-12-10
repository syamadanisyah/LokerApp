package com.c3.lokerapp;

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
import com.c3.lokerapp.koneksi.DetailResponse;
import com.c3.lokerapp.koneksi.RetrofitClient;
import com.c3.lokerapp.koneksi.ambil_data_det_post;
import com.c3.lokerapp.simpan.FavoritRespon;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class adapterlengkap2 extends RecyclerView.Adapter<adapterlengkap2.viewholderlengkap2> {

    private Context context;
    private List<ambil_data_det_post> arraylist;

    public adapterlengkap2(Context context,List<ambil_data_det_post> arraylist) {
        if (arraylist == null) {
            this.arraylist = new ArrayList<>();
        } else {
            this.arraylist = arraylist;

        }
        this.context = context;
    }


    @NonNull
    @Override
    public adapterlengkap2.viewholderlengkap2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View flater = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rc_lengkap2, parent, false);
        return new viewholderlengkap2(flater);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterlengkap2.viewholderlengkap2 holder, @SuppressLint("RecyclerView") int position) {
        ambil_data_det_post list = arraylist.get(position);

        holder.nama_peru_lengkap2.setText(list.getNama());
        holder.kaegori_lengkap2.setText(list.getId_kategori());
        holder.pekerjaan_lengkap2.setText(list.getJudul_post());

        Glide.with(context)
                .load(list.getFoto())
                .centerCrop()
                .placeholder(R.drawable.logo)
                .into(holder.logo_lengkap2);

        SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("prefLogin", holder.itemView.getContext().MODE_PRIVATE);
        String id_pelamar = sharedPreferences.getString("id_pelamar", "");

        RetrofitClient.getInstance().cekfavorit(id_pelamar, list.getId_post()).enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("alreadyex")) {
                    holder.btn_simpan_lengkap2.setImageResource(R.drawable.love_red);
                }
            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        holder.btn_simpan_lengkap2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mengambil shared preferences
                SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("prefLogin", holder.itemView.getContext().MODE_PRIVATE);
                String id_pelamar = sharedPreferences.getString("id_pelamar", "");


                // Melakukan pengecekan untuk status favorit
                RetrofitClient.getInstance().cekfavorit(id_pelamar, list.getId_post()).enqueue(new Callback<DetailResponse>() {
                    @Override
                    public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                        if (response.body() != null && response.body().getStatus().equalsIgnoreCase("alreadyex")) {
                            // Jika sudah favorit, hapus dari favorit
                            RetrofitClient.getInstance().hapusfavorit(id_pelamar, list.getId_post()).enqueue(new Callback<FavoritRespon>() {
                                @Override
                                public void onResponse(Call<FavoritRespon> call, Response<FavoritRespon> response) {
                                    if (response.body().getStatus().equals("success")) {
                                        holder.btn_simpan_lengkap2.setImageResource(R.drawable.love_white);
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
                            RetrofitClient.getInstance().simpanfavorit(id_pelamar, list.getId_post()).enqueue(new Callback<DetailResponse>() {
                                @Override
                                public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                                    if (response.body() != null && response.body().getMessage().equals("success")) {
                                        holder.btn_simpan_lengkap2.setImageResource(R.drawable.love_red);
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


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Data dari item yang diklik
                String nama = list.getNama();
                String pekerjaan = list.getJudul_post();
                String kategori = list.getId_kategori();
                String diskripsi = list.getDeskripsi_post();
                String id_post = list.getId_post();
                String link_lamar = list.getLink_lamar();
                String foto = list.getFoto();

//ok disconnect en anydec oke siap
                Intent intent = new Intent(view.getContext(), detail_pekerjaan.class);
                intent.putExtra("nama", nama);
                intent.putExtra("judul_post", pekerjaan);
                intent.putExtra("id_kategori", kategori);
                intent.putExtra("deskripsi_post", diskripsi);
                intent.putExtra("id_post", id_post);
                intent.putExtra("link_lamar", link_lamar);
                intent.putExtra("foto",foto);
                Toast.makeText(view.getContext(), "deskripsi nde adapter : " + diskripsi, Toast.LENGTH_SHORT).show();
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arraylist != null ? arraylist.size() : 0;
    }

    public class viewholderlengkap2 extends RecyclerView.ViewHolder {

        ImageView btn_simpan_lengkap2,logo_lengkap2;

        TextView nama_peru_lengkap2, kaegori_lengkap2, pekerjaan_lengkap2;

        public viewholderlengkap2(@NonNull View itemView) {
            super(itemView);

            logo_lengkap2 = itemView.findViewById(R.id.logo_perusahaan_lengkap2);
            btn_simpan_lengkap2 = itemView.findViewById(R.id.btnsimpan2);

            nama_peru_lengkap2 = itemView.findViewById(R.id.nama_peru_lengkap2);
            kaegori_lengkap2 = itemView.findViewById(R.id.kategori_lengkap2);
            pekerjaan_lengkap2 = itemView.findViewById(R.id.pekerjaan_lengkap2);
        }
    }
}
