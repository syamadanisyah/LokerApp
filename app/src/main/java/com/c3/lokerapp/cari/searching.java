package com.c3.lokerapp.cari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.c3.lokerapp.R;
import com.c3.lokerapp.koneksi.RetrofitClient;
import com.c3.lokerapp.koneksi.RetrofitEndPoint;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class searching extends AppCompatActivity {


    TextInputEditText inputEditText;
    RecyclerView rev_searching;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

        inputEditText = findViewById(R.id.searching);
        inputEditText.requestFocus();
        rev_searching = findViewById(R.id.rc_searching);

        inputEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_FLAG_NO_ENTER_ACTION) {
                // Hide the keyboard
                InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                if (!Objects.requireNonNull(inputEditText.getText()).toString().isEmpty()) {
                    RetrofitClient.getInstance().cari(inputEditText.getText().toString()).enqueue(new Callback<ResponseSearching>() {
                        @Override
                        public void onResponse(Call<ResponseSearching> call, Response<ResponseSearching> response) {
//                            if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")){

                                List<model_data_searching> cari = response.body().getData();
                                  adaptersearching ads = new adaptersearching(searching.this, cari);
                                  rev_searching.setAdapter(ads);
                                  
                                  if (cari.size() <= 0){
                                      Toast.makeText(searching.this, "data tidak ada", Toast.LENGTH_SHORT).show();
                                  }
//                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseSearching> call, Throwable t) {

                        }
                    });

                }

                return true;
            }
            return false;
        });

    }
    public void onBackPressed(){
        finish();
        overridePendingTransition(R.anim.layout_in,R.anim.layout_out);
    }
}