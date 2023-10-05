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

public class hapus_stok extends AppCompatActivity {

    ImageButton back, home, btnhapus;

    TextView Namabrg, Jmlh, Vendor, Tgl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hapus_stock);

        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        btnhapus = findViewById(R.id.btnhapus);
        Namabrg = findViewById(R.id.namabrg);
        Jmlh = findViewById(R.id.Jmlh);
        Tgl = findViewById(R.id.tgl);
        Vendor = findViewById(R.id.Vendor);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(hapus_stok.this, scan_hapusstok.class);
                startActivity(pindah);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(hapus_stok.this, dashboard_pengadaan.class);
                startActivity(pindah);
            }
        });

        btnhapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String idstok= getIntent().getStringExtra("ID");

                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.CONN();

                if (connection != null) {
                    try {
                        String query = "DELETE FROM TB_STOCK WHERE ID = ?";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1, idstok);

                        int rowsDeleted = statement.executeUpdate();
                        Toast.makeText(hapus_stok.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                        Intent pindah = new Intent(hapus_stok.this, scan_hapusstok.class);
                        startActivity(pindah);
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                        Toast.makeText(hapus_stok.this, "Terjadi kesalahan saat memperbarui data", Toast.LENGTH_LONG).show();
                    }finally {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(hapus_stok.this, "Tidak dapat terhubung ke database", Toast.LENGTH_LONG).show();
                }
            }
        });
        fetchData();
    }

    public void fetchData(){
        String idstok = getIntent().getStringExtra("ID");

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.CONN();

        try {
            String query = "SELECT NAMA_BARANG, JUMLAH, VENDOR, Tgl_Penyerahan " +
                    "FROM TB_STOCK WHERE ID = ?";
//            Statement statement = connection.createStatement();
            // Query SELECT
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, idstok);

            // Menjalankan query
            ResultSet resultSet = statement.executeQuery();
            // Loop melalui hasil resultSet
            while (resultSet.next()) {
                // Mendapatkan nilai kolom
                String namabrg = resultSet.getString("NAMA_BARANG");
                String jlh = resultSet.getString("JUMLAH");
                String vendor = resultSet.getString("VENDOR");
                String tgl = resultSet.getString("Tgl_Penyerahan");

                // Contoh: Tampilkan nilai kolom pada TextView
                Namabrg.setText(namabrg);
                Jmlh.setText(jlh);
                Vendor.setText(vendor);
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