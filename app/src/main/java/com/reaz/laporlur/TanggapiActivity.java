package com.reaz.laporlur;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class TanggapiActivity extends AppCompatActivity {

    SharedPreferences namauser;
    FirebaseRecyclerOptions options;
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<ListLUser, ListUserViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanggapi);
        namauser = this.getSharedPreferences("NamaUser", Context.MODE_PRIVATE);
        mRecycler = findViewById(R.id.list_user);
        mRecycler.setHasFixedSize(true);
        mDatabase = FirebaseDatabase.getInstance().getReference("Data_Warga_V2");
        mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);
        Query query = getQuery(mDatabase);

        options = new FirebaseRecyclerOptions.Builder<ListLUser>()
                .setQuery(query, ListLUser.class)
                .build();
        mAdapter = new FirebaseRecyclerAdapter<ListLUser, ListUserViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ListUserViewHolder holder, int position, @NonNull final ListLUser model) {
                holder.bindToLaporan(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String namauser_txt = model.nama;
                        SharedPreferences.Editor namauser_edit = namauser.edit();
                        namauser_edit.putString("user",namauser_txt);
                        namauser_edit.apply();
                        startActivity(new Intent(getApplicationContext(), Form_Tanggapi.class));
                    }
                });
            }

            @NonNull
            @Override
            public ListUserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new ListUserViewHolder(inflater.inflate(R.layout.listuser_element, viewGroup, false));
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
        Query query = mDatabase;
        return query;
    }
}
