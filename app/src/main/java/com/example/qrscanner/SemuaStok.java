package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SemuaStok extends AppCompatActivity {

    RecyclerView myrecycler;

    ImageButton home, back, tambah, hapus;

    private StokAdapter stokAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semua_stok);

        home = findViewById(R.id.home);
        back = findViewById(R.id.back);
        tambah = findViewById(R.id.tambah);
        hapus = findViewById(R.id.hapus);
        myrecycler = findViewById(R.id.stokRecycler);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(SemuaStok.this, dashboard_pengadaan.class);
                startActivity(pindah);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(SemuaStok.this, dashboard_pengadaan.class);
                startActivity(pindah);
            }
        });

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(SemuaStok.this, tambah_stok.class);
                startActivity(pindah);
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(SemuaStok.this, scan_hapusstok.class);
                startActivity(pindah);
            }
        });

        stokAdapter = new StokAdapter(this);
        myrecycler.setAdapter(stokAdapter);
        myrecycler.setLayoutManager(new LinearLayoutManager(this));

        fetchData();
    }

    private void fetchData() {

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.CONN();

        try {
            String selectedCategory = getIntent().getStringExtra("NAMA_BARANG");
            String query = "SELECT NAMA_BARANG, JUMLAH, VENDOR, TGL_PENYERAHAN " +
                    "FROM TB_STOCK WHERE NAMA_BARANG = ?" +
                    "ORDER BY Tgl_Penyerahan DESC";
//           Statement statement = connection.createStatement();
            // Query SELECT
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, selectedCategory);

            // Menjalankan query
            ResultSet resultSet = statement.executeQuery();
            // Loop melalui hasil resultSet
            while (resultSet.next()) {
                // Mendapatkan nilai kolom
                String namabrg = resultSet.getString("NAMA_BARANG");
                String Jlh = resultSet.getString("JUMLAH");
                String Vendor = resultSet.getString("VENDOR");
                String Tgl = resultSet.getString("TGL_PENYERAHAN");

                Stok stok = new Stok(0, namabrg, Jlh, Vendor, Tgl);
                stokAdapter.addStok(stok);
            }
            stokAdapter.notifyDataSetChanged();

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}