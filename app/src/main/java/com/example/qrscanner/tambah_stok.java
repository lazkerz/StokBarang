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
    EditText Noinven, Txtket, Txttgl, Txtnama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_stok);

        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        btntambah = findViewById(R.id.btntambah);
        Noinven = findViewById(R.id.Noinven);
        Txtket = findViewById(R.id.Txtket);
        Txttgl = findViewById(R.id.Txttgl);
        Txtnama = findViewById(R.id.Txtnama);

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
                Intent pindah = new Intent(tambah_stok.this, dashboard_adm.class);
                startActivity(pindah);
            }
        });

        Txttgl.setOnClickListener(new View.OnClickListener() {
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
                                Txttgl.setText(selectedDate);
                            }
                        }, year, month, day);

                // Menampilkan DatePickerDialog
                datePickerDialog.show();
            }
        });

        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String no = Noinven.getText().toString();
                String ket = Txtket.getText().toString();
                String tgl = Txttgl.getText().toString();
                String nama = Txtnama.getText().toString();
                String kategori = nama;

                // Ambil data dari database
                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.CONN();
                // Anda dapat menggunakan koneksi ke database dan eksekusi query di sini

                try {
                    String query = "INSERT INTO TB_STOK (No_Inventaris, Keterangan, Tgl_Penyerahan, Nama_Barang, NAMA_KATEGORI) VALUES (?,?,?,?,?)";

                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, no);
                    statement.setString(2, ket);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = dateFormat.parse(tgl);

                    statement.setDate(3, new java.sql.Date(date.getTime()));
                    statement.setString(4, kategori);
                    statement.setString(5, nama);

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