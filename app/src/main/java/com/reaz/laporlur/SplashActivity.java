package com.reaz.laporlur;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {
    Animation fb1, fb2, fb3, op, fb4;
    SharedPreferences loginstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loginstatus = this.getSharedPreferences("Login", Context.MODE_PRIVATE);
        boolean status = loginstatus.getBoolean("status", false);

        if (status == true) {
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
        } else {
            Toast.makeText(SplashActivity.this, "Selamat Datang", Toast.LENGTH_LONG).show();
        }

        ImageView logo = findViewById(R.id.logo);
        CardView Mboten = findViewById(R.id.Mboten);
        Button masuk = findViewById(R.id.masuk);
        CoordinatorLayout splash = findViewById(R.id.splashly);
        Button adminbt = findViewById(R.id.admin);

        fb1 = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fb2 = AnimationUtils.loadAnimation(this, R.anim.frombottom2);
        fb3 = AnimationUtils.loadAnimation(this, R.anim.frombottom3);
        fb4 = AnimationUtils.loadAnimation(this, R.anim.frombottom4);
        op = AnimationUtils.loadAnimation(this, R.anim.opacity);

        logo.setAnimation(fb1);
        Mboten.setAnimation(fb2);
        masuk.setAnimation(fb3);
        adminbt.setAnimation(fb4);
        splash.setAnimation(op);
    }

    public void masukbtn(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void adminbtn(View view) {
        startActivity(new Intent(getApplicationContext(), AdminActivity.class));
    }
}
