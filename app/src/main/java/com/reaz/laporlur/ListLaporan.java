package com.reaz.laporlur;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ListLaporan {

    public String judul;
    public String isi;
    public String nama;
    public String email;
    public String rtrw;
    public String dukuh;
    public String tanggapan;
    public String isitanggapan;
    public String waktu;

    public ListLaporan() {
    }

    public ListLaporan(String judul, String isi, String nama, String email, String rtrw, String dukuh, String tanggapan, String isitanggapan, String waktu) {
        this.judul = judul;
        this.isi = isi;
        this.nama = nama;
        this.email = email;
        this.rtrw = rtrw;
        this.dukuh = dukuh;
        this.tanggapan = tanggapan;
        this.isitanggapan = isitanggapan;
        this.waktu = waktu;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("judul", judul);
        result.put("isi", isi);
        result.put("nama", nama);
        result.put("email", email);
        result.put("rtrw", rtrw);
        result.put("dukuh", dukuh);
        result.put("tanggapan", tanggapan);
        result.put("isitanggapan", isitanggapan);
        result.put("waktu", waktu);
        return result;
    }

}
