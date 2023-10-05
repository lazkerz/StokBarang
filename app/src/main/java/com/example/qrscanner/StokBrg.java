package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StokBrg extends AppCompatActivity {

    ImageButton home, back;

    RecyclerView myrecycler;

    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok_brg);

        home = findViewById(R.id.home);
        back = findViewById(R.id.back);
        myrecycler = findViewById(R.id.kategoriRecycler);

        userAdapter = new UserAdapter(this);
        myrecycler.setAdapter(userAdapter);
        myrecycler.setLayoutManager(new LinearLayoutManager(this));


        userAdapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Aksi yang ingin Anda lakukan saat item di RecyclerView diklik
                String selectedCategory = userAdapter.getItem(position).getKategori();
                Toast.makeText(StokBrg.this, "Barang terpilih: " + selectedCategory, Toast.LENGTH_SHORT).show();

                // Berpindah ke aktivitas lain dengan Intent
                Intent pindah = new Intent(StokBrg.this, SemuaStok.class);
                pindah.putExtra("NAMA_BARANG", selectedCategory);
                startActivity(pindah);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(StokBrg.this, dashboard_pengadaan.class);
                startActivity(pindah);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(StokBrg.this, dashboard_pengadaan.class);
                startActivity(pindah);
            }
        });
        fetchData();
    }

    private void fetchData() {

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.CONN();

        try {
            String query ="SELECT NAMA_BARANG, JUMLAH FROM TB_STOCK";
//            String query = "SELECT NAMA_BARANG, COUNT(*) AS count FROM TB_STOCK " +
//                    "WHERE NAMA_BARANG IN (SELECT NAMA_BARANG FROM TB_STOCK) " +
//                    "GROUP BY NAMA_BARANG";//            Statement statement = connection.createStatement();
            // Query SELECT
            PreparedStatement statement = connection.prepareStatement(query);

            // Menjalankan query
            ResultSet resultSet = statement.executeQuery();

            // Loop melalui hasil resultSet
            while (resultSet.next()) {

//                int Jumlah = resultSet.getInt("count");
                String Jumlah = resultSet.getString("JUMLAH");
                // Mendapatkan nilai kolom
                String kategoribrg = resultSet.getString("NAMA_BARANG");
                String totalText = " : " + Jumlah;

                Kategori kategori = new Kategori(0, kategoribrg, totalText);
                userAdapter.addKategori(kategori);

            }

            userAdapter.notifyDataSetChanged();

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}