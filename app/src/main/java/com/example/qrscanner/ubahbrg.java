package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ubahbrg extends AppCompatActivity {

    ImageButton back, home, btnubah;
    EditText Namabrg, Textid, Textno, Textnrk, Textnama, Textjabatan, Texttanggal;

    private BrgAdapter brgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubahbrg);

        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        Namabrg = findViewById(R.id.Namabrng);
        Textid = findViewById(R.id.Txtid);
        Textno = findViewById(R.id.Txtno);
        Textnrk = findViewById(R.id.Txtnrk);
        Textnama = findViewById(R.id.Txtnama);
        Textjabatan = findViewById(R.id.Txtjabatan);
        Texttanggal = findViewById(R.id.Txttanggal);
        btnubah = findViewById(R.id.btnubah);

        brgAdapter = new BrgAdapter(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(ubahbrg.this, dashboard_adm.class);
                startActivity(pindah);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(ubahbrg.this, dashboard_adm.class);
                startActivity(pindah);
            }
        });

        Texttanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mendapatkan tanggal saat ini
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Membuat DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(ubahbrg.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Mengatur tanggal yang dipilih pada EditText
                                String selectedDate = dayOfMonth + "/" + (monthOfYear +1) + "/" + year;
                                Texttanggal.setText(selectedDate);
                            }
                        }, year, month, day);

                // Menampilkan DatePickerDialog
                datePickerDialog.show();
            }
        });

        btnubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String namabrg = Namabrg.getText().toString();
                String idBarang = getIntent().getStringExtra("ID");
                String no = Textno.getText().toString();
                String nrk = Textnrk.getText().toString();
                String nama = Textnama.getText().toString();
                String jbtn = Textjabatan.getText().toString();
                String tgl = Texttanggal.getText().toString();
                String kategori = namabrg;

                // Ambil data dari database
                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.CONN();

                if (connection != null) {
                    try {
                        String query = "UPDATE TB_BARANG SET Nama_Barang = ?, No_Inventaris = ?, Nrk = ?, Nama = ?, NAMA_KATEGORI = ?, Jabatan = ?, Tgl_Terima = ? WHERE ID = ?";

                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1, namabrg);
                        statement.setString(2, no);
                        statement.setString(3, nrk);
                        statement.setString(4, nama);
                        statement.setString(5, kategori);
                        statement.setString(6, jbtn);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = dateFormat.parse(tgl);

                        statement.setDate(7, new java.sql.Date(date.getTime()));
                        statement.setString(8, idBarang);

                        int rowsUpdated = statement.executeUpdate();
                        if (rowsUpdated > 0) {
                            Toast.makeText(ubahbrg.this, "Data Diperbarui", Toast.LENGTH_LONG).show();
                            Intent pindah = new Intent(ubahbrg.this, SemuaBrg.class);
                            startActivity(pindah);
                        } else {
                            Toast.makeText(ubahbrg.this, "Data gagal Diperbarui", Toast.LENGTH_LONG).show();
                        }
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        Toast.makeText(ubahbrg.this, "Terjadi kesalahan saat memperbarui data", Toast.LENGTH_LONG).show();
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(ubahbrg.this, "Terjadi kesalahan dalam memformat tanggal", Toast.LENGTH_LONG).show();
                    } finally {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(ubahbrg.this, "Tidak dapat terhubung ke database", Toast.LENGTH_LONG).show();
                }
            }
        });
        fetchData();
    }

    private void fetchData() {

        String idBarang = getIntent().getStringExtra("ID");

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.CONN();

        try {
            String query = "SELECT Nama_Barang, ID, No_Inventaris, Nrk, Nama, NAMA_KATEGORI, Jabatan, Tgl_Terima " +
                    "FROM TB_BARANG WHERE ID = ? " ;
//            Statement statement = connection.createStatement();
            // Query SELECT
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, idBarang);

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

                Namabrg.setText(namaBarang);
                Textid.setText(id);
                Textno.setText(noinventaris);
                Textnrk.setText(Nrk);
                Textnama.setText(nama);
                Textjabatan.setText(jabatan);
                Texttanggal.setText(tgl);
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