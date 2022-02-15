package com.reaz.laporlur;

public class Lapor {
    private String nama;
    private String email;
    private String judul;
    private String isi;
    private String rtrw;
    private String dukuh;
    private String tanggapan;
    private String isitanggapan;
    private String waktu;

    public Lapor() {

    }


    public Lapor(String nama, String email, String judul, String isi, String rtrw, String dukuh, String tanggapan, String isitanggapan, String waktu) {
        this.nama = nama;
        this.email = email;
        this.judul = judul;
        this.isi = isi;
        this.rtrw = rtrw;
        this.dukuh = dukuh;
        this.tanggapan = tanggapan;
        this.isitanggapan = isitanggapan;
        this.waktu = waktu;

    }

    public String getIsitanggapan() {
        return isitanggapan;
    }

    public void setIsitanggapan(String isitanggapan) {
        this.isitanggapan = isitanggapan;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
    public String getEmail() {
        return email;
    }

    public String getTanggapan() {
        return tanggapan;
    }

    public void setTanggapan(String tanggapan) {
        this.tanggapan = tanggapan;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getRtrw() {
        return rtrw;
    }

    public void setRtrw(String rtrw) {
        this.rtrw = rtrw;
    }

    public String getDukuh() {
        return dukuh;
    }

    public void setDukuh(String dukuh) {
        this.dukuh = dukuh;
    }
}
