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

public class semuastok_user extends AppCompatActivity {

    RecyclerView myrecycler;

    ImageButton home, back;

    private StokAdapter stokAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semuastok_user);

        home = findViewById(R.id.home);
        back = findViewById(R.id.back);
        myrecycler = findViewById(R.id.stokRecycler);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(semuastok_user.this, dashboard_user.class);
                startActivity(pindah);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(semuastok_user.this, dashboard_user.class);
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
            String selectedCategory = getIntent().getStringExtra("NAMA_KATEGORI");
            String query = "SELECT No_Inventaris, Keterangan, Tgl_Penyerahan, Nama_Barang, NAMA_KATEGORI " +
                    "FROM TB_STOK WHERE NAMA_KATEGORI = ?" +
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
                String noinventaris = resultSet.getString("No_Inventaris");
                String Ket = resultSet.getString("Keterangan");
                String tgl = resultSet.getString("Tgl_Penyerahan");
                String namaBarang = resultSet.getString("Nama_Barang");
                String kategoribrg = resultSet.getString("NAMA_KATEGORI");

                Stok stok = new Stok(0, noinventaris, Ket, tgl, namaBarang, kategoribrg);
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