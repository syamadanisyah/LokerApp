package com.c3.lokerapp.koneksi;

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
            @Field("id_pelamar") String id_Pelamar,
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

    @GET("users/cek_user.php")
    Call<UsersResponse> cekUser(
            @Query("email") String email
    );

    @FormUrlEncoded
    @POST("users/login.php")
    Call<UsersResponse> login (
            @Field("email") String email,
            @Field("password") String password
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
