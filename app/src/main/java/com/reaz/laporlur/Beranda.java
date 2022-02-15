package com.reaz.laporlur;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;



public class Beranda extends Fragment {

    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Berita, BeritaViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Beranda() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Beranda.
     */
    // TODO: Rename and change types and number of parameters
    public static Beranda newInstance(String param1, String param2) {
        Beranda fragment = new Beranda();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beranda, container, false);
        CardView logout = view.findViewById(R.id.logout);
        CardView loginbtn = view.findViewById(R.id.loginbtn);
        final SharedPreferences loginstatus = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        final SharedPreferences datanama = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
        boolean status = loginstatus.getBoolean("status", false);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AdminActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean clear = false;
                String clearname = "guest";
                SharedPreferences.Editor nameclear = datanama.edit();
                nameclear.putString("nama", clearname);
                nameclear.apply();
                SharedPreferences.Editor statusclear = loginstatus.edit();
                statusclear.putBoolean("status", clear);
                statusclear.apply();
                startActivity(new Intent(getActivity(), SplashActivity.class));
            }
        });

        CardView laporankubtn = view.findViewById(R.id.laporankubtn);
        CardView desaku = view.findViewById(R.id.desaku);

        if (!status) {
            logout.setVisibility(View.GONE);
            desaku.setVisibility(View.GONE);
        } else {
            loginbtn.setVisibility(View.GONE);
            laporankubtn.setVisibility(View.GONE);
        }



        CardView ceunahbtn = view.findViewById(R.id.ceunahbtn);
        CardView helpbtn = view.findViewById(R.id.helpbtn);
        CardView beritabtn = view.findViewById(R.id.berita);
        CardView kesehatanbtn = view.findViewById(R.id.kesehatan);
        CardView keamananbtn = view.findViewById(R.id.keamanan);

        beritabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BeritaActivity.class));
            }
        });

        kesehatanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), KesehatanActivity.class));
            }
        });

        keamananbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), KeamananActivity.class));
            }
        });

        laporankubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CekNamaActivity.class));
            }
        });

        ceunahbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Ceunah.class));
            }
        });

        helpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Help.class));
            }
        });

        final SharedPreferences saveBerita = getActivity().getSharedPreferences("Berita", Context.MODE_PRIVATE);



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
