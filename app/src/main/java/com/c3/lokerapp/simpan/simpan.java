package com.c3.lokerapp.simpan;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link simpan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class simpan extends Fragment {


    private List<item1_simpan1> data_item;

    private RecyclerView recview_simpan;

    private adapter_simpan adapter_s;


    private List<item1_simpan1> data_item(){
        List<item1_simpan1> data_item = new ArrayList<>();
        data_item.add(new item1_simpan1(R.drawable.logo,"Pt.Indroprima","jl.patimura","Pendirikan S1/D4"));
        data_item.add(new item1_simpan1(R.drawable.logo,"Pt.Lintang NTT","jl.Madyapura","Pendirikan S1/D4"));
        data_item.add(new item1_simpan1(R.drawable.logo,"Pt.Handayani","jl.Peru","Pendirikan S1/D4"));
        data_item.add(new item1_simpan1(R.drawable.logo,"Pt.fajartimur","jl.patih putih","Pendirikan S1/D4"));
        data_item.add(new item1_simpan1(R.drawable.logo,"Pt.mandala indo","jl.biru senja","Pendirikan S1/D4"));
        data_item.add(new item1_simpan1(R.drawable.logo,"Pt.senja sekunder","jl.raja","Pendirikan S1/D4"));
        data_item.add(new item1_simpan1(R.drawable.logo,"Pt.krikwak","jl.raja si singa","Pendirikan S1/D4"));
        data_item.add(new item1_simpan1(R.drawable.logo,"Pt.kirawak","jl.mura","Pendirikan S1/D4"));
        data_item.add(new item1_simpan1(R.drawable.logo,"Pt.tb timur","jl.patimura","Pendirikan S1/D4"));


        return data_item;
    }

    public void pindah_detail(){
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

    public simpan() {
        // Required empty public constructor
    }

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View pindah= inflater.inflate(R.layout.fragment_simpan, container, false);

recview_simpan = pindah.findViewById(R.id.recyview_simpan);

adapter_s = new adapter_simpan(data_item,new adapter_simpan.pindah(){

    public void onItemCLick(int position){
        Toast.makeText(requireContext(), data_item.get(position).nama_peru_i2, Toast.LENGTH_SHORT).show();
    }


});



        // Inflate the layout for this fragment
        return pindah;

    }
}