package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import android.os.Bundle;
import android.widget.TextView;

public class Registrasi extends AppCompatActivity {

    TextView nrk, nama, email, usn, pass, jabatan;

    EditText txtnrk, txtnama, txtemail, txtusn, txtpass, txtjbtn;

    Button login;
    ImageButton regis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        nrk = findViewById(R.id.nrk);
        nama = findViewById(R.id.txtnama);
        email = findViewById(R.id.txtemail);
        usn = findViewById(R.id.txtusername);
        pass = findViewById(R.id.txtpassword);
        jabatan = findViewById(R.id.txtjabatan);

        txtnrk = findViewById(R.id.Nrk);
        txtnama = findViewById(R.id.Txtnama);
        txtemail = findViewById(R.id.Txtemail);
        txtusn = findViewById(R.id.Txtusername);
        txtpass = findViewById(R.id.Txtpassword);
        txtjbtn = findViewById(R.id.Txtjabatan);

        regis = findViewById(R.id.btnregis);
        login = findViewById(R.id.login);

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nrk = txtnrk.getText().toString();
                String nama = txtnama.getText().toString();
                String email = txtemail.getText().toString();
                String usn = txtusn.getText().toString();
                String pass = txtpass.getText().toString();
                String jbtn = txtjbtn.getText().toString();
                String role = "User";

                if (!nrk.isEmpty() && !nama.isEmpty() && !email.isEmpty() && !usn.isEmpty() && !pass.isEmpty() && !jbtn.isEmpty()) {

                    ConnectionClass connectionClass = new ConnectionClass();
                    Connection connection = connectionClass.CONN();
                    try {
                        String query = "INSERT INTO TB_USER (Nrk, Nama, Email, Username, Password, Jabatan, Role) VALUES (?,?,?,?,?,?,?)";

                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1, nrk);
                        statement.setString(2, nama);
                        statement.setString(3, email);
                        statement.setString(4, usn);
                        statement.setString(5, pass);
                        statement.setString(6, jbtn);
                        statement.setString(7, role);

                        int rowsInserted = statement.executeUpdate();

                        if (rowsInserted > 0) {
                            Toast.makeText(Registrasi.this, "Registrasi Selesai. Silahkan Login!", Toast.LENGTH_LONG).show();
                            Intent pindah = new Intent(Registrasi.this, Login.class);
                                startActivity(pindah);
                            }
                        statement.close();
                        connection.close();
                    }catch (SQLException e) {

                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(Registrasi.this, "Isi Semua", Toast.LENGTH_LONG).show();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(Registrasi.this, Login.class);
                startActivity(pindah);
            }
        });
    }
}