package com.example.android_ck.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class TaiKhoan {
    @SerializedName("tentaikhoan")
    @Expose
    private String tentaikhoan;
    @SerializedName("matkhau")
    @Expose
    private String matkhau;
    @SerializedName("quyen")
    @Expose
    private String quyen;
    @SerializedName("ngaytao")
    @Expose
    private String ngaytao;

    // Getter và Setter cho tentaikhoan
    public String getTentaikhoan() {
        return tentaikhoan;
    }

    public void setTentaikhoan(String tentaikhoan) {
        this.tentaikhoan = tentaikhoan;
    }

    // Getter và Setter cho matkhau
    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    // Getter và Setter cho quyen
    public String getQuyen() {
        return quyen;
    }

    public void setQuyen(String quyen) {
        this.quyen = quyen;
    }

    // Getter và Setter cho ngaytao
    public String getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(String ngaytao) {
        this.ngaytao = ngaytao;
    }
}


