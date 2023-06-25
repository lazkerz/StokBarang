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

public class tambah_brg extends AppCompatActivity {

    ImageButton back, home, btntambah;

    EditText Namabrg, Textid, Textno, Textnrk, Textnama, Textjabatan, Texttanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_brg);

        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        Namabrg = findViewById(R.id.Namabrng);
        Textid = findViewById(R.id.Txtid);
        Textno = findViewById(R.id.Txtno);
        Textnrk = findViewById(R.id.Txtnrk);
        Textnama = findViewById(R.id.Txtnama);
        Textjabatan = findViewById(R.id.Txtjabatan);
        Texttanggal = findViewById(R.id.Txttanggal);
        btntambah = findViewById(R.id.btntambah);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(tambah_brg.this, dashboard_adm.class);
                startActivity(pindah);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(tambah_brg.this, dashboard_adm.class);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(tambah_brg.this,
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

        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namabrg = Namabrg.getText().toString();
                String id = Textid.getText().toString();
                String no = Textno.getText().toString();
                String nrk= Textnrk.getText().toString();
                String nama = Textnama.getText().toString();
                String jbtn = Textjabatan.getText().toString();
                String tgl = Texttanggal.getText().toString();
                String kategori = namabrg;

                // Ambil data dari database
                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.CONN();
                // Anda dapat menggunakan koneksi ke database dan eksekusi query di sini

                try {
                    String query = "INSERT INTO TB_BARANG (Nama_Barang, ID, No_Inventaris, Nrk, Nama, Jabatan, Tgl_Terima, NAMA_KATEGORI) VALUES (?,?,?,?,?,?,?,?)";

                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, namabrg);
                    statement.setString(2, id);
                    statement.setString(3, no);
                    statement.setString(4, nrk);
                    statement.setString(5, nama);
                    statement.setString(6, jbtn);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = dateFormat.parse(tgl);

                    statement.setDate(7, new java.sql.Date(date.getTime()));
                    statement.setString(8, kategori);

                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        Toast.makeText(tambah_brg.this, "Data Ditambahkan", Toast.LENGTH_LONG).show();
                        Intent pindah = new Intent(tambah_brg.this, SemuaBrg.class);
                        startActivity(pindah);
                    }else{
                        Toast.makeText(tambah_brg.this, "Data gagal Ditambahkan", Toast.LENGTH_LONG).show();
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