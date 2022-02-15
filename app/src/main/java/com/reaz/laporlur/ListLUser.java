package com.reaz.laporlur;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ListLUser {


    public String nama;

    public ListLUser() {
    }

    public ListLUser(String nama) {
        this.nama = nama;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nama", nama);
        return result;
    }

}
