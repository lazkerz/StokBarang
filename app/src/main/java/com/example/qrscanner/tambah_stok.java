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
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class tambah_stok extends AppCompatActivity {

    ImageButton back, home, btntambah;
    EditText Namabrg, Jmlh, Vendor, Tgl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_stok);

        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        btntambah = findViewById(R.id.btntambah);
        Namabrg = findViewById(R.id.namabrg);
        Jmlh = findViewById(R.id.Jmlh);
        Vendor = findViewById(R.id.Vendor);
        Tgl = findViewById(R.id.Tgl);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(tambah_stok.this, SemuaStok.class);
                startActivity(pindah);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(tambah_stok.this, dashboard_pengadaan.class);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(tambah_stok.this,
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

        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = Namabrg.getText().toString();
                String jlh = Jmlh.getText().toString();
                String vendor = Vendor.getText().toString();
                String tgl = Tgl.getText().toString();

                // Ambil data dari database
                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.CONN();
                // Anda dapat menggunakan koneksi ke database dan eksekusi query di sini

                try {
                    String query = "INSERT INTO TB_STOCK (NAMA_BARANG, JUMLAH, VENDOR, Tgl_Penyerahan) VALUES (?,?,?,?)";

                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, nama);
                    statement.setString(2, jlh);
                    statement.setString(3, vendor);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = dateFormat.parse(tgl);

                    statement.setDate(4, new java.sql.Date(date.getTime()));

                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        Toast.makeText(tambah_stok.this, "Data Ditambahkan", Toast.LENGTH_LONG).show();
                        Intent pindah = new Intent(tambah_stok.this, SemuaStok.class);
                        startActivity(pindah);
                    }else{
                        Toast.makeText(tambah_stok.this, "Data gagal Ditambahkan", Toast.LENGTH_LONG).show();
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
    }
}