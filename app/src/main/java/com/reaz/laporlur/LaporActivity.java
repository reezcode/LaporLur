package com.reaz.laporlur;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LaporActivity extends AppCompatActivity {
    TextInputEditText email, judul, isi, rtrw, dukuh, nama;
    String emailtxt, judultxt, isitxt, rtrwtxt, dukuhtxt, tanggapan, isitanggapan, waktu;
    DatabaseReference laporandb, namadb;
    FirebaseDatabase database;
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lapor);
        Toolbar mToolbar2 = findViewById(R.id.toolbar);
        database = FirebaseDatabase.getInstance();
        laporandb = database.getReference("Laporan_Warga");
        namadb = database.getReference("Data_Warga_V2");
        mToolbar2.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

         email = findViewById(R.id.email);
         nama = findViewById(R.id.nama);
         judul = findViewById(R.id.judul);
         isi = findViewById(R.id.isi);
         rtrw = findViewById(R.id.rtrw);
         dukuh = findViewById(R.id.dukuh);
         isitanggapan = "Tanggapan belum ada";
         tanggapan = "https://firebasestorage.googleapis.com/v0/b/lapor-lur.appspot.com/o/uncheck.png?alt=media&token=c26f07d0-db28-4767-b4cd-ae44e59b3292";
         waktu = getWaktu();

    }

    boolean isEmail(TextInputEditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }


    public void kirimbtn(View view) {
            proseslaporan();

    }

    private void proseslaporan() {
        if(!isEmail(email)) {
            email.setError("Masukan Email dengan benar");
        } else {
            final Nama nama2 = new Nama(nama.getText().toString());
            final Lapor lapor = new Lapor(nama.getText().toString(), email.getText().toString(), judul.getText().toString(), isi.getText().toString(), rtrw.getText().toString(), dukuh.getText().toString(), tanggapan, isitanggapan, waktu);
            namadb.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(nama2.getNama()).exists())
                        System.out.println("OK!");
                        else {
                            namadb.child(nama2.getNama()).setValue(nama2);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            laporandb.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(lapor.getNama()).child(lapor.getJudul()).exists())
                        Toast.makeText(LaporActivity.this, "Judul sudah pernah ada!", Toast.LENGTH_SHORT).show();
                    else {
                        laporandb.child(lapor.getNama()).child(lapor.getJudul()).setValue(lapor);
                        Toast.makeText(LaporActivity.this, "Proses pengiriman sukses, silakan tunggu konfirmasi dari kami", Toast.LENGTH_SHORT).show();
                        assert getFragmentManager() != null;
                        startActivity(new Intent(getApplicationContext(), Terimakasih.class));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Window window = getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                        }
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        email.setText("");
        judul.setText("");
        isi.setText("");
        rtrw.setText("");
        dukuh.setText("");

    }

    private String getWaktu() {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH.mm.ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
