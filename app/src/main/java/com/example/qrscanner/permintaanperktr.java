package com.example.qrscanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class permintaanperktr extends AppCompatActivity {

    RecyclerView myrecycler;
    ImageButton home, back;

    private MintaAdapter mintaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permintaanperktr);

        home = findViewById(R.id.home);
        back = findViewById(R.id.back);
        myrecycler = findViewById(R.id.mintaRecycler);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(permintaanperktr.this, dashboard_manajemen.class);
                startActivity(pindah);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(permintaanperktr.this, dashboard_manajemen.class);
                startActivity(pindah);
            }
        });

        mintaAdapter = new MintaAdapter(this);
        myrecycler.setAdapter(mintaAdapter);
        myrecycler.setLayoutManager(new LinearLayoutManager(this));

        mintaAdapter.setOnItemClickListener(new MintaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Aksi yang ingin Anda lakukan saat item di RecyclerView diklik
                String selectedId = mintaAdapter.getItem(position).getIdpermintaan();
                Toast.makeText(permintaanperktr.this, "Yang terpilih: " + selectedId, Toast.LENGTH_SHORT).show();

                // Berpindah ke aktivitas lain dengan Intent
                Intent pindah = new Intent(permintaanperktr.this, ubahpermintaan.class);
                pindah.putExtra("ID", selectedId);
                startActivity(pindah);
            }
        });

        fetchData();
    }

    private void fetchData() {

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.CONN();

        try {
            String selectedCategory = getIntent().getStringExtra("KETERANGAN");
            String query = "SELECT ID, NAMA, NAMA_BARANG, TGL_PESAN, KETERANGAN, KEPUTUSAN " +
                    "FROM TB_PERMINTAAN WHERE KETERANGAN = ?";
//            Statement statement = connection.createStatement();
            // Query SELECT
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, selectedCategory);

            // Menjalankan query
            ResultSet resultSet = statement.executeQuery();
            // Loop melalui hasil resultSet
            while (resultSet.next()) {
                // Mendapatkan nilai kolom
                String Id = resultSet.getString("ID");
                String Nama = resultSet.getString("NAMA");
                String NamaBrg = resultSet.getString("NAMA_BARANG");
                String Tgl = resultSet.getString("TGL_PESAN");
                String Keterangan = resultSet.getString("KETERANGAN");
                String Keputusan = resultSet.getString("KEPUTUSAN");

                Minta minta = new Minta(0, Id, Nama, NamaBrg, Tgl, Keterangan, Keputusan);
                mintaAdapter.addMinta(minta);
            }
            mintaAdapter.notifyDataSetChanged();

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}