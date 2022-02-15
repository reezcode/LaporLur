package com.reaz.laporlur;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class DetailLaporan extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference refdb;
    SharedPreferences userlapor;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laporan);

        userlapor = this.getSharedPreferences("User_Lapor", Context.MODE_PRIVATE);
        userid = userlapor.getString("user", "missing");
        final SharedPreferences detaillaporan = this.getSharedPreferences("Detail_Laporan", Context.MODE_PRIVATE);
        String idlaporan = detaillaporan.getString("idlaporan", "missing");
        final TextView judullaporan = findViewById(R.id.judullaporan);
        final TextView isilaporan = findViewById(R.id.isilaporan);
        final TextView waktulapor = findViewById(R.id.waktulapor);
        final TextView emailpelapor = findViewById(R.id.emailpelapor);
        final TextView rtrwpelapor = findViewById(R.id.rtrwpelapor);
        final TextView dukuhpelapor = findViewById(R.id.pedukuhanpelapor);
        final TextView isitanggapan = findViewById(R.id.tanggapanisi);
        judullaporan.setText(idlaporan);
        database = FirebaseDatabase.getInstance();
        refdb = database.getReference();

        refdb.child("Laporan_Warga").child(userid).child(idlaporan).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshot1 = dataSnapshot.child("isi");
                DataSnapshot dataSnapshot2 = dataSnapshot.child("waktu");
                DataSnapshot dataSnapshot3 = dataSnapshot.child("email");
                DataSnapshot dataSnapshot4 = dataSnapshot.child("rtrw");
                DataSnapshot dataSnapshot5 = dataSnapshot.child("dukuh");
                DataSnapshot dataSnapshot6 = dataSnapshot.child("isitanggapan");
                String email = (String) dataSnapshot3.getValue();
                String rtrw = (String) dataSnapshot4.getValue();
                String dukuh = (String) dataSnapshot5.getValue();
                String waktu = (String) dataSnapshot2.getValue();
                String isi = (String) dataSnapshot1.getValue();
                String tanggapan = (String) dataSnapshot6.getValue();
                isitanggapan.setText(tanggapan);
                isilaporan.setText(isi);
                waktulapor.setText(waktu);
                emailpelapor.setText("Email : " + email);
                rtrwpelapor.setText("RT/RW : " + rtrw);
                dukuhpelapor.setText("Pedukuhan : " + dukuh);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                judullaporan.setText("Error Loading Data!");

            }
        });
    }
}
