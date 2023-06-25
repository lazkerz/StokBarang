package com.example.qrscanner;

public class Kategori {
    private int id;
    private String namaKategori, jmlh;

    public Kategori(int id, String namaKategori, String jmlh) {
        this.id = id;
        this.namaKategori = namaKategori;
        this.jmlh = jmlh;

    }

    public int getId() {
        return id;
    }

    public String getKategori() {
        return namaKategori;
    }

    public String getJmlh() {
        return jmlh;
    }
}
