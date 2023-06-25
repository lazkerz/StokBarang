package com.example.qrscanner;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;



public class CekBrg extends AppCompatActivity {

    ImageButton home, back, check, scan;
    TextView hasil, textno, no, textnamabrg, namabrg, textnrk, nrk, textnama, nama, textjbtn, jbtn, Tgl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cekbrg);

        check = findViewById(R.id.cek);
        scan = findViewById(R.id.qr);
        hasil = findViewById(R.id.text);
        home = findViewById(R.id.home);
        back = findViewById(R.id.back);
        textno = findViewById(R.id.textno);
        no = findViewById(R.id.no);
        textnamabrg = findViewById(R.id.textnamabrg);
        namabrg = findViewById(R.id.namabrg);
        textnrk = findViewById(R.id.textnrk);
        nrk = findViewById(R.id.nrk);
        textnama = findViewById(R.id.textnama);
        nama = findViewById(R.id.nama);
        textjbtn = findViewById(R.id.textjbtn);
        jbtn = findViewById(R.id.jbtn);
        Tgl = findViewById(R.id.tgl);


//        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(threadPolicy);


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(CekBrg.this);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.setPrompt("Scan a QR Code");
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.initiateScan();
            }
        });



        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(CekBrg.this, dashboard_user.class);
                startActivity(pindah);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(CekBrg.this, dashboard_user.class);
                startActivity(pindah);
            }
        });
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
            if(intentResult != null){
                String contents = intentResult.getContents();
                String[] idbarang = contents.split("=");
                if(idbarang.length > 1){
                    hasil.setText(("ID : "+ idbarang[1]));
                    String idBarang = idbarang[1];

                check.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
//                    Intent pindah = new Intent(MainActivity.this, Select.class);
//                    pindah.putExtra("ID",idBarang);
//                    startActivity(pindah);
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
                       } catch (SQLException e) {
                           e.printStackTrace();
                       }
                    }
                });
                }else{
                    super.onActivityResult(requestCode, resultCode, data);
                }
            }
    }
}