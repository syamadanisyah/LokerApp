package com.c3.lokerapp.koneksi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class model_cek_email {


    @Expose
    @SerializedName("email")
    private String email;

    public model_cek_email(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
