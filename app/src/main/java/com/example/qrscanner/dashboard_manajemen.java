package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class dashboard_manajemen extends AppCompatActivity {

    ImageButton minta, setuju, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_manajemen);

        minta = findViewById(R.id.minta);
        setuju = findViewById(R.id.setuju);
        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(dashboard_manajemen.this, Login.class);
                startActivity(pindah);
                finish();
            }
        });

        minta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(dashboard_manajemen.this, permintaan.class);
                startActivity(pindah);
                finish();
            }
        });

        setuju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(dashboard_manajemen.this, persetujuan.class);
                startActivity(pindah);
                finish();
            }
        });
    }
}