package com.c3.lokerapp.util;

import android.content.Context;

import com.c3.lokerapp.koneksi.UserModel;
import com.c3.lokerapp.shared.DataShared;

public class UserUtil {
    private final DataShared dataShared;

    public UserUtil(Context context, UserModel model){
        dataShared = new DataShared(context);

        if (model != null){
            setId(model.getId_pelamar());
            setUsername(model.getUsername());
            setEmail(model.getEmail());
            setFullName(model.getNama_lengkap());
            setAlamat(model.getAlamat());


            /*setLevel(model.getLevel());*/
            /*setVerified(model.getVerified());*/
            /*setUserPhoto(model.getUserPhoto());*/
            /*setCreated(model.getCreatedAt());*/
        }
    }

    public UserUtil(Context context){
        this(context, null);
    }

    public boolean isSignIn(){
        return  dataShared.contains(DataShared.KEY.ACC_USERNAME) &&
                !dataShared.getData(DataShared.KEY.ACC_USERNAME).isEmpty();
    }

    public String getId(){
        return dataShared.getData(DataShared.KEY.ACC_ID_USER);
    }

    public void setId(String id){
        dataShared.setData(DataShared.KEY.ACC_ID_USER, id);
    }

    public String getUsername(){
        return dataShared.getData(DataShared.KEY.ACC_USERNAME);
    }

    public void setUsername(String username){
        dataShared.setData(DataShared.KEY.ACC_USERNAME, username);
    }

    public String getEmail(){
        return dataShared.getData(DataShared.KEY.ACC_EMAIL);
    }

    public void setEmail(String email){
        dataShared.setData(DataShared.KEY.ACC_EMAIL, email);
    }

    public String getFullName(){
        return dataShared.getData(DataShared.KEY.ACC_FULL_NAME);
    }

    public void setFullName(String name){
        dataShared.setData(DataShared.KEY.ACC_FULL_NAME, name);
    }

    public String getAlamat(){
        return dataShared.getData(DataShared.KEY.ACC_ALAMAT);
    }

    public void setAlamat(String alamat){
        dataShared.setData(DataShared.KEY.ACC_ALAMAT, alamat);
    }

    public String getVerified(){
        return dataShared.getData(DataShared.KEY.ACC_EMAIL_VERIFY);
    }

    public void setVerified(String verified){
        dataShared.setData(DataShared.KEY.ACC_EMAIL_VERIFY, verified);
    }

    public void setUserPhoto(String newUserPhoto){
        dataShared.setData(DataShared.KEY.ACC_PHOTO, newUserPhoto);
    }

    public String getUserPhoto(){
        return dataShared.getData(DataShared.KEY.ACC_PHOTO);
    }

    public String getCreated(){
        return dataShared.getData(DataShared.KEY.ACC_CREATED);
    }

    public void setCreated(String created){
        dataShared.setData(DataShared.KEY.ACC_CREATED, created);
    }

    public void signOut(){
        dataShared.setNullData(DataShared.KEY.ACC_ID_USER);
        dataShared.setNullData(DataShared.KEY.ACC_USERNAME);
        dataShared.setNullData(DataShared.KEY.ACC_EMAIL);
        dataShared.setNullData(DataShared.KEY.ACC_FULL_NAME);
       /* dataShared.setNullData(DataShared.KEY.ACC_LEVEL);*/
        dataShared.setNullData(DataShared.KEY.ACC_PHOTO);
        dataShared.setNullData(DataShared.KEY.ACC_EMAIL_VERIFY);
        dataShared.setNullData(DataShared.KEY.ACC_CREATED);
    }




}
