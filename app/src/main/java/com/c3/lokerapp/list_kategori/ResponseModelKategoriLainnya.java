package com.c3.lokerapp.list_kategori;

import androidx.annotation.ArrayRes;

import com.c3.lokerapp.cari.model_data_searching;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResponseModelKategoriLainnya {
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("data")
    private ArrayList<KategoriLainnyaModel> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<KategoriLainnyaModel> getData() {
        return data;
    }

    public void setData(ArrayList<KategoriLainnyaModel> data) {
        this.data = data;
    }
}
