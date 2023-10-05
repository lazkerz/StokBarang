package com.example.qrscanner;

public class Minta {
    private int id;

    private String Idpermintaan, Nama, NamaBrg, Tgl, Keterangan, Keputusan;

    public Minta(int id, String Idpermintaan, String Nama, String NamaBrg, String Tgl, String Keterangan, String Keputusan) {
        this.id = id;
        this.Idpermintaan = Idpermintaan;
        this.Nama = Nama;
        this.NamaBrg = NamaBrg;
        this.Tgl = Tgl;
        this.Keterangan = Keterangan;
        this.Keputusan = Keputusan;
    }

    public int getId() {
        return id;
    }
    public String getIdpermintaan() {
        return Idpermintaan;
    }

    public String getNama() {
        return Nama;
    }

    public String getNamaBrg() {
        return NamaBrg;
    }

    public String getTgl() {
        return Tgl;
    }

    public String getKeterangan() {
        return Keterangan;
    }

    public String getKeputusan() {
        return Keputusan;
    }
}
