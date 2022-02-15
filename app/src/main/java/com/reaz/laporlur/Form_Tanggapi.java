package com.reaz.laporlur;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Form_Tanggapi extends AppCompatActivity {

    SharedPreferences namauser, idtanggapi;
    FirebaseRecyclerOptions options;
    private DatabaseReference mDatabase;
    String userid;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<ListLaporan, DetailLapoanViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form__tanggapi);
        namauser = this.getSharedPreferences("NamaUser", Context.MODE_PRIVATE);
        idtanggapi = this.getSharedPreferences("ID_Tanggapan", Context.MODE_PRIVATE);
        userid = namauser.getString("user", "missing");
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Tanggapi - " + userid);
        mRecycler = findViewById(R.id.list_laporan);
        mRecycler.setHasFixedSize(true);
        mDatabase = FirebaseDatabase.getInstance().getReference("Laporan_Warga");
        mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);
        Query query = getQuery(mDatabase);

        options = new FirebaseRecyclerOptions.Builder<ListLaporan>()
                .setQuery(query, ListLaporan.class)
                .build();
        mAdapter = new FirebaseRecyclerAdapter<ListLaporan, DetailLapoanViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DetailLapoanViewHolder holder, int position, @NonNull final ListLaporan model) {
                holder.bindToLaporan(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String idtanggap = model.judul;
                        SharedPreferences.Editor tanggapedit = idtanggapi.edit();
                        tanggapedit.putString("id", idtanggap);
                        tanggapedit.apply();
                        startActivity(new Intent(getApplicationContext(), Detail_Tanggapi.class));
                    }
                });
            }

            @NonNull
            @Override
            public DetailLapoanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new DetailLapoanViewHolder(inflater.inflate(R.layout.laporanelement, viewGroup, false));
            }
        };
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);

    }
    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }

    private Query getQuery(DatabaseReference mDatabase){
        Query query = mDatabase.child(userid);
        return query;
    }

}
