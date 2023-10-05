package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class
Login extends AppCompatActivity {

    TextView login, username, password, akun;

    EditText usn, psd;

    CheckBox ShowPassword;

    ImageButton btnlogin;
    Button btnregis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        ShowPassword = findViewById(R.id.checkBoxShowPassword);
        akun = findViewById(R.id.akun);
        usn = findViewById(R.id.Usn);
        psd = findViewById(R.id.Psd);

        btnlogin = findViewById(R.id.btnlogin);
        btnregis = findViewById(R.id.regis);

        ShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Tampilkan password
                   psd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    // Sembunyikan password
                    psd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = usn.getText().toString();
                String pass = psd.getText().toString();

                if (!user.isEmpty() && !pass.isEmpty()) {

                    ConnectionClass connectionClass = new ConnectionClass();
                    Connection connection = connectionClass.CONN();
                    try {
                        String query = "SELECT Username, Password, Role FROM TB_USER WHERE Username = ? AND Password = ?";

                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1, user);
                        statement.setString(2, pass);
                        ResultSet resultSet = statement.executeQuery();

                        if (resultSet.next()) {
                            String role = resultSet.getString("Role");

                            if(role.equals("Admin")){
                                Intent pindah = new Intent(Login.this, dashboard_adm.class);
                                pindah.putExtra("user", usn.getText().toString());
                                startActivity(pindah);
                            } else if (role.equals("User")) {
                                Intent pindah = new Intent(Login.this, dashboard_user.class);
                                pindah.putExtra("user", usn.getText().toString());
                                startActivity(pindah);
                            }else if (role.equals("Manajemen")) {
                                Intent pindah = new Intent(Login.this, dashboard_manajemen.class);
                                pindah.putExtra("user", usn.getText().toString());
                                startActivity(pindah);
                            }else if (role.equals("Pengadaan")) {
                                Intent pindah = new Intent(Login.this, dashboard_pengadaan.class);
                                pindah.putExtra("user", usn.getText().toString());
                                startActivity(pindah);
                            }else {
                                Toast.makeText(Login.this, "Peran pengguna tidak valid", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(Login.this, "Username Atau Password Salah", Toast.LENGTH_LONG).show();
                        }

                        resultSet.close();
                        statement.close();
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(Login.this, "Isi Username dan Password", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(Login.this, Registrasi.class);
                startActivity(pindah);
            }
        });
    }
}