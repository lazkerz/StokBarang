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

public class dashboard_user extends AppCompatActivity {

    ImageButton cek, kategori, tambah, lihat, logout;

    TextView jmlhstok, jmlhbrg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user);

        cek = findViewById(R.id.cek);
        kategori = findViewById(R.id.kategori);
        tambah = findViewById(R.id.tambah);
        lihat = findViewById(R.id.lihat);
        jmlhbrg = findViewById(R.id.jmlhbrg);
        jmlhstok = findViewById(R.id.jmlhstok);
        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(dashboard_user.this, Login.class);
                startActivity(pindah);
            }
        });

        cek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(dashboard_user.this, CekBrg.class);
                startActivity(pindah);
            }
        });

        kategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(dashboard_user.this, kategori_user.class);
                startActivity(pindah);
            }
        });

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(dashboard_user.this, semuaBrg_user.class);
                startActivity(pindah);
            }
        });

        lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(dashboard_user.this, StokBrgUser.class);
                startActivity(pindah);
            }
        });

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.CONN();

        if (connection != null) {
            try {
                String query = "SELECT COUNT(*) AS count FROM TB_STOK " +
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
            Toast.makeText(dashboard_user.this, "Tidak dapat terhubung ke database", Toast.LENGTH_LONG).show();
        }
    }
}