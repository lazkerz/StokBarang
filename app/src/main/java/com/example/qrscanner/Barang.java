package com.example.qrscanner;

public class Barang {

    private int id;
    private String kategoriBarang;
    private String noBarang;
    private String namaBarang;
    private String nrk;
    private String nama;
    private String jabatan;
    private String Tgl;
    private String IdBrg;

    public Barang(int id, String kategoriBarang, String noBarang, String namaBarang, String nrk, String nama, String jabatan, String Tgl, String IdBrg) {
        this.id = id;
        this.kategoriBarang = kategoriBarang;
        this.noBarang = noBarang;
        this.namaBarang = namaBarang;
        this.nrk = nrk;
        this.nama = nama;
        this.jabatan = jabatan;
        this.Tgl = Tgl;
        this.IdBrg = IdBrg;
    }

    public int getId() {
        return id;
    }

    public String getBarang() {
        return namaBarang;
    }

    public String getKategori() {
        return kategoriBarang;
    }

    public String getNo() {
        return noBarang;
    }

    public String getNama() {
        return nama;
    }

    public String getJabatan() {
        return jabatan;
    }

    public String getNrk() {
        return nrk;
    }
    public String getTgl() {
        return Tgl;
    }
    public String getIdBrg(){
        return IdBrg;
    }
}
