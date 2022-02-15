package com.reaz.laporlur;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Berita {

    public String judul;
    public String isi;
    public String urlimage;

    public Berita() {
    }

    public Berita(String judul, String isi, String urlimage) {
        this.judul = judul;
        this.isi = isi;
        this.urlimage = urlimage;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("judul", judul);
        result.put("isi", isi);
        result.put("urlimage", urlimage);
        return result;
    }

}
