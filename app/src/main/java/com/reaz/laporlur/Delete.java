package com.reaz.laporlur;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Delete extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference refdb;
    SharedPreferences idtanggapan, iduser;
    String userid, isitanggapan_txt, idtanggap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        database = FirebaseDatabase.getInstance();
        refdb = database.getReference();
        idtanggapan = this.getSharedPreferences("ID_Tanggapan", Context.MODE_PRIVATE);
        idtanggap = idtanggapan.getString("id", "missing");

        iduser = this.getSharedPreferences("NamaUser", Context.MODE_PRIVATE);
        userid = iduser.getString("user", "missing");
    }

    public void delbtn(View view) {

        refdb.child("Laporan_Warga").child(userid).child(idtanggap).removeValue();
        Toast.makeText(Delete.this, "Laporan berhasil dihapus", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), TanggapiActivity.class));

    }
}
