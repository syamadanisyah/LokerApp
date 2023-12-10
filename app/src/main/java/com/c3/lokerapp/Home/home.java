package com.c3.lokerapp.Home;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c3.lokerapp.R;
import com.c3.lokerapp.cari.ResponseSearching;
import com.c3.lokerapp.cari.searching;
import com.c3.lokerapp.detail_pekerjaan;
import com.c3.lokerapp.koneksi.DetailResponse;
import com.c3.lokerapp.koneksi.RetrofitClient;
import com.c3.lokerapp.koneksi.RetrofitEndPoint;
import com.c3.lokerapp.koneksi.ambil_data_det_post;
import com.c3.lokerapp.lengkap;
import com.c3.lokerapp.lengkap2;
import com.c3.lokerapp.list_kategori.KategoriLainnyaModel;
import com.c3.lokerapp.list_kategori.ResponseModelKategoriLainnya;
import com.c3.lokerapp.list_kategori.adapterlistkategori;
import com.c3.lokerapp.list_kategori.list_kategori;
import com.c3.lokerapp.shared.DataShared;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

import com.c3.lokerapp.kategori.kategori;
import com.google.android.material.textfield.TextInputEditText;

public class home extends Fragment {


    private adapter1 adapter;
    private adapter2 adapter2;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private ImageView btnother;
    private View view;

    private List<ambil_data_det_post> itemlist = new ArrayList<>();


    private List<ambil_data_det_post> itemlist2 = new ArrayList<>();

    private final String TAG = "home";


    private List<item2> isi_item2() {

        List<item2> isiitem2 = new ArrayList<>();
        isiitem2.add(new item2(R.drawable.logo, "Pt.Indroprima", "jl.patimura", "Pendirikan S1/D4", "staff", "Admin"));
        isiitem2.add(new item2(R.drawable.logo, "Pt.Lintang NTT", "jl.Madyapura", "Pendirikan S1/D4", "davelpore web", "teknologi_informasi"));
        isiitem2.add(new item2(R.drawable.logo, "Pt.Handayani", "jl.Peru", "Pendirikan S1/D4", "davelpore web", "teknologi_informasi"));
        isiitem2.add(new item2(R.drawable.logo, "Pt.fajartimur", "jl.patih putih", "Pendirikan S1/D4", "davelpore web", "teknologi_informasi"));
        isiitem2.add(new item2(R.drawable.logo, "Pt.mandala indo", "jl.biru senja", "Pendirikan S1/D4", "davelpore web", "teknologi_informasi"));
        isiitem2.add(new item2(R.drawable.logo, "Pt.senja sekunder", "jl.raja", "Pendirikan S1/D4", "davelpore web", "teknologi_informasi"));
        isiitem2.add(new item2(R.drawable.logo, "Pt.krikwak", "jl.raja si singa", "Pendirikan S1/D4", "davelpore web", "teknologi_informasi"));
        isiitem2.add(new item2(R.drawable.logo, "Pt.kirawak", "jl.mura", "Pendirikan S1/D4", "davelpore web", "teknologi_informasi"));
        isiitem2.add(new item2(R.drawable.logo, "Pt.tb timur", "jl.patimura", "Pendirikan S1/D4", "davelpore web", "teknologi_informasi"));


        return isiitem2;
    }

    public void gantiDetail() {
        Intent i = new Intent(requireActivity(), detail_pekerjaan.class);
        startActivity(i);

    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static home newInstance(String param1, String param2) {
        home fragment = new home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void getDataFromAPI2() {
        if (isAdded()){
            RetrofitEndPoint endpoint2 = RetrofitClient.getInstance();
            recyclerView2 = view.findViewById(R.id.horizontalRecyclerView2);
            TextView kosong = view.findViewById(R.id.kosong);
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("prefLogin", getContext().MODE_PRIVATE);
            String id_pelamar = sharedPreferences.getString("id_pelamar", "");
            Call<DetailResponse> ambil2 = endpoint2.gunakadataspinner(id_pelamar);
            ambil2.enqueue(new Callback<DetailResponse>() {
                @Override
                public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                    if (response.isSuccessful()) {
                        List<ambil_data_det_post> data2 = response.body().getData();
                        adapter2 adapterkedua = new adapter2(requireContext(), data2);
                        recyclerView2.setAdapter(adapterkedua);

                        if (data2.size() <1){
                            kosong.setVisibility(View.VISIBLE);
                            recyclerView2.setVisibility(View.GONE);
                        }else {
                            kosong.setVisibility(View.GONE);
                            recyclerView2.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(getContext(), "erorr" + response.message(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<DetailResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        }

    }

    //ambil data dari database
    private void getDataFromAPI() {

        RetrofitEndPoint endpoint = RetrofitClient.getInstance();
        recyclerView = view.findViewById(R.id.horizontalRecyclerView);
        // recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        Call<DetailResponse> ambil = endpoint.ambildetpost();
        ambil.enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(requireContext(), "tidak ada", Toast.LENGTH_SHORT).show();


                    List<ambil_data_det_post> data = response.body().getData();
                    adapter1 adapterpertama = new adapter1(requireContext(), data);
                    recyclerView.setAdapter(adapterpertama);

                } else {
                    Toast.makeText(getContext(), "erorr" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
                t.printStackTrace();

            }
        });


    }




    TextInputEditText inputEditText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);




        /*isi_isi = isi_item();*/
//        isi_isi2 = isi_item2();
//        recyclerView2 = tampilan.findViewById(R.id.horizontalRecyclerView2);
        /*recyclerView = tampilan.findViewById(R.id.horizontalRecyclerView);*/
        getDataFromAPI();
        getDataFromAPI2();

        inputEditText = view.findViewById(R.id.searching_home);
        inputEditText.setFocusable(false);
        inputEditText.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), searching.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.layout_in,R.anim.layout_out);
        });


//        adapter2 = new adapter2(isi_isi2, new adapter2.tampilan2() {
//            @Override
//            public void onItemClick(int position) {
//                Toast.makeText(requireContext(), isi_isi2.get(position).getNama_peru_i(), Toast.LENGTH_SHORT).show();
//                gantiDetail();
//
//            }
//        });
//        recyclerView2.setAdapter(adapter2);

        TextView lengkap2 = view.findViewById(R.id.tv_detail2);
        lengkap2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireActivity(), com.c3.lokerapp.lengkap2.class));
                getActivity().overridePendingTransition(R.anim.layout_in,R.anim.layout_out);
            }
        });


        TextView lengkap = view.findViewById(R.id.tv_detail);
        lengkap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireActivity(), com.c3.lokerapp.lengkap.class));
                getActivity().overridePendingTransition(R.anim.layout_in,R.anim.layout_out);
            }
        });


        //button other kategori
         btnother = view.findViewById(R.id.btn_other);

        btnother.setOnClickListener(view -> {
            showbottomsheet();

            btnother.setClickable(false);

        });


        LinearLayout btnAdmin = view.findViewById(R.id.btn_admin);
        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(requireActivity(), kategori.class)
                                .putExtra(kategori.KATEGORI, kategori.ADMIN)
                );
                getActivity().overridePendingTransition(R.anim.layout_in,R.anim.layout_out);
            }
        });


        LinearLayout btnBank = view.findViewById(R.id.btn_bank);
        btnBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(requireActivity(), kategori.class)
                                .putExtra(kategori.KATEGORI, kategori.BANK)
                );
                getActivity().overridePendingTransition(R.anim.layout_in,R.anim.layout_out);
            }
        });


        LinearLayout btnGuru = view.findViewById(R.id.btn_guru);
        btnGuru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(requireActivity(), kategori.class)
                                .putExtra(kategori.KATEGORI, kategori.GURU)

                );
                getActivity().overridePendingTransition(R.anim.layout_in,R.anim.layout_out);

            }
        });


        LinearLayout btnKurir = view.findViewById(R.id.btn_kurir);
        btnKurir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(requireActivity(), kategori.class)
                                .putExtra(kategori.KATEGORI, kategori.KURIR)
                );
                getActivity().overridePendingTransition(R.anim.layout_in,R.anim.layout_out);

            }
        });


        LinearLayout btnResturant = view.findViewById(R.id.btn_staff_restorant);
        btnResturant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(requireActivity(), kategori.class)
                                .putExtra(kategori.KATEGORI, kategori.STAFFRESTURANT)
                );
                getActivity().overridePendingTransition(R.anim.layout_in,R.anim.layout_out);

            }
        });


        LinearLayout btnTeknologi = view.findViewById(R.id.btn_teknologi_informasi);
        btnTeknologi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(requireActivity(), kategori.class)
                                .putExtra(kategori.KATEGORI, kategori.TEKNOLOGI_INFORMASI)
                );
                getActivity().overridePendingTransition(R.anim.layout_in,R.anim.layout_out);

            }
        });

        LinearLayout btnkesehatan = view.findViewById(R.id.btn_tenaga_kesehatan);

        btnkesehatan.setOnClickListener(view -> {

            startActivity(
                    new Intent(requireActivity(), kategori.class)
                            .putExtra(kategori.KATEGORI, kategori.TENAGA_KESEHATAN));
            getActivity().overridePendingTransition(R.anim.layout_in,R.anim.layout_out);
        });


        return view;

    }

    private View view1;
    private RecyclerView recyclerViewKategori;

    private void showbottomsheet() {
        BottomSheetDialog sheet = new BottomSheetDialog(requireContext(), R.style.BottomSheetTheme);
        View sheetInflater = getLayoutInflater().inflate(R.layout.fragment_botton_seet, null);
        sheet.setContentView(sheetInflater);

        RetrofitEndPoint endpoint = RetrofitClient.getInstance();
        recyclerViewKategori = sheetInflater.findViewById(R.id.rec_list_kategori1);

        Call<ResponseModelKategoriLainnya> ambil = endpoint.KategoriLainnya();
        ambil.enqueue(new Callback<ResponseModelKategoriLainnya>() {
            @Override
            public void onResponse(Call<ResponseModelKategoriLainnya> call, Response<ResponseModelKategoriLainnya> response) {
                if (response.isSuccessful()) {
                    ArrayList<KategoriLainnyaModel> data = response.body().getData();
                    adapterlistkategori adapterpertama = new adapterlistkategori(requireContext(), data);
                    KategoriLainnyaModel datanya = new KategoriLainnyaModel();

                    // PROBLEM E STATUS E SUCCES
                    // TAPI KategoriLainnyaModel ISI NE KOSONG AS NULL:v


                    recyclerViewKategori.setAdapter(adapterpertama);
                } else {
                    Toast.makeText(getContext(), "erorr" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModelKategoriLainnya> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(requireContext(), "tidak ada", Toast.LENGTH_SHORT).show();
            }
        });

        sheet.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                btnother.setClickable(true);
                sheet.dismiss();
            }
        });

        //menam
        sheet.show();
        sheet.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        sheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sheet.getWindow().getAttributes().windowAnimations = R.style.BottomSheetAnim;
        sheet.getWindow().setGravity(Gravity.BOTTOM);
        ImageView btncancel = sheetInflater.findViewById(R.id.cancel);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
sheet.dismiss();
            }
        });
    }

    private void showbottomsheetOld() {


        final Dialog dialog_bottom_sheet = new Dialog(getActivity());
        dialog_bottom_sheet.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_bottom_sheet.setContentView(R.layout.bottom_sheet_layout);

        ImageView btncancel = dialog_bottom_sheet.findViewById(R.id.cancel);


        dialog_bottom_sheet.show();
        dialog_bottom_sheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //ambil di values/..themes dan mendklarasikan animasi
        dialog_bottom_sheet.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog_bottom_sheet.getWindow().setGravity(Gravity.BOTTOM);
    }


}