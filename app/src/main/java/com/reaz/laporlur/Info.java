package com.reaz.laporlur;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Info.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Info#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Info extends Fragment {
    FirebaseDatabase database;
    DatabaseReference refdb;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Info() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Info.
     */
    // TODO: Rename and change types and number of parameters
    public static Info newInstance(String param1, String param2) {
        Info fragment = new Info();
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
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        final TextView namadesa= view.findViewById(R.id.namadesa);
        final TextView letakdesa = view.findViewById(R.id.letakdesa);
        final TextView namadesahead = view.findViewById(R.id.namadesahead);
        final TextView letakdesahead = view.findViewById(R.id.letakdesahead);
        final TextView luasdesa = view.findViewById(R.id.luasdesa);
        final TextView jumlahpenduduk = view.findViewById(R.id.jumlahpenduduk);


        String desa = "Gembong Dadi";
        database = FirebaseDatabase.getInstance();
        refdb = database.getReference();

        refdb.child("Daftar_Desa").child(desa).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshot1 = dataSnapshot.child("namadesa");
                DataSnapshot dataSnapshot2 = dataSnapshot.child("letak");
                DataSnapshot dataSnapshot3 = dataSnapshot.child("luasdesa");
                DataSnapshot dataSnapshot4 = dataSnapshot.child("jumlahpenduduk");
                String bm = (String) dataSnapshot3.getValue();
                String cm = (String) dataSnapshot4.getValue();
                String am = (String) dataSnapshot2.getValue();
                String em = (String) dataSnapshot1.getValue();
                namadesa.setText("Nama Desa : "+ em);
                namadesahead.setText("Desa " + em);
                letakdesa.setText("Letak Desa : " + am);
                letakdesahead.setText(am);
                luasdesa.setText("Luas Desa : " + bm);
                jumlahpenduduk.setText("Jumlah Penduduk : " + cm);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                namadesa.setText("Error Loading Data!");

            }
        });


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
