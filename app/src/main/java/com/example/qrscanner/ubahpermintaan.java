package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ubahpermintaan extends AppCompatActivity {

    ImageButton back, home, btnubah;

    EditText Txtnama, Namabrg, Txttanggal;

    Spinner spinnerket, spinnerkeputusan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubahpermintaan);

        home = findViewById(R.id.home);
        back = findViewById(R.id.back);
        Txtnama = findViewById(R.id.Txtnama);
        Namabrg = findViewById(R.id.Namabrg);
        Txttanggal = findViewById(R.id.Txttanggal);
        spinnerket = findViewById(R.id.spinnerket);
        spinnerkeputusan = findViewById(R.id.spinnerkeputusan);
        btnubah = findViewById(R.id.btnubah);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(ubahpermintaan.this, dashboard_manajemen.class);
                startActivity(pindah);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(ubahpermintaan.this, dashboard_manajemen.class);
                startActivity(pindah);
            }
        });


        Txttanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mendapatkan tanggal saat ini
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Membuat DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(ubahpermintaan.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Mengatur tanggal yang dipilih pada EditText
                                String selectedDate = dayOfMonth + "/" + (monthOfYear +1) + "/" + year;
                                Txttanggal.setText(selectedDate);
                            }
                        }, year, month, day);

                // Menampilkan DatePickerDialog
                datePickerDialog.show();
            }
        });

        btnubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = Txtnama.getText().toString();
                String brg = Namabrg.getText().toString();
                String ket = spinnerket.getSelectedItem().toString();
                String keputusan = spinnerkeputusan.getSelectedItem().toString();
                String tgl = Txttanggal.getText().toString();

                // Ambil data dari database
                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.CONN();
                // Anda dapat menggunakan koneksi ke database dan eksekusi query di sini

                try {
                    String selectedId = getIntent().getStringExtra("ID");
                    String query = "UPDATE TB_PERMINTAAN SET NAMA = ?, NAMA_BARANG = ?, KETERANGAN = ?, KEPUTUSAN = ?, TGL_PESAN = ? WHERE ID = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, nama);
                    statement.setString(2, brg);
                    statement.setString(3, ket);
                    statement.setString(4, keputusan);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = dateFormat.parse(tgl);

                    statement.setDate(5, new java.sql.Date(date.getTime()));
                    statement.setString(6, selectedId); // Tambahkan ini untuk mengisi parameter ke-6


                    int rowsUpdated = statement.executeUpdate();

                    if (rowsUpdated > 0) {
                        if (keputusan.equals("Diterima") && ket.equals("Tambah")) {
                            String updateStokQuery = "UPDATE TB_STOCK SET JUMLAH = JUMLAH - 1 WHERE NAMA_BARANG = ?";
                            PreparedStatement updateStokStatement = connection.prepareStatement(updateStokQuery);
                            updateStokStatement.setString(1, brg);
                            int stokRowsUpdated = updateStokStatement.executeUpdate();
                            updateStokStatement.close();

                            if (stokRowsUpdated > 0) {
                                Toast.makeText(ubahpermintaan.this, "Data Diperbarui, jumlah stok berkurang", Toast.LENGTH_LONG).show();
                                Intent pindah = new Intent(ubahpermintaan.this, permintaan.class);
                                startActivity(pindah);
                            } else {
                                Toast.makeText(ubahpermintaan.this, "Gagal mengurangi jumlah stok", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(ubahpermintaan.this, "Data Diperbarui", Toast.LENGTH_LONG).show();
                            Intent pindah = new Intent(ubahpermintaan.this, permintaan.class);
                            startActivity(pindah);
                        }
                    } else {
                        Toast.makeText(ubahpermintaan.this, "Data gagal Diperbarui", Toast.LENGTH_LONG).show();
                    }

                    statement.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        fetchData();
        new ubahpermintaan.LoadSpinnerData().execute();
    }

    private void fetchData() {

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.CONN();

        try {
            String selectedId = getIntent().getStringExtra("ID");
            String query = "SELECT ID, NAMA, NAMA_BARANG, TGL_PESAN, KETERANGAN, KEPUTUSAN " +
                    "FROM TB_PERMINTAAN WHERE ID = ?";
//            Statement statement = connection.createStatement();
            // Query SELECT
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, selectedId);

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

                Txtnama.setText(Nama);
                Namabrg.setText(NamaBrg);
                Txttanggal.setText(Tgl);
                // Set spinner selection
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private class LoadSpinnerData extends AsyncTask<Void, Void, List<String>[]> {
        @Override
        protected List<String>[] doInBackground(Void... params) {
            List<String>[] spinnerData = new ArrayList[2];

            // Mengisi data spinner pertama
            List<String> spinnerKetData = new ArrayList<>();
            spinnerKetData.add("Tambah");
            spinnerKetData.add("Musnahkan");
            spinnerData[0] = spinnerKetData;

            // Mengisi data spinner kedua
            List<String> spinnerkeputusanData = new ArrayList<>();
            spinnerkeputusanData.add("Setujui");
            spinnerkeputusanData.add("Tolak");
            spinnerData[1] = spinnerkeputusanData;

            return spinnerData;
        }

        @Override
        protected void onPostExecute(List<String>[] result) {
            // Spinner pertama
            ArrayAdapter<String> spinner1Adapter = new ArrayAdapter<String>(ubahpermintaan.this,
                    R.layout.spinner_item, result[0]);
            spinner1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerket.setAdapter(spinner1Adapter);

            // Spinner kedua
            ArrayAdapter<String> spinner2Adapter = new ArrayAdapter<String>(ubahpermintaan.this,
                    R.layout.spinner_item, result[1]);
            spinner2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerkeputusan.setAdapter(spinner2Adapter);
        }
    }
}