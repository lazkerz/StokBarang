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

public class permintaan extends AppCompatActivity {

    RecyclerView myrecycler;
    ImageButton home, back;

    private MintaAdapter mintaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permintaan);

        home = findViewById(R.id.home);
        back = findViewById(R.id.back);
        myrecycler = findViewById(R.id.mintaRecycler);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(permintaan.this, dashboard_manajemen.class);
                startActivity(pindah);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(permintaan.this, dashboard_manajemen.class);
                startActivity(pindah);
            }
        });

        mintaAdapter = new MintaAdapter(this);
        myrecycler.setAdapter(mintaAdapter);
        myrecycler.setLayoutManager(new LinearLayoutManager(this));

        fetchData();
    }

    private void fetchData() {

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.CONN();

        try {
            String query = "SELECT ID, NAMA, NAMA_BARANG, TGL_PESAN, KETERANGAN, KEPUTUSAN " +
                    "FROM TB_PERMINTAAN";
//            Statement statement = connection.createStatement();
            // Query SELECT
            PreparedStatement statement = connection.prepareStatement(query);

            // Menjalankan query
            ResultSet resultSet = statement.executeQuery();
            // Loop melalui hasil resultSet
            while (resultSet.next()) {
                // Mendapatkan nilai kolom
                String Id = resultSet.getString("ID");
                String Nama = resultSet.getString("NAMA");
                String NamaBrg = resultSet.getString("NAMA_BARANG");
                String Tgl = resultSet.getString("Tgl_Pesan");
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