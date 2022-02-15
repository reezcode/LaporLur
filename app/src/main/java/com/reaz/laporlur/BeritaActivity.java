package com.reaz.laporlur;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class BeritaActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Berita, BeritaViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.cyandark));
        }

        final SharedPreferences saveBerita = this.getSharedPreferences("Berita", Context.MODE_PRIVATE);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecycler = findViewById(R.id.list_berita);
        mRecycler.setHasFixedSize(true);

        mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        Query query = getQuery(mDatabase);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Berita>()
                .setQuery(query, Berita.class)
                .build();

        mAdapter = new FirebaseRecyclerAdapter<Berita, BeritaViewHolder>(options) {
            @Override
            public BeritaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                return new BeritaViewHolder(inflater.inflate(R.layout.cardelement, parent, false));
            }

            @Override
            protected void onBindViewHolder(@NonNull BeritaViewHolder holder, int position, @NonNull final Berita model) {
                holder.bindToBerita(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String berita = model.judul;
                        SharedPreferences.Editor beritaedit = saveBerita.edit();
                        beritaedit.putString("berita", berita);
                        beritaedit.apply();
                        startActivity(new Intent(getApplicationContext(), DetailBeritaActivity.class));
                    }
                });
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
        Query query = mDatabase.child("Berita");
        return query;
    }

}
