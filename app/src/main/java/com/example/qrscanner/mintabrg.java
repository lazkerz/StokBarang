package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class mintabrg extends AppCompatActivity {

    EditText Nama, Tgl;

    ImageButton btnminta, home, back;

    Spinner Namabrg, Ket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mintabrg);

        home = findViewById(R.id.home);
        back = findViewById(R.id.back);
        btnminta = findViewById(R.id.btnminta);
        Nama = findViewById(R.id.Nama);
        Namabrg = findViewById(R.id.Namabrg);
        Tgl = findViewById(R.id.Tgl);
        Ket = findViewById(R.id.Ket);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(mintabrg.this, dashboard_user.class);
                startActivity(pindah);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(mintabrg.this, dashboard_user.class);
                startActivity(pindah);
            }
        });

        Tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mendapatkan tanggal saat ini
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Membuat DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(mintabrg.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Mengatur tanggal yang dipilih pada EditText
                                String selectedDate = dayOfMonth + "/" + (monthOfYear +1) + "/" + year;
                                Tgl.setText(selectedDate);
                            }
                        }, year, month, day);

                // Menampilkan DatePickerDialog
                datePickerDialog.show();
            }
        });

        btnminta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = Nama.getText().toString();
                String brg = Namabrg.getSelectedItem().toString();
                String tgl = Tgl.getText().toString();
                String ket = Ket.getSelectedItem().toString();

                DataSingleton dataSingleton = DataSingleton.getInstance();
                dataSingleton.setNama(nama);
                dataSingleton.setBarang(brg);
                dataSingleton.setTanggal(tgl);
                dataSingleton.setKeterangan(ket);

                // Lanjutkan ke Activity kedua
                if (dataSingleton.getNama() != null && dataSingleton.getBarang() != null
                        && dataSingleton.getTanggal() != null && dataSingleton.getKeterangan() != null) {
                    Toast.makeText(mintabrg.this, "Data Ditambahkan", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mintabrg.this, "Data gagal Ditambahkan", Toast.LENGTH_LONG).show();
                }
            }
        });

        new LoadSpinnerData().execute();
    }

    private class LoadSpinnerData extends AsyncTask<Void, Void, List<String>[]> {
        @Override
        protected List<String>[] doInBackground(Void... params) {
            List<String>[] spinnerData = new ArrayList[2];
            List<String> namaBarangList = new ArrayList<>();

            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.CONN();
            // Pastikan Anda telah melakukan koneksi database sebelumnya

            try {
                String query = "SELECT NAMA_BARANG FROM TB_STOCK";
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String namaBarang = resultSet.getString("NAMA_BARANG");
                    namaBarangList.add(namaBarang);
                }

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Mengisi data spinner pertama
            List<String> spinnerKetData = new ArrayList<>();
            spinnerKetData.add("Tambah");
            spinnerKetData.add("Musnahkan");
            spinnerData[0] = spinnerKetData;

            spinnerData[1] = namaBarangList;

            return spinnerData;
        }

        @Override
        protected void onPostExecute(List<String>[] result) {
            // Spinner pertama
            ArrayAdapter<String> spinner1Adapter = new ArrayAdapter<String>(mintabrg.this,
                    R.layout.spinner_item, result[0]);
            spinner1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Ket.setAdapter(spinner1Adapter);

            // Spinner kedua
            ArrayAdapter<String> spinner2Adapter = new ArrayAdapter<String>(mintabrg.this,
                    R.layout.spinner_item, result[1]);
            spinner2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Namabrg.setAdapter(spinner2Adapter);
        }
    }
}