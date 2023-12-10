package com.c3.lokerapp.cari;

import com.c3.lokerapp.koneksi.ambil_data_det_post;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseSearching {

    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("data")
    private List<model_data_searching> data;


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

    public List<model_data_searching> getData() {
        return data;
    }

    public void setData(List<model_data_searching> data) {
        this.data = data;
    }
}
