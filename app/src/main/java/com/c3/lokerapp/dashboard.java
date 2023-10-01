package com.c3.lokerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class dashboard extends AppCompatActivity {
BottomNavigationView nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        nav = findViewById(R.id.navigasi_bottom);
nav.setOnNavigationItemSelectedListener(item -> {
    Fragment selectFragment = null;

    if (item.getItemId()==R.id.home){
        selectFragment = new home();
    }
    else if(item.getItemId()==R.id.simpan){
        selectFragment = new simpan();
    }

    else if(item.getItemId()==R.id.akun){
        selectFragment = new akun();
    }

    if (selectFragment != null){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame,selectFragment)
                .commit();
    }
    return true;
});

getSupportFragmentManager().beginTransaction()
        .replace(R.id.frame,new home()).commit();

    }
}