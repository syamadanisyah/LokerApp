package com.c3.lokerapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.usb.UsbInterface;
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
import com.c3.lokerapp.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adapterlengkap extends RecyclerView.Adapter<adapterlengkap.viewholderlengkap> {

    private Context context;

    private List<ambil_data_det_post> dl;

    public adapterlengkap(Context context,List<ambil_data_det_post> dl) {
        if (dl == null) {
            this.dl = new ArrayList<>();
        } else {
            this.dl = dl;

        }
        this.context = context;
    }


    @NonNull
    @Override
    public adapterlengkap.viewholderlengkap onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View flater = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lv_lengkap, parent, false);
        return new viewholderlengkap(flater);

    }

    @Override
    public void onBindViewHolder(@NonNull adapterlengkap.viewholderlengkap holder, @SuppressLint("RecyclerView") int position) {
        ambil_data_det_post D = dl.get(position);

        /*UserUtil util = new UserUtil(holder.itemView.getContext());
        String id_pelamar = util.getId();*/

        holder.nama_peru_lengkap.setText(D.getNama());
        holder.kaegori_lengkap.setText(D.getId_kategori());
        holder.pekerjaan_lengkap.setText(D.getJudul_post());

        Glide.with(context)
                .load(D.getFoto())
                .centerCrop()
                .placeholder(R.drawable.logo)
                .into(holder.logo_lengkap);

        SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("prefLogin", holder.itemView.getContext().MODE_PRIVATE);
        String id_pelamar = sharedPreferences.getString("id_pelamar", "");

        RetrofitClient.getInstance().cekfavorit(id_pelamar, D.getId_post()).enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("alreadyex")){
                    holder.btn_simpan_lengkap.setImageResource(R.drawable.love_red);
                }
            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
t.printStackTrace();
            }
        });

        holder.btn_simpan_lengkap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mengambil shared preferences
                SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("prefLogin", holder.itemView.getContext().MODE_PRIVATE);
                String id_pelamar = sharedPreferences.getString("id_pelamar", "");


                // Melakukan pengecekan untuk status favorit
                RetrofitClient.getInstance().cekfavorit(id_pelamar, D.getId_post()).enqueue(new Callback<DetailResponse>() {
                    @Override
                    public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                        if (response.body() != null && response.body().getStatus().equalsIgnoreCase("alreadyex")) {
                            // Jika sudah favorit, hapus dari favorit
                            RetrofitClient.getInstance().hapusfavorit(id_pelamar, D.getId_post()).enqueue(new Callback<FavoritRespon>() {
                                @Override
                                public void onResponse(Call<FavoritRespon> call, Response<FavoritRespon> response) {
                                    if (response.body().getStatus().equals("success")) {
                                        holder.btn_simpan_lengkap.setImageResource(R.drawable.love_white);
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
                            RetrofitClient.getInstance().simpanfavorit(id_pelamar, D.getId_post()).enqueue(new Callback<DetailResponse>() {
                                @Override
                                public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                                    if (response.body() != null && response.body().getMessage().equals("success")) {
                                        holder.btn_simpan_lengkap.setImageResource(R.drawable.love_red);
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
                String nama = D.getNama();
                String pekerjaan = D.getJudul_post();
                String kategori = D.getId_kategori();
                String diskripsi = D.getDeskripsi_post();
                String id_post = D.getId_post();
                String link_lamar = D.getLink_lamar();
                String foto = D.getFoto();

//ok disconnect en anydec oke siap
                Intent intent = new Intent(view.getContext(), detail_pekerjaan.class);
                intent.putExtra("nama", nama);
                intent.putExtra("judul_post", pekerjaan);
                intent.putExtra("id_kategori", kategori);
                intent.putExtra("deskripsi_post", diskripsi);
                intent.putExtra("id_post", id_post);
                intent.putExtra("link_lamar",link_lamar);
                intent.putExtra("foto",foto);
                Toast.makeText(view.getContext(), "deskripsi nde adapter : " + diskripsi, Toast.LENGTH_SHORT).show();
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dl != null ? dl.size() : 0;
    }

    public class viewholderlengkap extends RecyclerView.ViewHolder {


        ImageView btn_simpan_lengkap,logo_lengkap;

        TextView nama_peru_lengkap, kaegori_lengkap, pekerjaan_lengkap;


        public viewholderlengkap(@NonNull View itemView) {
            super(itemView);
logo_lengkap = itemView.findViewById(R.id.logo_perusahaan_lengkap);
            btn_simpan_lengkap = itemView.findViewById(R.id.btn_simpan_lengkap);

            nama_peru_lengkap = itemView.findViewById(R.id.nama_tempat_pekerjaan_lengkap);
            kaegori_lengkap = itemView.findViewById(R.id.lengkap_kategori);
            pekerjaan_lengkap = itemView.findViewById(R.id.pekerjaan_lengkap);


        }
    }
}
