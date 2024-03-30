package com.example.android_ck.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TheLoai {
    private Integer matheloai;
    private String tentheloai;

    // Getter và Setter cho matheloai
    public Integer getMaTheloai() {
        return matheloai;
    }

    public void setMaTheloai(Integer matheloai) {
        this.matheloai = matheloai;
    }

    // Getter và Setter cho tentheloai
    public String getTenTheloai() {
        return tentheloai;
    }

    public void setTenTheloai(String tentheloai) {
        this.tentheloai = tentheloai;
    }
}
