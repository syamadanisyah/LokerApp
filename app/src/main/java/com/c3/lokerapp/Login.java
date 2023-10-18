package com.c3.lokerapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.c3.lokerapp.google.GoogleUsers;
import com.c3.lokerapp.koneksi.RetrofitClient;
import com.c3.lokerapp.koneksi.UsersResponse;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {


    EditText user,pass;
    MaterialButton btnlogin;

    private GoogleUsers googleUsers;

    ImageView btngoogle;


    boolean passwordvisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        googleUsers = new GoogleUsers(this);

        user = findViewById(R.id.txtusernamelog);
        pass = findViewById(R.id.txtpasswordlog);
        btnlogin = findViewById(R.id.btnlogin);


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Login.this, user.getText().toString() + " // " + pass.getText().toString(), Toast.LENGTH_SHORT).show();

                RetrofitClient.getInstance().loginCaker(user.getText().toString(), pass.getText().toString())
                        .enqueue(new Callback<UsersResponse>() {
                            @Override
                            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                                Log.e("test", "oi");
                                if(response.body() != null && response.body().getStatus().equalsIgnoreCase("success")){

                                    Toast.makeText(getApplicationContext(), "Login berhasil", Toast.LENGTH_SHORT).show();


                                    // jajalen login
                                    startActivity(new Intent(Login.this, dashboard.class)); // iki ne sg salah
                                }else{
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UsersResponse> call, Throwable t) {
                                Log.e("test", "err");
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });


        btngoogle = findViewById(R.id.google);


        btngoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(googleUsers.getIntent(), GoogleUsers.REQUEST_CODE);
            }
        });



        pass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
final int right = 2;

if (motionEvent.getAction() == MotionEvent.ACTION_UP){

    if(motionEvent.getRawX()>= pass.getRight()-pass.getCompoundDrawables()[right].getBounds().width()){
int pilih = pass.getSelectionEnd();
if(passwordvisible){

// menaruh gambar lain disini
    pass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.visibility_off,0);

    //fungsi untuk hiden password
pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
passwordvisible = false;


}else {

    // menaruh gambar lain disini
    pass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.visibility,0);

    //fungsi untuk hiden password
    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
    passwordvisible = true;



}

pass.setSelection(pilih);

return true;
    }
}


                return false;
            }
        });





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        googleUsers.onActivityResult(requestCode, resultCode, data);
    }
}