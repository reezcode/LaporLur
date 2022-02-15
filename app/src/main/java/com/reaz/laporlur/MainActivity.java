package com.reaz.laporlur;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    final String TAG = this.getClass().getName();
    FrameLayout frameLayout;
    boolean twice = false;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.framefrag, new Beranda()).commit();
                    return true;
                case R.id.navigation_dashboard:
                    startActivity(new Intent(getApplicationContext(), LaporActivity.class));
                    return true;
                case R.id.navigation_notifications:
                    getSupportFragmentManager().beginTransaction().replace(R.id.framefrag, new Info()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        Log.d(TAG,"click");

        if (twice == true) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        }
        twice = true;
        Log.d(TAG,"twice: "+twice);

        //      super.onBackPressed();
        Toast.makeText(MainActivity.this, "Please press Back again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice = false;
                Log.d(TAG,"twice: "+twice);
            }
        }, 3000);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        frameLayout = findViewById(R.id.framefrag);
        getSupportFragmentManager().beginTransaction().replace(R.id.framefrag, new Beranda()).commit();
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
