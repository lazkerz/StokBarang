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

    TextView no, ket, Tgl, namabrg, kateg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hapus_stock);

        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        btnhapus = findViewById(R.id.btnhapus);
        no = findViewById(R.id.no);
        ket = findViewById(R.id.ket);
        Tgl = findViewById(R.id.tgl);
        namabrg = findViewById(R.id.namabrg);
        kateg = findViewById(R.id.kateg);

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
                Intent pindah = new Intent(hapus_stok.this, dashboard_adm.class);
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
                        String query = "DELETE FROM TB_STOK WHERE ID = ?";
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
            String query = "SELECT No_Inventaris, Keterangan, Tgl_Penyerahan, Nama_Barang, NAMA_KATEGORI " +
                    "FROM TB_STOK WHERE ID = ?";
//            Statement statement = connection.createStatement();
            // Query SELECT
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, idstok);

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

                // Contoh: Tampilkan nilai kolom pada TextView
                no.setText(noinventaris);
                namabrg.setText(namaBarang);
                ket.setText(Ket);
                kateg.setText(kategoribrg);
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