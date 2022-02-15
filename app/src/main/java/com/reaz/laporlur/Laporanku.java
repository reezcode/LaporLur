package com.reaz.laporlur;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Laporanku extends AppCompatActivity {

    SharedPreferences userlapor, detaillaporan;
    String userid;
    FirebaseRecyclerOptions options;
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<ListLaporan, DetailLapoanViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporanku);
        detaillaporan = this.getSharedPreferences("Detail_Laporan", Context.MODE_PRIVATE);
        userlapor = this.getSharedPreferences("User_Lapor", Context.MODE_PRIVATE);
        userid = userlapor.getString("user", "missing");
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
                        String idlaporan = model.judul;
                        SharedPreferences.Editor laporedit = detaillaporan.edit();
                        laporedit.putString("idlaporan", idlaporan);
                        laporedit.apply();
                        startActivity(new Intent(getApplicationContext(), DetailLaporan.class));
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
