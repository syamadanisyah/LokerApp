package com.c3.lokerapp.cari;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class model_data_searching {
    @Expose
    @SerializedName("id_post")
    private String id_post;
    @Expose
    @SerializedName("nama")
    private String nama;
    @Expose
    @SerializedName("judul_post")
    private String judul_post;
    @Expose
    @SerializedName("deskripsi_post")
    private String deskripi_post;
    @Expose
    @SerializedName("link_lamar")
    private String link_lamar;
    @Expose
    @SerializedName("foto")
    private String foto;
    @Expose
    @SerializedName("id_akun")
    private String id_akun;
    @Expose
    @SerializedName("id_kategori")
    private String id_kategori;
    @Expose
    @SerializedName("kategori")
    private String kategori;

    public model_data_searching(String id_post, String nama, String judul_post, String deskripi_post, String link_lamar, String foto, String id_akun, String id_kategori, String kategori) {
        this.id_post = id_post;
        this.nama = nama;
        this.judul_post = judul_post;
        this.deskripi_post = deskripi_post;
        this.link_lamar = link_lamar;
        this.foto = foto;
        this.id_akun = id_akun;
        this.id_kategori = id_kategori;
        this.kategori = kategori;
    }

    public String getId_post() {
        return id_post;
    }

    public void setId_post(String id_post) {
        this.id_post = id_post;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJudul_post() {
        return judul_post;
    }

    public void setJudul_post(String judul_post) {
        this.judul_post = judul_post;
    }

    public String getDeskripi_post() {
        return deskripi_post;
    }

    public void setDeskripi_post(String deskripi_post) {
        this.deskripi_post = deskripi_post;
    }

    public String getLink_lamar() {
        return link_lamar;
    }

    public void setLink_lamar(String link_lamar) {
        this.link_lamar = link_lamar;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getId_akun() {
        return id_akun;
    }

    public void setId_akun(String id_akun) {
        this.id_akun = id_akun;
    }

    public String getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(String id_kategori) {
        this.id_kategori = id_kategori;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
}
