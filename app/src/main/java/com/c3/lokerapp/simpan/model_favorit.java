package com.c3.lokerapp.simpan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class model_favorit {

    @Expose
    @SerializedName("id_simpan")
    private String id_simpan;

    @Expose
    @SerializedName("id_post")
    private String id_post;

    @Expose
    @SerializedName("id_pelamar")
    private String id_pelamar;

    @Expose
    @SerializedName("judul_post")
    private String judul_post;

    @Expose
    @SerializedName("deskripsi_post")
    private String deskripsi_post;
    @Expose
    @SerializedName("nama")
    private String nama;


    @Expose
    @SerializedName("kategori")
    private String id_kategori;

    @Expose
    @SerializedName("link_lamar")
    private String link_lamar;

    @Expose
    @SerializedName("foto")
    private String foto;

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getLink_lamar() {
        return link_lamar;
    }

    public void setLink_lamar(String link_lamar) {
        this.link_lamar = link_lamar;
    }

    public String getId_simpan() {
        return id_simpan;
    }

    public void setId_simpan(String id_simpan) {
        this.id_simpan = id_simpan;
    }

    public String getId_post() {
        return id_post;
    }

    public void setId_post(String id_post) {
        this.id_post = id_post;
    }

    public String getId_pelamar() {
        return id_pelamar;
    }

    public void setId_pelamar(String id_pelamar) {
        this.id_pelamar = id_pelamar;
    }

    public String getJudul_post() {
        return judul_post;
    }

    public void setJudul_post(String judul_post) {
        this.judul_post = judul_post;
    }

    public String getDeskripsi_post() {
        return deskripsi_post;
    }

    public void setDeskripsi_post(String deskripsi_post) {
        this.deskripsi_post = deskripsi_post;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(String id_kategori) {
        this.id_kategori = id_kategori;
    }
}
