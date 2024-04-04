package com.example.android_ck.model;

public class TheLoai {
    private Integer matheloai;
    private String tentheloai;

    public TheLoai(Integer matheloai, String tentheloai) {
        this.matheloai = matheloai;
        this.tentheloai = tentheloai;
    }

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
