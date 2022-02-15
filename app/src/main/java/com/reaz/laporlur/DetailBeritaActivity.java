package com.reaz.laporlur;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailBeritaActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference refdb;
    String idberita;
    SharedPreferences saveBerita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_berita);
        saveBerita = this.getSharedPreferences("Berita", Context.MODE_PRIVATE);
         idberita = saveBerita.getString("berita", "missing");

        final TextView judulberita = findViewById(R.id.judulberita);
        final TextView authberita = findViewById(R.id.authorberita);
        final TextView waktuberita = findViewById(R.id.waktuberita);
        final TextView isiberita = findViewById(R.id.isiberita);
        final ImageView ivparalax = findViewById(R.id.ivparalax);
        judulberita.setText(idberita);

        database = FirebaseDatabase.getInstance();
        refdb = database.getReference();

        refdb.child("Berita").child(idberita).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshot1 = dataSnapshot.child("author");
                DataSnapshot dataSnapshot2 = dataSnapshot.child("judul");
                DataSnapshot dataSnapshot3 = dataSnapshot.child("waktu");
                DataSnapshot dataSnapshot4 = dataSnapshot.child("isi");
                DataSnapshot dataSnapshot5 = dataSnapshot.child("urlimage");
                String urlimage = (String) dataSnapshot3.getValue();
                String isi = (String) dataSnapshot4.getValue();
                String waktu = (String) dataSnapshot5.getValue();
                String judul = (String) dataSnapshot2.getValue();
                String author = (String) dataSnapshot1.getValue();

                authberita.setText(author);
                waktuberita.setText(urlimage);
                isiberita.setText(isi);
                Glide.with(DetailBeritaActivity.this).load(waktu).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivparalax);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DetailBeritaActivity.this, "Gagal Loading Berita!", Toast.LENGTH_LONG).show();

            }
        });
    }
}
