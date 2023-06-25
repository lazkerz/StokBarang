package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Select extends AppCompatActivity {

    RecyclerView myrecycler;

    ImageButton home, back;

    private BrgAdapter brgAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        home = findViewById(R.id.home);
        back = findViewById(R.id.back);
        myrecycler = findViewById(R.id.kategoriRecycler);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(Select.this, dashboard_adm.class);
                startActivity(pindah);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(Select.this, TmbhKategori.class);
                startActivity(pindah);
            }
        });


        brgAdapter = new BrgAdapter(this);
        myrecycler.setAdapter(brgAdapter);
        myrecycler.setLayoutManager(new LinearLayoutManager(this));

        fetchData();
    }

    private void fetchData() {

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.CONN();

        try {

            String selectedCategory = getIntent().getStringExtra("NAMA_KATEGORI");
            String query = "SELECT Nama_Barang, No_Inventaris, ID, Nrk, Nama, NAMA_KATEGORI, Jabatan, Tgl_Terima " +
                    "FROM TB_BARANG WHERE NAMA_KATEGORI = ?" +
                    "ORDER BY Tgl_Terima DESC";
//            Statement statement = connection.createStatement();
            // Query SELECT
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, selectedCategory);

            // Menjalankan query
            ResultSet resultSet = statement.executeQuery();
            // Loop melalui hasil resultSet
            while (resultSet.next()) {
                // Mendapatkan nilai kolom
                String kategoribrg = resultSet.getString("NAMA_KATEGORI");
                String noinventaris = resultSet.getString("No_Inventaris");
                String namaBarang = resultSet.getString("Nama_Barang");
                String Nrk = resultSet.getString("Nrk");
                String nama = resultSet.getString("Nama");
                String jabatan = resultSet.getString("Jabatan");
                String tgl = resultSet.getString("Tgl_Terima");
                String id = resultSet.getString("ID");

                Barang barang = new Barang(0, kategoribrg, noinventaris, namaBarang, Nrk, nama, jabatan, tgl, id);
                brgAdapter.addBarang(barang);
            }
            brgAdapter.notifyDataSetChanged();

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}