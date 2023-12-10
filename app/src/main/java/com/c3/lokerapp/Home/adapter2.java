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
import com.c3.lokerapp.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adapter2 extends RecyclerView.Adapter<adapter2.viewholder2> {

    private Context context;
    List<ambil_data_det_post> itemlist2;

   /* private tampilan2 tampil2;*/

    public adapter2(Context context,List<ambil_data_det_post> itemslist) {
        if (itemslist == null) {
            this.itemlist2 = new ArrayList<>();
        } else {
            this.itemlist2 = itemslist;

        }
        this.context = context;
    }


    @NonNull
    @Override
    public adapter2.viewholder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View flater = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rc2,parent,false);
        return new viewholder2(flater);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter2.viewholder2 holder, @SuppressLint("RecyclerView") int position) {
        ambil_data_det_post listitem2 = itemlist2.get(position);
       /* UserUtil util = new UserUtil(holder.itemView.getContext());
        String id_pelamar = util.getId();*/

        if (listitem2 != null) {
            holder.txt_nama2.setText(listitem2.getNama());
            holder.pekerjaan2.setText(listitem2.getJudul_post());
            holder.kategori_pekerjaan2.setText(listitem2.getId_kategori());
        }

        Glide.with(context)
                .load(listitem2.getFoto())
                .centerCrop()
                .placeholder(R.drawable.logo)
                .into(holder.logo2);

        SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("prefLogin", holder.itemView.getContext().MODE_PRIVATE);
        String id_pelamar = sharedPreferences.getString("id_pelamar", "");


        RetrofitClient.getInstance().cekfavorit(id_pelamar,listitem2.getId_post()).enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("alreadyex")){
                    holder.btn_simpan_home2.setImageResource(R.drawable.love_red);
                }
            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
t.printStackTrace();
            }
        });

        holder.btn_simpan_home2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, listitem2.getId_post(), Toast.LENGTH_SHORT).show();
                // Mengambil shared preferences
                SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("prefLogin", holder.itemView.getContext().MODE_PRIVATE);
                String id_pelamar = sharedPreferences.getString("id_pelamar", "");

                // Melakukan pengecekan untuk status favorit
                RetrofitClient.getInstance().cekfavorit(id_pelamar, listitem2.getId_post()).enqueue(new Callback<DetailResponse>() {
                    @Override
                    public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {

                        if (response.isSuccessful()) {
                            DetailResponse detailResponse = response.body();

                            if (detailResponse != null) {
                                String status = detailResponse.getStatus();

                                // Jika sudah favorit, hapus dari favorit
                                if ("alreadyex".equalsIgnoreCase(status)) {
                                    Toast.makeText(context, "Dua Hapus", Toast.LENGTH_SHORT).show();
                                    RetrofitClient.getInstance().hapusfavorit(id_pelamar, listitem2.getId_post()).enqueue(new Callback<FavoritRespon>() {
                                        @Override
                                        public void onResponse(Call<FavoritRespon> call, Response<FavoritRespon> response) {
                                            Toast.makeText(context, "Dua simpan reto", Toast.LENGTH_SHORT).show();
                                            if (response.isSuccessful() && response.body() != null && "success".equals(response.body().getStatus())) {
                                                holder.btn_simpan_home2.setImageResource(R.drawable.love_white);
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
                                    RetrofitClient.getInstance().simpanfavorit(id_pelamar, listitem2.getId_post()).enqueue(new Callback<DetailResponse>() {
                                        @Override
                                        public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                                            Toast.makeText(context, "Dua simpan retro", Toast.LENGTH_SHORT).show();
                                            if (response.isSuccessful() && response.body() != null && "success".equals(response.body().getMessage())) {
                                                holder.btn_simpan_home2.setImageResource(R.drawable.love_red);
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

                // Data dari item yang diklik
                String nama = listitem2.getNama();
                String pekerjaan = listitem2.getJudul_post();
                String kategori = listitem2.getId_kategori();
                String deskripsi = listitem2.getDeskripsi_post();
                String id_post = listitem2.getId_post();
                String link_lamar = listitem2.getLink_lamar();
                String foto = listitem2.getFoto();

                // Kirim data ke aktivitas selanjutnya

                Intent intent = new Intent(v.getContext(), detail_pekerjaan.class);
                intent.putExtra("nama", nama);
                intent.putExtra("judul_post", pekerjaan);
                intent.putExtra("id_kategori", kategori);
                intent.putExtra("deskripsi_post",deskripsi);
                intent.putExtra("id_post",id_post);
                intent.putExtra("link_lamar",link_lamar);
                intent.putExtra("foto",foto);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemlist2 != null ? itemlist2.size() : 0;
    }

    public class viewholder2 extends RecyclerView.ViewHolder {

        ImageView logo2,btn_simpan_home2;
        TextView txt_nama2, txt_alamat2, txt_title2,pekerjaan2,kategori_pekerjaan2;

        public viewholder2(@NonNull View itemView) {
            super(itemView);

            btn_simpan_home2 = itemView.findViewById(R.id.btn_simpan_home2);
            logo2 = itemView.findViewById(R.id.logo2);
            txt_nama2 = itemView.findViewById(R.id.nama_peru2);
           /* txt_alamat2 = itemView.findViewById(R.id.alamat_peru2);
            txt_title2 = itemView.findViewById(R.id.title_peru2);*/
            pekerjaan2 = itemView.findViewById(R.id.pekerjaan2);
            kategori_pekerjaan2 = itemView.findViewById(R.id.kategori2);




        }
    }

    public interface tampilan2 {
        void onItemClick(int position);

    }
}
