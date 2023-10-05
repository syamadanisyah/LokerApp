package com.c3.lokerapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class adapter1 extends RecyclerView.Adapter<adapter1.viewholder1> {

    List<item1> itemslist;
    private tampilan tampil;

    public adapter1(List<item1> itemslist, tampilan tampil) {
        this.itemslist = itemslist;
        this.tampil = tampil;
    }

    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
View flater = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rc,parent,false);
        return new viewholder1(flater);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder1 holder, @SuppressLint("RecyclerView") int position) {
item1 listitem = itemslist.get(position);
holder.img.setImageResource(listitem.getImg());
holder.txt_nama.setText(listitem.getNama_peru_i());
        holder.txt_alamat.setText(listitem.getAlamat_peru_1());
        holder.txt_title.setText(listitem.getTitle());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.getAdapterPosition() != RecyclerView.NO_POSITION){
                    tampil.onItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemslist.size();
    }

    public static class viewholder1 extends RecyclerView.ViewHolder{
ImageView img;
TextView txt_nama,txt_alamat,txt_title;
    public viewholder1(@NonNull View itemView) {
        super(itemView);


        img = itemView.findViewById(R.id.logo);
        txt_nama = itemView.findViewById(R.id.nama_peru);
        txt_alamat = itemView.findViewById(R.id.alamat_peru);
        txt_title = itemView.findViewById(R.id.title_peru);

    }



}
    public interface tampilan{
        void onItemClick(int position);
    }
}
