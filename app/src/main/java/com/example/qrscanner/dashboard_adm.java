package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class dashboard_adm extends AppCompatActivity {

    ImageButton kategori, barang, lihatstok, tambah, hapus, ubahbrg, minta, logout;

    TextView jmlhstok, jmlhbrg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_adm);

        kategori = findViewById(R.id.kategori);
        barang = findViewById(R.id.barang);
        lihatstok = findViewById(R.id.lihatstok);
        tambah = findViewById(R.id.tambah);
        hapus = findViewById(R.id.hapus);
        ubahbrg = findViewById(R.id.ubahbrg);
        minta = findViewById(R.id.permintaan);
        jmlhbrg = findViewById(R.id.jmlhbrg);
        jmlhstok = findViewById(R.id.jmlhstok);
        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(dashboard_adm.this, Login.class);
                startActivity(pindah);
                finish();
            }
        });

        kategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(dashboard_adm.this, TmbhKategori.class);
                startActivity(pindah);
                finish();
            }
        });

        barang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(dashboard_adm.this, SemuaBrg.class);
                startActivity(pindah);
                finish();
            }
        });

        lihatstok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(dashboard_adm.this, StokBrgUser.class);
                startActivity(pindah);
                finish();
            }
        });

        minta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(dashboard_adm.this, menupermintaan.class);
                startActivity(pindah);
                finish();
            }
        });

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(dashboard_adm.this, tambah_brg.class);
                startActivity(pindah);
                finish();
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(dashboard_adm.this, scan_hapusbrg.class);
                startActivity(pindah);
                finish();
            }
        });

        ubahbrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(dashboard_adm.this, scan_ubahbrg.class);
                startActivity(pindah);
                finish();
            }
        });

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.CONN();

        if (connection != null) {
            try {
                String query = "SELECT COUNT(*) AS count FROM TB_STOCK " +
                        "UNION " +
                        "SELECT COUNT(*) AS count FROM TB_BARANG";
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();

                // Inisialisasi variabel untuk menyimpan hasil count
                int countStok = 0;
                int countBarang = 0;

                // Loop melalui hasil resultSet
                int i = 0;
                while (resultSet.next()) {
                    // Mendapatkan nilai count
                    int count = resultSet.getInt("count");

                    // Memisahkan hasil count berdasarkan indeks
                    if (i == 0) {
                        countStok = count;
                    } else if (i == 1) {
                        countBarang = count;
                    }

                    i++;
                }

                // Menampilkan hasil count terpisah pada TextView
                String totalStok = String.valueOf(countStok);
                String totalBarang = String.valueOf(countBarang);
                jmlhstok.setText(totalStok);
                jmlhbrg.setText(totalBarang);

                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(dashboard_adm.this, "Tidak dapat terhubung ke database", Toast.LENGTH_LONG).show();
        }
    }
}