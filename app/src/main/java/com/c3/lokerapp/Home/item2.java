package com.c3.lokerapp.Home;

public class item2 {
    int img;

    String nama_peru_i, alamat_peru_1,title,pekerjaan,kategori_pekerjaan;

    public item2(int img, String nama_peru_i, String alamat_peru_1, String title, String pekerjaan, String kategori_pekerjaan) {
        this.img = img;
        this.nama_peru_i = nama_peru_i;
        this.alamat_peru_1 = alamat_peru_1;
        this.title = title;
        this.pekerjaan = pekerjaan;
        this.kategori_pekerjaan = kategori_pekerjaan;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getNama_peru_i() {
        return nama_peru_i;
    }

    public void setNama_peru_i(String nama_peru_i) {
        this.nama_peru_i = nama_peru_i;
    }

    public String getAlamat_peru_1() {
        return alamat_peru_1;
    }

    public void setAlamat_peru_1(String alamat_peru_1) {
        this.alamat_peru_1 = alamat_peru_1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getKategori_pekerjaan() {
        return kategori_pekerjaan;
    }

    public void setKategori_pekerjaan(String kategori_pekerjaan) {
        this.kategori_pekerjaan = kategori_pekerjaan;
    }
}
