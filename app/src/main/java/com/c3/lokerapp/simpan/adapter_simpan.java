package com.c3.lokerapp.simpan;

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
import com.c3.lokerapp.koneksi.RetrofitClient;
import com.c3.lokerapp.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adapter_simpan extends RecyclerView.Adapter<adapter_simpan.viewholder_simpan> {

    private Context context;
    List<model_favorit> listitem;

    private pindah pindah;

    public adapter_simpan(Context context, List<model_favorit> listitem, adapter_simpan.pindah pindah) {
        if (listitem == null) {
            this.listitem = new ArrayList<>();
        } else {
            this.listitem = listitem;

        }
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder_simpan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View flater = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rc_simpan, parent, false);
        return new viewholder_simpan(flater);
    }


    @Override
    public void onBindViewHolder(@NonNull viewholder_simpan holder, @SuppressLint("RecyclerView") int position) {
        model_favorit item_simpan = listitem.get(position);
       /* UserUtil util = new UserUtil(holder.itemView.getContext());
        String id_pelamar = util.getId();*/


        //holder.img_simpan.setImageResource(item_simpan.getImg());
        holder.txt_nama_perusahaan_simpan.setText(item_simpan.getNama());
        holder.txt_pekerjaan_simpan.setText(item_simpan.getJudul_post());
        holder.txt_kategori_simpan.setText(item_simpan.getId_kategori());
        /* holder.btn_simpan*/


        Glide.with(context)
                .load( item_simpan.getFoto())
                .centerCrop()
                .placeholder(R.drawable.logo)
                .into(holder.img_simpan);


        holder.btn_simpan.setImageResource(R.drawable.love_red);

        SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("prefLogin", holder.itemView.getContext().MODE_PRIVATE);
        String id_pelamar = sharedPreferences.getString("id_pelamar", "");


        holder.btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitClient.getInstance().hapusfavorit(id_pelamar, item_simpan.getId_post()).enqueue(new Callback<FavoritRespon>() {
                    @Override
                    public void onResponse(Call<FavoritRespon> call, Response<FavoritRespon> response) {
                        if (response.body().getStatus().equals("success")) {
                            // Mengubah gambar tombol_simpan menjadi love_white
                            holder.btn_simpan.setImageResource(R.drawable.love_white);

                            // Menampilkan ID post yang dihapus favoritnya
                            Toast.makeText(holder.itemView.getContext(), item_simpan.getId_post() + " dihapus dari favorit", Toast.LENGTH_SHORT).show();

                            // Menghapus item dari daftar (list)
                            listitem.remove(position);
                            // Memberi tahu adapter bahwa data telah berubah
                            notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onFailure(Call<FavoritRespon> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = item_simpan.getNama();
                String pekerjaan = item_simpan.getJudul_post();
                String kategori = item_simpan.getId_kategori();
                String deskripsi = item_simpan.getDeskripsi_post();
                String id_post = item_simpan.getId_post();
                String link_lamar = item_simpan.getLink_lamar();
                String foto = item_simpan.getFoto();

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
        return listitem != null ? listitem.size() : 0;
    }

    public class viewholder_simpan extends RecyclerView.ViewHolder {

        ImageView img_simpan, btn_simpan;

        TextView txt_nama_perusahaan_simpan, txt_pekerjaan_simpan, txt_kategori_simpan;

        public viewholder_simpan(@NonNull View itemView) {
            super(itemView);

            img_simpan = itemView.findViewById(R.id.logo_simpan);

            btn_simpan = itemView.findViewById(R.id.btnsimpan);

            txt_nama_perusahaan_simpan = itemView.findViewById(R.id.nama_peru_simpan);
            txt_pekerjaan_simpan = itemView.findViewById(R.id.pekerjaan_simpan);
            txt_kategori_simpan = itemView.findViewById(R.id.kategori_simpan);


        }
    }

    public interface pindah {
        void onItemClick(int position);
    }
}
