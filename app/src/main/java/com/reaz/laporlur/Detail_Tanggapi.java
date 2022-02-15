package com.reaz.laporlur;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Detail_Tanggapi extends AppCompatActivity {

    SharedPreferences idtanggapan, iduser;
    TextView namauser, judul, waktu, lokasi, isitanggapan, tanggapan_txt;
    String userid, isitanggapan_txt, idtanggap;
    Button editbtn, krmbtn;
    EditText editText;
    FirebaseDatabase database;
    DatabaseReference refdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__tanggapi);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.greendark));
        }
        tanggapan_txt = findViewById(R.id.isitanggapan_sudah);
        editbtn = findViewById(R.id.editbtn);
        tanggapan_txt.setVisibility(View.GONE);
        editbtn.setVisibility(View.GONE);
        krmbtn = findViewById(R.id.btn_kirim);
        idtanggapan = this.getSharedPreferences("ID_Tanggapan", Context.MODE_PRIVATE);
        idtanggap = idtanggapan.getString("id", "missing");

         editText = findViewById(R.id.isi_tanggapan);

        iduser = this.getSharedPreferences("NamaUser", Context.MODE_PRIVATE);
        userid = iduser.getString("user", "missing");

        namauser = findViewById(R.id.namauser_tanggapi);
        judul = findViewById(R.id.judul_tanggapi);
        waktu = findViewById(R.id.waktu_tanggapi);
        lokasi = findViewById(R.id.lok_tanggapi);
        isitanggapan = findViewById(R.id.isi_tanggapi);
        judul.setText(idtanggap);


        database = FirebaseDatabase.getInstance();
        refdb = database.getReference();

        refdb.child("Laporan_Warga").child(userid).child(idtanggap).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshot1 = dataSnapshot.child("isi");
                DataSnapshot dataSnapshot2 = dataSnapshot.child("waktu");
                DataSnapshot dataSnapshot3 = dataSnapshot.child("email");
                DataSnapshot dataSnapshot4 = dataSnapshot.child("rtrw");
                DataSnapshot dataSnapshot5 = dataSnapshot.child("dukuh");
                DataSnapshot dataSnapshot6 = dataSnapshot.child("isitanggapan");
                DataSnapshot dataSnapshot7 = dataSnapshot.child("tanggapan");


                String email = (String) dataSnapshot3.getValue();
                String rtrw = (String) dataSnapshot4.getValue();
                String dukuh = (String) dataSnapshot5.getValue();
                String waktutxt = (String) dataSnapshot2.getValue();
                String isi = (String) dataSnapshot1.getValue();
                String isian = (String) dataSnapshot6.getValue();
                isitanggapan.setText(" ' "+ isi + " ' ");
                namauser.setText(email);
                waktu.setText(waktutxt);
                tanggapan_txt.setText(isian);
                lokasi.setText("RT/RW " + rtrw + " Dk." + dukuh);

                String tanggapan = (String) dataSnapshot7.getValue();
                if (tanggapan != null && tanggapan.equals("https://firebasestorage.googleapis.com/v0/b/lapor-lur.appspot.com/o/check.png?alt=media&token=2d2a109e-b11d-4e83-a932-07c2488212e3")) {
                    tanggapan_txt.setVisibility(View.VISIBLE);
                    editbtn.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.GONE);
                    krmbtn.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                judul.setText("Error Loading Data!");

            }
        });
    }

    public void tanggpkrm(View view) {
        isitanggapan_txt = editText.getText().toString();
        try {
            refdb.child("Laporan_Warga").child(userid).child(idtanggap).child("isitanggapan").setValue(isitanggapan_txt);
            refdb.child("Laporan_Warga").child(userid).child(idtanggap).child("tanggapan").setValue("https://firebasestorage.googleapis.com/v0/b/lapor-lur.appspot.com/o/check.png?alt=media&token=2d2a109e-b11d-4e83-a932-07c2488212e3");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(Detail_Tanggapi.this, "Laporan berhasil ditanggapi", Toast.LENGTH_SHORT).show();
        tanggapan_txt.setVisibility(View.VISIBLE);
        editbtn.setVisibility(View.VISIBLE);
        editText.setVisibility(View.GONE);
        krmbtn.setVisibility(View.GONE);

    }

    public void editbtn(View view) {
        tanggapan_txt.setVisibility(View.GONE);
        editbtn.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);
        krmbtn.setVisibility(View.VISIBLE);
    }


    public void deletebtn(View view) {
        startActivity(new Intent(getApplicationContext(), Delete.class));
    }
}
