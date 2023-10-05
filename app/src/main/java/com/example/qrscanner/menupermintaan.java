package com.example.qrscanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class menupermintaan extends AppCompatActivity {

    ImageButton home, back, minta, setuju;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menupermintaan);

        home = findViewById(R.id.home);
        back = findViewById(R.id.back);
        minta = findViewById(R.id.minta);
        setuju = findViewById(R.id.setuju);

        minta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(menupermintaan.this, permintaan.class);
                startActivity(pindah);
                finish();
            }
        });

        setuju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(menupermintaan.this, permintaanbrg.class);
                startActivity(pindah);
                finish();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(menupermintaan.this, dashboard_adm.class);
                startActivity(pindah);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(menupermintaan.this, dashboard_adm.class);
                startActivity(pindah);
                finish();
            }
        });

    }
}