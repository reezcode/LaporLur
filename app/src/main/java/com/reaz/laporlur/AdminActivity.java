package com.reaz.laporlur;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminActivity extends AppCompatActivity {

    TextInputEditText nama, password;
    Button masukbtn;
    DatabaseReference users;
    FirebaseDatabase database;
    SharedPreferences loginstatus, datanama;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        loginstatus = this.getSharedPreferences("Login", Context.MODE_PRIVATE);
        datanama = this.getSharedPreferences("Data", Context.MODE_PRIVATE);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Admin");
        nama = findViewById(R.id.nama);
        password = findViewById(R.id.password);
        masukbtn = findViewById(R.id.masuk);


    }

    boolean isUserName(TextInputEditText text) {
        CharSequence UserName = text.getText().toString();
        return (!TextUtils.isEmpty(UserName) && Patterns.EMAIL_ADDRESS.matcher(UserName).matches());
    }

    private void Login(final String user, final String pwd) {
        if (isUserName(nama)) {

            nama.setError("Mohon jangan masukan email, masukan username yang benar!");

            return;

        }
        if (password.getText().toString().length() < 0) {

            password.setError("Masukan password");

        } else {

            users.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(user).exists())
                    {
                        if(!user.isEmpty())
                        {
                            User login = dataSnapshot.child(user).getValue(User.class);
                            assert login != null;
                            if(login.getPassword().equals(pwd)) {
                                String name = nama.getText().toString();
                                boolean status = true;
                                SharedPreferences.Editor loginedit = loginstatus.edit();
                                SharedPreferences.Editor dataedit = datanama.edit();
                                dataedit.putString("nama", name);
                                dataedit.apply();
                                loginedit.putBoolean("status", status);
                                loginedit.apply();
                                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));

                            }
                            else {
                                Toast.makeText(AdminActivity.this, "Login gagal. Mohon coba lagi...", Toast.LENGTH_SHORT ).show();
                            }

                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }}

    public void masukadmin(View view) {
        Login(nama.getText().toString(), password.getText().toString());
    }
}
