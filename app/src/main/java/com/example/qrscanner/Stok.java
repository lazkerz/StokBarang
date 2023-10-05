package com.example.qrscanner;

public class Stok {

    private int id;
    private String namaBrg, Jmlh, Vendor, tgl;

    public Stok(int id, String namaBrg, String Jmlh, String Vendor, String tgl) {
        this.id = id;
        this.namaBrg = namaBrg;
        this.Jmlh = Jmlh;
        this.Vendor = Vendor;
        this.tgl = tgl;
    }

    public int getId() {
        return id;
    }

    public String getnamaBrg() {
        return namaBrg;
    }

    public String getJmlh() {
        return Jmlh;
    }

    public String getVendor() {
        return Vendor;
    }

    public String getTgl() {
        return tgl;
    }

}
