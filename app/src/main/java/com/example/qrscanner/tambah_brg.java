package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
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

public class tambah_brg extends AppCompatActivity {

    ImageButton back, home, btntambah;

    EditText Textid, Textno, Textnrk, Textnama, Textjabatan, Texttanggal;

    Spinner Namabrg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_brg);

        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        Namabrg = findViewById(R.id.spinnerNamabrg);
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
                String namabrg = Namabrg.getSelectedItem().toString();
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
                    // Mengisi spinner dengan data dari database
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
        new LoadNamaBarangTask().execute();
    }

    private class LoadNamaBarangTask extends AsyncTask<Void, Void, List<String>> {
        @Override
        protected List<String> doInBackground(Void... params) {
            List<String> namaBarangList = new ArrayList<>();

            // Kode untuk mengambil data dari database Oracle
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.CONN();
            // Pastikan Anda telah melakukan koneksi database sebelumnya

            try {
                String query = "SELECT NAMA_KATEGORI FROM TB_KATEGORI";
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String namaBarang = resultSet.getString("NAMA_KATEGORI");
                    namaBarangList.add(namaBarang);
                }

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return namaBarangList;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(tambah_brg.this,
                    R.layout.spinner_item, result) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView textView = (TextView) view.findViewById(android.R.id.text1);
                    textView.setTextColor(getResources().getColor(R.color.black)); // Ubah warna teks sesuai keinginan
                    return view;
                }
            };

            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Namabrg.setAdapter(spinnerAdapter);
        }
    }
}