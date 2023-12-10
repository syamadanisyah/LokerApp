package com.c3.lokerapp.list_kategori;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KategoriLainnyaModel {
    @Expose
    @SerializedName("id_kategori")
    private String id_kategori;
    @Expose
    @SerializedName("kategori")
    private String kategori;

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(String id_kategori) {
        this.id_kategori = id_kategori;
    }
}
