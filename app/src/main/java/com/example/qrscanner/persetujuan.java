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

public class persetujuan extends AppCompatActivity {

    ImageButton home, back;

    RecyclerView myrecycler;

    private SetujuAdapter setujuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persetujuan);

        home = findViewById(R.id.home);
        back = findViewById(R.id.back);
        myrecycler = findViewById(R.id.setujuRecycler);

        setujuAdapter = new SetujuAdapter(this);
        myrecycler.setAdapter(setujuAdapter);
        myrecycler.setLayoutManager(new LinearLayoutManager(this));

        setujuAdapter.setOnItemClickListener(new SetujuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Aksi yang ingin Anda lakukan saat item di RecyclerView diklik
                String selectedCategory = setujuAdapter.getItem(position).getPersetujuan();
                Toast.makeText(persetujuan.this, "Permintaan terpilih: " + selectedCategory, Toast.LENGTH_SHORT).show();

                // Berpindah ke aktivitas lain dengan Intent
                Intent pindah = new Intent(persetujuan.this, permintaanperktr.class);
                pindah.putExtra("KETERANGAN", selectedCategory);
                startActivity(pindah);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(persetujuan.this, dashboard_manajemen.class);
                startActivity(pindah);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(persetujuan.this, dashboard_manajemen.class);
                startActivity(pindah);
            }
        });
        fetchData();
    }

    private void fetchData() {

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.CONN();

        try {
            String query = "SELECT KETERANGAN, COUNT(*) AS count FROM TB_PERMINTAAN " +
                    "WHERE KETERANGAN IN (SELECT KETERANGAN FROM TB_PERMINTAAN) " +
                    "GROUP BY KETERANGAN";//            Statement statement = connection.createStatement();
            // Query SELECT
            PreparedStatement statement = connection.prepareStatement(query);

            // Menjalankan query
            ResultSet resultSet = statement.executeQuery();
            // Loop melalui hasil resultSet
            while (resultSet.next()) {

                int Jumlah = resultSet.getInt("count");
                // Mendapatkan nilai kolom
                String keterangan = resultSet.getString("KETERANGAN");
                String totalText = " : " + Jumlah;

                Setuju setuju = new Setuju(0, keterangan, totalText);
                setujuAdapter.addSetuju(setuju);

            }

            setujuAdapter.notifyDataSetChanged();

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}