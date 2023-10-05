package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class dashboard_pengadaan extends AppCompatActivity {

    ImageButton lihatstok, logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_pengadaan);

        lihatstok = findViewById(R.id.lihatstok);
        logout= findViewById(R.id.logout);

        lihatstok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(dashboard_pengadaan.this, StokBrg.class);
                startActivity(pindah);
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(dashboard_pengadaan.this, Login.class);
                startActivity(pindah);
                finish();
            }
        });
    }
}