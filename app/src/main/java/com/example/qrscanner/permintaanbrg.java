package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class permintaanbrg extends AppCompatActivity {

    ImageButton kirim, home, back;

    TextView Nama, namabrg, tgl, Ket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permintaanbrg);

        kirim = findViewById(R.id.kirim);
        home = findViewById(R.id.home);
        back = findViewById(R.id.back);
        Nama = findViewById(R.id.Nama);
        namabrg = findViewById(R.id.namabrg);
        tgl = findViewById(R.id.Tgl);
        Ket = findViewById(R.id.keterangan);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(permintaanbrg.this, dashboard_adm.class);
                startActivity(pindah);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(permintaanbrg.this, menupermintaan.class);
                startActivity(pindah);
            }
        });

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataSingleton dataSingleton = DataSingleton.getInstance();

                String nama = dataSingleton.getNama();
                String barang = dataSingleton.getBarang();
                String tanggal = dataSingleton.getTanggal();
                String keterangan = dataSingleton.getKeterangan();

                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.CONN();

                try {
                    String query = "INSERT INTO TB_PERMINTAAN (NAMA, Nama_Barang, TGL_PESAN, KETERANGAN) VALUES (?, ?, ?, ?)";

                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, nama);
                    statement.setString(2, barang);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = dateFormat.parse(tanggal);
                    statement.setDate(3, new java.sql.Date(date.getTime()));

                    statement.setString(4, keterangan);

                    int rowsInserted = statement.executeUpdate();
                    // Mengisi spinner dengan data dari database
                    if (rowsInserted > 0) {
                        Toast.makeText(permintaanbrg.this, "Data Ditambahkan", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(permintaanbrg.this, "Data gagal Ditambahkan", Toast.LENGTH_LONG).show();
                    }
                    statement.close();
                } catch (SQLException | ParseException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        tampilkanDataDariSingleton();
    }

        private void tampilkanDataDariSingleton() {
        DataSingleton dataSingleton = DataSingleton.getInstance();

        String nama = dataSingleton.getNama();
        String barang = dataSingleton.getBarang();
        String tanggal = dataSingleton.getTanggal();
        String keterangan = dataSingleton.getKeterangan();

        if (nama != null) {
            Nama.setText(nama);
        }

        if (barang != null) {
            namabrg.setText(barang);
        }

        if (tanggal != null) {
            tgl.setText(tanggal);
        }

        if (keterangan != null) {
            Ket.setText(keterangan);
        }
    }
}