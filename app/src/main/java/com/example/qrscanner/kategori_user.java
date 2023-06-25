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

public class kategori_user extends AppCompatActivity {

    ImageButton home, back;

    RecyclerView myrecycler;

    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_user);

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
                Toast.makeText(kategori_user.this, "Kategori terpilih: " + selectedCategory, Toast.LENGTH_SHORT).show();

                // Berpindah ke aktivitas lain dengan Intent
                Intent pindah = new Intent(kategori_user.this, kategoriperbrng_user.class);
                pindah.putExtra("NAMA_KATEGORI", selectedCategory);
                startActivity(pindah);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(kategori_user.this, dashboard_user.class);
                startActivity(pindah);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(kategori_user.this, dashboard_user.class);
                startActivity(pindah);
            }
        });
        fetchData();
    }

    private void fetchData() {

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.CONN();

        try {
            String query = "SELECT NAMA_KATEGORI, COUNT(*) AS count FROM TB_BARANG " +
                    "WHERE NAMA_KATEGORI IN (SELECT NAMA_KATEGORI FROM TB_STOK) " +
                    "GROUP BY NAMA_KATEGORI";//            Statement statement = connection.createStatement();
            // Query SELECT
            PreparedStatement statement = connection.prepareStatement(query);

            // Menjalankan query
            ResultSet resultSet = statement.executeQuery();
            // Loop melalui hasil resultSet
            while (resultSet.next()) {

                int Jumlah = resultSet.getInt("count");
                // Mendapatkan nilai kolom
                String kategoribrg = resultSet.getString("NAMA_KATEGORI");
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