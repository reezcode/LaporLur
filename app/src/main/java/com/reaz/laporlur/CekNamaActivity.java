package com.reaz.laporlur;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CekNamaActivity extends AppCompatActivity {

    TextInputEditText userid;
    SharedPreferences userlapor;
    String usercek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_nama);
        userid = findViewById(R.id.userid);
        userlapor = this.getSharedPreferences("User_Lapor", Context.MODE_PRIVATE);


    }

    public void enterbtn(View view) {
        usercek = userid.getText().toString();
        if (usercek.length()==0){
            userid.setError("Silakan Masukan Nama atau ID!");
        } else {
            SharedPreferences.Editor useredit = userlapor.edit();
            useredit.putString("user", usercek);
            useredit.apply();
            startActivity(new Intent(getApplicationContext(), Laporanku.class));
        }
    }
}
