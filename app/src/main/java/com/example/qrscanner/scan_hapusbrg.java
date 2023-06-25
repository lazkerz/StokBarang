package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class scan_hapusbrg extends AppCompatActivity {

    ImageButton back, home, cek, scan;

    TextView hasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_hapusbrg);

        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        cek = findViewById(R.id.cek);
        scan = findViewById(R.id.qr);
        hasil = findViewById(R.id.txthasil);


//        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(threadPolicy);


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(scan_hapusbrg.this);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.setPrompt("Scan a QR Code");
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.initiateScan();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(scan_hapusbrg.this, dashboard_adm.class);
                startActivity(pindah);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(scan_hapusbrg.this, dashboard_adm.class);
                startActivity(pindah);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null){
            String contents = intentResult.getContents();
            String[] idbarang = contents.split("=");
            if(idbarang.length > 1){
                hasil.setText(("ID : "+ idbarang[1]));
                String idBarang = idbarang[1];

                cek.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent pindah = new Intent(scan_hapusbrg.this, hapusbrg.class);
                        pindah.putExtra("ID",idBarang);
                        startActivity(pindah);
                    }
                });
            }else{
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}