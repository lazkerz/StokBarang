package com.example.qrscanner;

public class Stok {

    private int id;
    private String noBarang;
    private String ket;
    private String tgl;
    private String namaBarang;
    private String kategoriBarang;

    public Stok(int id, String noBarang, String ket, String tgl, String namaBarang, String kategoriBarang) {
        this.id = id;
        this.noBarang = noBarang;
        this.ket = ket;
        this.tgl = tgl;
        this.namaBarang = namaBarang;
        this.kategoriBarang = kategoriBarang;
    }

    public int getId() {
        return id;
    }

    public String getNo() {
        return noBarang;
    }

    public String getKet() {
        return ket;
    }

    public String getTgl() {
        return tgl;
    }

    public String getBarang() {
        return namaBarang;
    }

    public String getKategori() {
        return kategoriBarang;
    }



}
