package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class scan_hapusstok extends AppCompatActivity {

    ImageButton cek, back, home;

    EditText IdStok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_hapusstok);

        cek = findViewById(R.id.btncek);
        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        IdStok = findViewById(R.id.Idstok);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(scan_hapusstok.this, dashboard_adm.class);
                startActivity(pindah);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(scan_hapusstok.this, SemuaStok.class);
                startActivity(pindah);
            }
        });

        cek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idstok = IdStok.getText().toString();
                Intent pindah = new Intent(scan_hapusstok.this, hapus_stok.class);
                pindah.putExtra("ID",idstok);
                startActivity(pindah);
            }
        });
    }
}