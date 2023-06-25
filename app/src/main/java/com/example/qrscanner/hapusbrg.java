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
import java.text.ParseException;

public class hapusbrg extends AppCompatActivity {

    ImageButton back, home, btnhapus;

    TextView no, namabrg, nrk, nama, jbtn, Tgl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hapusbrg);

        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        btnhapus = findViewById(R.id.btnhapus);
        no = findViewById(R.id.no);
        namabrg = findViewById(R.id.namabrg);
        nrk = findViewById(R.id.nrk);
        nama = findViewById(R.id.nama);
        jbtn = findViewById(R.id.jbtn);
        Tgl = findViewById(R.id.tgl);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(hapusbrg.this, scan_hapusbrg.class);
                startActivity(pindah);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(hapusbrg.this, dashboard_adm.class);
                startActivity(pindah);
            }
        });

        btnhapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String idBarang = getIntent().getStringExtra("ID");

                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.CONN();

                if (connection != null) {
                try {
                    String query = "DELETE FROM TB_BARANG WHERE ID = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, idBarang);

                    int rowsDeleted = statement.executeUpdate();
                    Toast.makeText(hapusbrg.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                    Intent pindah = new Intent(hapusbrg.this, scan_hapusbrg.class);
                    startActivity(pindah);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                    Toast.makeText(hapusbrg.this, "Terjadi kesalahan saat memperbarui data", Toast.LENGTH_LONG).show();
                }finally {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                } else {
                    Toast.makeText(hapusbrg.this, "Tidak dapat terhubung ke database", Toast.LENGTH_LONG).show();
                }
            }
        });
        fetchData();
    }

        public void fetchData(){
        String idBarang = getIntent().getStringExtra("ID");

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.CONN();

        try {
            String query = "SELECT Nama_Barang, ID, No_Inventaris, Nrk, Nama, NAMA_KATEGORI, Jabatan, Tgl_Terima " +
                    "FROM TB_BARANG WHERE ID = ?";
//            Statement statement = connection.createStatement();
            // Query SELECT
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, idBarang);

            // Menjalankan query
            ResultSet resultSet = statement.executeQuery();
            // Loop melalui hasil resultSet
            while (resultSet.next()) {
                // Mendapatkan nilai kolom
                String noinventaris = resultSet.getString("No_Inventaris");
                String namaBarang = resultSet.getString("Nama_Barang");
                String Nrk = resultSet.getString("Nrk");
                String Nama = resultSet.getString("Nama");
                String Jabatan = resultSet.getString("Jabatan");
                String tgl = resultSet.getString("Tgl_Terima");

                // Contoh: Tampilkan nilai kolom pada TextView
                no.setText(noinventaris);
                namabrg.setText(namaBarang);
                nrk.setText(Nrk);
                nama.setText(Nama);
                jbtn.setText(Jabatan);
                Tgl.setText(tgl);

            }

            resultSet.close();
            statement.close();
            connection.close();
            }catch (SQLException e) {
            e.printStackTrace();
            }
        }
}