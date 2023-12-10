package com.c3.lokerapp.koneksi;

import com.c3.lokerapp.cari.ResponseSearching;
import com.c3.lokerapp.list_kategori.ResponseModelKategoriLainnya;
import com.c3.lokerapp.simpan.FavoritRespon;
import com.google.gson.annotations.Expose;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitEndPoint {

    @FormUrlEncoded
    @POST("registrasi.php")
    Call<UsersResponse> registerCaker(
            @Field("username") String username,
            @Field("password") String password,
            @Field("nama_lengkap") String nama_lengkap,
            @Field("no_telp") String no_telp,
            @Field("alamat") String alamat,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("checklogin.php")
    Call<UsersResponse> loginCaker(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("controllers/mobile/users/login.php")
    Call<UsersResponse> loginHaqi(
            @Field("email") String username,
            @Field("password") String password
    );

    //php mu pye? ambildata jeneng e deskripsi iku data sng ga enek nng RC tapi ape tak tambahi dwe.iku manut adapter ora? ngga,gamasalah tetep iso manggil og
    @GET("users/cek_user.php")
    Call<UsersResponse> cekUser(
            @Query("email") String email
    );

    @FormUrlEncoded
    @POST("users/login.php")
    Call<UsersResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("google_login.php")
    Call<UsersResponse> google_login(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("mail.php")
    Call<VerifyResponse> sendEmail(
            @Field("email") String email,
            @Field("type") String type,
            @Field("action") String action
    );


    @FormUrlEncoded
    @POST("update_pw.php")
    Call<UsersResponse> updatePassword(
            @Field("email") String email,
            @Field("password") String password
    );


    @GET("ambil_data.php")
    Call<DetailResponse> ambildetpost(

    );

    @FormUrlEncoded
    @POST("ambil_data_spesifik.php")
    Call<DetailResponse> ambildataspesifik(
            @Field("id_kategori") String id_kategori
    );

    @GET("ambil_data_lengkap.php")
    Call<DetailResponse> ambildatabawah(
            @Query("id_pelamar") String id_pelamar

    );

    @FormUrlEncoded
    @POST("ambil_data_spinner.php")
    Call<DetailResponse> data_spinner(
            @Field("id_pelamar") String id_pelamar,
            @Field("pilihan1") String pilihan1,
            @Field("pilihan2") String pilihan2
    );

    @FormUrlEncoded
    @POST("gunakan_data_spinner.php")
    Call<DetailResponse> gunakadataspinner(
            @Field("id_pelamar") String id_pelamar

    );

    @FormUrlEncoded
    @POST("gunakan_data_spinner_lengkap.php")
    Call<DetailResponse> gunakadataspinnerlengkap(
            @Field("id_pelamar") String id_pelamar

    );


    @GET("menyimpan_favorit.php")
    Call<DetailResponse> simpanfavorit(
            @Query("id_pelamar") String id_pelamar,
            @Query("id_post") String id_post
    );

    @FormUrlEncoded
    @POST("cek_favorit.php")
    Call<DetailResponse> cekfavorit(
            @Field("id_pelamar") String id_pelamar,
            @Field("id_post") String id_post
    );

    @FormUrlEncoded
    @POST("menampilkan_data_favorit.php")
    Call<FavoritRespon> menampilkanfavorit(
            @Field("id_pelamar") String id_pelamar
    );

    @GET("hapus_data_favorit.php")
    Call<FavoritRespon> hapusfavorit(
            @Query("id_pelamar") String id_pelamar,
            @Query("id_post") String id_post
    );

    @FormUrlEncoded
    @POST("searching.php")
    Call<ResponseSearching> cari(
            @Field("cari") String cari

    );

    @GET("list_kategori.php")
    Call<ResponseModelKategoriLainnya> KategoriLainnya(
    );


    @GET("cekemail.php")
    Call<Responcekemail> cekemail(
            @Query("email") String email
    );

    @FormUrlEncoded
    @POST("spinner_tidak_berubah.php")
    Call<ResponseModelKategoriLainnya> spinner_tidak_berubah(
            @Field("id_pelamar") String id_pelamar
    );

    @GET("ambil_data_id_pelamar.php")
    Call<UsersResponse> ambil_id_pelamar(
      @Query("email") String email
    );
@FormUrlEncoded
    @POST("edit_akun.php")
    Call<UsersResponse> edit_akun(
      @Field("id_pelamar") String id_pelamar,
      @Field("username") String username,
      @Field("nama_lengkap") String nama_lengkap,
      @Field("alamat") String alamat,
      @Field("email") String email
    );

 /*   @FormUrlEncoded
    @POST("users/login_google.php")
    Call<UsersResponse> loginGoogle(
            @Field("email") String email
    );*/

 /*   @FormUrlEncoded
    @POST("users/register.php")
    Call<UsersResponse> register(
            @Field("username") String username,
            @Field("email") String email,
            @Field("full_name") String fullName,
            @Field("password") String password
    );*/

  /*  @FormUrlEncoded
    @POST("users/register_google.php")
    Call<UsersResponse> registerGoogle(
            @Field("username") String username,
            @Field("email") String email,
            @Field("full_name") String fullName,
            @Field("password") String password
    );*/

   /* @FormUrlEncoded
    @POST("users/update_pw.php")
    Call<UsersResponse> updatePassword(
            @Field("email") String email,
            @Field("password") String password
    );*/

   /* @FormUrlEncoded
    @POST("users/update_pp.php")
    Call<UsersResponse> uploadPhotoBase64(
            @Field("email") String email,
            @Field("photo") String photo);*/


}
