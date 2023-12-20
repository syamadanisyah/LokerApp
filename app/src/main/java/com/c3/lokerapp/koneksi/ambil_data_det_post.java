package com.c3.lokerapp.koneksi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ambil_data_det_post {
@Expose
@SerializedName("id_post")
private String id_post;

private String logo;
    @Expose
    @SerializedName("nama")
private String nama;
    @Expose
    @SerializedName("judul_post")
private String judul_post;
    @Expose
    @SerializedName("deskripsi_post")
private  String deskripsi_post;
    @Expose
    @SerializedName("link_lamar")
private String link_lamar;

    @Expose
    @SerializedName("foto")
    private String foto;
private int id_akun;

//serialzedname berfungsi untuk mencocokan dari postman apabila di ambil Id untuk muncul tulisan data maka menggunakan seliazedname
@Expose
@SerializedName("kategori")
private String id_kategori;

//    public ambil_data_det_post(String foto,String id_post,String logo ,String nama, String judul_post, String deskripsi_post, String link_lamar, int id_akun, String id_kategori) {
//        this.id_post = id_post;
//        this.nama = nama;
//        this.judul_post = judul_post;
//        this.deskripsi_post = deskripsi_post;
//        this.link_lamar = link_lamar;
//        this.id_akun = id_akun;
//        this.id_kategori = id_kategori;
//        this.logo = logo;
//        this.foto = foto;
//    }


    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getId_post() {
        return id_post;
    }

    public void setId_post(String id_det_post) {
        this.id_post = id_det_post;
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

    public String getDeskripsi_post() {
        return deskripsi_post;
    }

    public void setDeskripsi_post(String deskripsi_post) {
        this.deskripsi_post = deskripsi_post;
    }

    public String getLink_lamar() {
        return link_lamar;
    }

    public void setLink_lamar(String link_lamar) {
        this.link_lamar = link_lamar;
    }

    public int getId_akun() {
        return id_akun;
    }

    public void setId_akun(int id_akun) {
        this.id_akun = id_akun;
    }

    public String getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(String id_kategori) {
        this.id_kategori = id_kategori;
    }

    @Override
    public String toString() {
        return "ambil_data_det_post{" +
                "id_post=" + id_post +
                ", nama='" + nama + '\'' +
                ", judul_post='" + judul_post + '\'' +
                ", deskripsi_post='" + deskripsi_post + '\'' +
                ", link_lamar='" + link_lamar + '\'' +
                ", id_akun=" + id_akun +
                ", id_kategori=" + id_kategori +
                '}';
    }


}
