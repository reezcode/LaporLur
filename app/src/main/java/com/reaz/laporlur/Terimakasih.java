package com.reaz.laporlur;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Terimakasih extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terimakasih);
    }

    public void kmbali(View view) {
    startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
