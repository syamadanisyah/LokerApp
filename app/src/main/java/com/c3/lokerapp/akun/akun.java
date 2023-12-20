package com.c3.lokerapp.akun;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.c3.lokerapp.Login;
import com.c3.lokerapp.R;
import com.c3.lokerapp.koneksi.DetailResponse;
import com.c3.lokerapp.koneksi.RetrofitClient;
import com.c3.lokerapp.koneksi.RetrofitEndPoint;
import com.c3.lokerapp.koneksi.UsersResponse;
import com.c3.lokerapp.list_kategori.KategoriLainnyaModel;
import com.c3.lokerapp.list_kategori.ResponseModelKategoriLainnya;
import com.c3.lokerapp.registrasi_kategori;
import com.c3.lokerapp.shared.DataShared;
import com.c3.lokerapp.util.UserUtil;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link akun#newInstance} factory method to
 * create an instance of this fragment.
 */
public class akun extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MaterialTextView username, nama_lengkap, email, alamat;

    MaterialButton btn_edit, btn_logout;

    public akun() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment akun.
     */
    // TODO: Rename and change types and number of parameters
    public static akun newInstance(String param1, String param2) {
        akun fragment = new akun();
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


    Spinner spin1, spin2;
    RecyclerView recyclerview2;

    ArrayAdapter<String> spinnerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View tampilan = inflater.inflate(R.layout.fragment_akun, container, false);

        btn_edit = tampilan.findViewById(R.id.edit);

//menginisiasi component pada design berdasarkan idnya
        btn_logout = tampilan.findViewById(R.id.logout);
        username = tampilan.findViewById(R.id.username_profile);
        nama_lengkap = tampilan.findViewById(R.id.nama_lengkap_profile);
        email = tampilan.findViewById(R.id.email_profile);
        alamat = tampilan.findViewById(R.id.alamat_profile);

        // get data akun yang sedang login
       /* UserUtil util = new UserUtil(requireContext());
        username.setText(util.getUsername());
        nama_lengkap.setText(util.getFullName());
        email.setText(util.getEmail());
        alamat.setText(util.getAlamat());*/

        // Mengambil instance dari SharedPreferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("prefLogin", Context.MODE_PRIVATE);

// Mengambil nilai dari SharedPreferences
        String Username = sharedPreferences.getString("username", "");
        String Nama_lengkap = sharedPreferences.getString("nama_lengkap", "");
        String Email = sharedPreferences.getString("Email", "");
        String Alamat = sharedPreferences.getString("Alamat", "");

        Toast.makeText(getActivity(), "Email = " + Email, Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), "Alamat = " + Alamat, Toast.LENGTH_SHORT).show();

// Menetapkan nilai ke TextView
        username.setText(Username);
        nama_lengkap.setText(Nama_lengkap);
        email.setText(Email);
        alamat.setText(Alamat);


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireActivity(), edit_akun.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.layout_in, R.anim.layout_out);
            }
        });


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get data akun untuk logout
                UserUtil util = new UserUtil(requireContext());
                util.signOut();
                BersihkanAkun();
                startActivity(new Intent(requireActivity(), Login.class));
                getActivity().overridePendingTransition(R.anim.layout_in, R.anim.layout_out);
            }
        });


        spin1 = tampilan.findViewById(R.id.spinner1);
        spin2 = tampilan.findViewById(R.id.spinner2);




        SharedPreferences sharedPreferences1 = this.getContext().getSharedPreferences("prefLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        DataShared dataShared = new DataShared(requireContext());
        RetrofitEndPoint spinDb = RetrofitClient.getInstance();


        editor.putString("spiner1", Integer.toString(spin1.getSelectedItemPosition()));
        editor.putString("spiner2", Integer.toString(spin2.getSelectedItemPosition()));
        editor.apply();


        spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new ArrayList<>());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(spinnerAdapter);
        spin2.setAdapter(spinnerAdapter);


        RetrofitClient.getInstance().ambil_kate_db().enqueue(new Callback<ResponseModelKategoriLainnya>() {
            @Override
            public void onResponse(Call<ResponseModelKategoriLainnya> call, Response<ResponseModelKategoriLainnya> response) {
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {
                    ArrayList<KategoriLainnyaModel> models = response.body().getData();

                    ArrayList<String> kategori = new ArrayList<>();
                    for(KategoriLainnyaModel model : models){
                        kategori.add(model.getKategori());
                    }

                    // tampilkan spinner berdasarkan models : ketiken dewe :v ngelag anydesk e :) ws ngene tok? sng ngisor kode lama di hps ora?
                    // iyo dihapus ae
                    spinnerAdapter.addAll(kategori);
                    spinnerAdapter.notifyDataSetChanged();
                    // build di run? iyo ok


                } else {
                    Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModelKategoriLainnya> call, Throwable t) {
                Toast.makeText(requireContext(), "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        MaterialButton btn_kategori = tampilan.findViewById(R.id.simpan_kategori);
        btn_kategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pilihan1 = spin1.getSelectedItemPosition();
                int pilihan2 = spin2.getSelectedItemPosition();

                // Mengambil SharedPreferences dengan nama "prefLogin"
                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("prefLogin", Context.MODE_PRIVATE);

                // Mengambil editor untuk mengubah nilai SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                // Mengambil id_pelamar dari objek user dan menyimpannya di SharedPreferences
                String id_pelamar = sharedPreferences.getString("id_pelamar", "");
                editor.putString("id_pelamar", id_pelamar);

                // Menyimpan perubahan
                editor.apply();

                Toast.makeText(requireContext(), spin1.getSelectedItem() + " -- " + spin2.getSelectedItem(), Toast.LENGTH_SHORT).show();

                RetrofitEndPoint endp = RetrofitClient.getInstance();
                Call<DetailResponse> c = endp.data_spinner(
                        sharedPreferences.getString("id_pelamar", ""), // Menggunakan id_pelamar yang telah disimpan di SharedPreferences
                        spin1.getSelectedItem().toString(),
                        spin2.getSelectedItem().toString()
                );

                c.enqueue(new Callback<DetailResponse>() {
                    @Override
                    public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(requireContext(), "berhasil di ubah", Toast.LENGTH_SHORT).show();
                            editor.putString("spiner1", Integer.toString(spin1.getSelectedItemPosition()));
                            editor.putString("spiner2", Integer.toString(spin2.getSelectedItemPosition()));
                            editor.apply();
                        } else {
                            Toast.makeText(requireContext(), "gagal di ubah", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailResponse> call, Throwable t) {
                        // Handle failure
                    }
                });
            }
        });

        int posisi1 = Integer.parseInt(sharedPreferences.getString("spiner1", "0"));
        int posisi2 = Integer.parseInt(sharedPreferences.getString("spiner2", "0"));

        spin1.setSelection(posisi1);
        spin2.setSelection(posisi2);

        // Mengambil id_pelamar dari objek user dan menyimpannya di SharedPreferences
//        String id_pelamar = sharedPreferences.getString("id_pelamar", "");
//        RetrofitClient.getInstance().spinner_tidak_berubah(id_pelamar).enqueue(new Callback<ResponseModelKategoriLainnya>() {
//            @Override
//            public void onResponse(Call<ResponseModelKategoriLainnya> call, Response<ResponseModelKategoriLainnya> response) {
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {
//                    ArrayList<KategoriLainnyaModel> models = response.body().getData();
//
//                    int position = 0, position2;
//                    String item1 = models.get(0).getKategori();
//                    String item2 = models.get(1).getKategori();
//
//                    List<String> items = new ArrayList<>();
//
//                    // Iterate through the adapter to get all items
//                    for (int i = 0; i < spin1.getCount(); i++) {
//                        items.add(spin1.getItemAtPosition(0).toString());
//                    }
//
//                    for (int i = 0; i < items.size(); i++) {
//                        if (items.get(i).equals(item1)) {
//                            position = i;
//                            break;
//                        }
//                    }
//
//                spin1.setSelection(position);
//            }
//        }
//
//        @Override
//        public void onFailure (Call < ResponseModelKategoriLainnya > call, Throwable t){
//
//        }
//    });

        return tampilan;
    }

    private void BersihkanAkun() {
        SharedPreferences sharedPreferencesedit = getActivity().getSharedPreferences("prefLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesedit.edit();
        editor.putString("id_pelamar", null);
        editor.putString("nama_lengkap", null);
        editor.putString("username", null);
        editor.putString("password", null);
        editor.putString("no_telp", null);
        editor.putString("alamat", null);
        editor.putString("email", null);
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Mengambil instance dari SharedPreferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("prefLogin", Context.MODE_PRIVATE);

// Mengambil nilai dari SharedPreferences
        String Username = sharedPreferences.getString("username", "");
        String Nama_lengkap = sharedPreferences.getString("nama_lengkap", "");
        String Email = sharedPreferences.getString("Email", "");
        String Alamat = sharedPreferences.getString("Alamat", "");

        Toast.makeText(getActivity(), "Email = " + Email, Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), "Alamat = " + Alamat, Toast.LENGTH_SHORT).show();

// Menetapkan nilai ke TextView
        username.setText(Username);
        nama_lengkap.setText(Nama_lengkap);
        email.setText(Email);
        alamat.setText(Alamat);
    }
}

