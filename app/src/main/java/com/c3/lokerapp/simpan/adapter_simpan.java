package com.c3.lokerapp.simpan;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c3.lokerapp.R;

import java.util.List;

public class adapter_simpan extends RecyclerView.Adapter<adapter_simpan.viewholder_simpan> {

    List<item1_simpan1> listitem;

    private pindah pindah;

    public adapter_simpan(List<item1_simpan1> listitem, adapter_simpan.pindah pindah) {
        this.listitem = listitem;
        this.pindah = pindah;
    }

    @NonNull
    @Override
    public viewholder_simpan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View flater = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rc2, parent, false);
        return new viewholder_simpan(flater);
    }


    @Override
    public void onBindViewHolder(@NonNull viewholder_simpan holder, @SuppressLint("RecyclerView") int position) {
        item1_simpan1 item_simpan = listitem.get(position);
        holder.img_simpan.setImageResource(item_simpan.getImg());
        holder.txt_nama_simpan.setText(item_simpan.nama_peru_i2);
        holder.txt_alamat_simpan.setText(item_simpan.alamat_peru_2);
        holder.txt_title_simpan.setText(item_simpan.title2);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                    pindah.onItemClick(position);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class viewholder_simpan extends RecyclerView.ViewHolder {

        ImageView img_simpan;

        TextView txt_nama_simpan, txt_alamat_simpan, txt_title_simpan;

        public viewholder_simpan(@NonNull View itemView) {
            super(itemView);

            img_simpan = itemView.findViewById(R.id.logo2);
            txt_nama_simpan = itemView.findViewById(R.id.nama_peru2);
            txt_alamat_simpan = itemView.findViewById(R.id.alamat_peru2);
            txt_title_simpan = itemView.findViewById(R.id.title_peru2);


        }
    }

    public interface pindah {
        void onItemClick(int position);
    }
}
