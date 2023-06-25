package com.example.qrscanner;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionClass{
    private static final String DEFAULT_DRIVER ="oracle.jdbc.driver.OracleDriver";
    private static final String DEFAULT_URL = "jdbc:oracle:thin:@34.80.190.235:1521:xe";
    private static final String DEFAULT_USERNAME = "hr";
    private static final String DEFAULT_PASSWORD = "azra";

    private Connection connection;

    @SuppressLint("NewAPi")
    public Connection CONN(){
        if (android.os.Build.VERSION.SDK_INT> 24) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }
        try {
            connection = createConnection();
        }catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        }catch (ClassNotFoundException e) {
            Log.e("ERRO",e.getMessage());
        }catch(Exception e){
            Log.e("ERRO",e.getMessage());
        }
        return connection;
    }
    public static Connection createConnection(String driver, String url, String username, String password) throws ClassNotFoundException,
            SQLException{
        Class.forName(driver);
        return DriverManager.getConnection(url,username,password);
    }
    public static Connection createConnection() throws ClassNotFoundException,
            SQLException{
        return createConnection(DEFAULT_DRIVER,DEFAULT_URL,DEFAULT_USERNAME,DEFAULT_PASSWORD);
    }
}

