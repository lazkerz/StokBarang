package com.example.qrscanner;

public class Setuju {
    private int id;

    private String Persetujuan, Jmlh;

    public Setuju(int id, String Persetujuan, String Jmlh) {
        this.id = id;
        this.Persetujuan = Persetujuan;
        this.Jmlh = Jmlh;
    }

    public int getId() {
        return id;
    }

    public String getPersetujuan() {
        return Persetujuan;
    }

    public String getJmlh() {
        return Jmlh;
    }
}
