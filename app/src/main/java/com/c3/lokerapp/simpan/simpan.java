package com.c3.lokerapp.simpan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.c3.lokerapp.Home.item1;
import com.c3.lokerapp.R;
import com.c3.lokerapp.detail_pekerjaan;
import com.c3.lokerapp.koneksi.RetrofitClient;
import com.c3.lokerapp.koneksi.RetrofitEndPoint;
import com.c3.lokerapp.koneksi.UserModel;
import com.c3.lokerapp.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link simpan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class simpan extends Fragment {

    private List<item1_simpan1> data_item;

    private RecyclerView recview_simpan;

    private adapter_simpan adapter_s;


    private List<item1_simpan1> data_item() {
        List<item1_simpan1> data_item = new ArrayList<>();
        data_item.add(new item1_simpan1(R.drawable.logo, "Pt.Indroprima", "jl.patimura", "Pendirikan S1/D4"));
        data_item.add(new item1_simpan1(R.drawable.logo, "Pt.Lintang NTT", "jl.Madyapura", "Pendirikan S1/D4"));
        data_item.add(new item1_simpan1(R.drawable.logo, "Pt.Handayani", "jl.Peru", "Pendirikan S1/D4"));
        data_item.add(new item1_simpan1(R.drawable.logo, "Pt.fajartimur", "jl.patih putih", "Pendirikan S1/D4"));
        data_item.add(new item1_simpan1(R.drawable.logo, "Pt.mandala indo", "jl.biru senja", "Pendirikan S1/D4"));
        data_item.add(new item1_simpan1(R.drawable.logo, "Pt.senja sekunder", "jl.raja", "Pendirikan S1/D4"));
        data_item.add(new item1_simpan1(R.drawable.logo, "Pt.krikwak", "jl.raja si singa", "Pendirikan S1/D4"));
        data_item.add(new item1_simpan1(R.drawable.logo, "Pt.kirawak", "jl.mura", "Pendirikan S1/D4"));
        data_item.add(new item1_simpan1(R.drawable.logo, "Pt.tb timur", "jl.patimura", "Pendirikan S1/D4"));


        return data_item;
    }

    public void pindah_detail() {
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


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment simpan.
     */
    // TODO: Rename and change types and number of parameters
    public static simpan newInstance(String param1, String param2) {
        simpan fragment = new simpan();
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
//    UserUtil util = new UserUtil(getContext());

//    String id_pelamar = util.getId();

    private void getDataSimpan() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("prefLogin", getContext().MODE_PRIVATE);
        String id_pelamar = sharedPreferences.getString("id_pelamar", "");
        RetrofitClient.getInstance().menampilkanfavorit(id_pelamar).enqueue(new Callback<FavoritRespon>() {
            @Override
            public void onResponse(Call<FavoritRespon> call, Response<FavoritRespon> response) {
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {

                    List<model_favorit> favorit = response.body().getData();
                    adapter_simpan as = new adapter_simpan(requireContext(),favorit, new adapter_simpan.pindah() {
                        @Override
                        public void onItemClick(int position) {
                            startActivity(new Intent(getActivity(), detail_pekerjaan.class));
                            {
                                String nama = favorit.get(position).getNama();
                                String pekerjaan = favorit.get(position).getJudul_post();
                                String kategori = favorit.get(position).getId_kategori();
                                String diskripsi = favorit.get(position).getDeskripsi_post();
                                String id_post = favorit.get(position).getId_post();

//ok disconnect en anydec oke siap
                                Intent intent = new Intent(getActivity(), detail_pekerjaan.class);
                                intent.putExtra("nama", nama);
                                intent.putExtra("judul_post", pekerjaan);
                                intent.putExtra("id_kategori", kategori);
                                intent.putExtra("deskripsi_post", diskripsi);
                                intent.putExtra("id_post", id_post);
                                Toast.makeText(getActivity(), "deskripsi nde adapter : " + diskripsi, Toast.LENGTH_SHORT).show();
                                getActivity().startActivity(intent);

                            }
                        }
                    });

                    recview_simpan.setAdapter(as);
                }

            }

            @Override
            public void onFailure(Call<FavoritRespon> call, Throwable t) {

            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        getDataSimpan();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View pindah = inflater.inflate(R.layout.fragment_simpan, container, false);

        recview_simpan = pindah.findViewById(R.id.recyview_simpan);

        data_item = data_item();

       /* adapter_s = new adapter_simpan(data_item, new adapter_simpan.pindah() {

            @Override
            public void onItemClick(int position) {
                Toast.makeText(requireContext(), data_item.get(position).getNama_peru_i2(), Toast.LENGTH_SHORT).show();

                pindah_detail();


            }
        });*/
        // recview_simpan.setAdapter(adapter_s);
        // Inflate the layout for this fragment
        getDataSimpan();
        return pindah;

    }
}