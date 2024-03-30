package com.example.android_ck.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HoaDon {
    private Integer idhoadon;
    private String tentaikhoan;
    private String ngaydat;

    // Getter và Setter cho idhoadon
    public Integer getIdHoaDon() {
        return idhoadon;
    }

    public void setIdHoaDon(Integer idhoadon) {
        this.idhoadon = idhoadon;
    }

    // Getter và Setter cho tentaikhoan
    public String getTenTaiKhoan() {
        return tentaikhoan;
    }

    public void setTenTaiKhoan(String tentaikhoan) {
        this.tentaikhoan = tentaikhoan;
    }

    // Getter và Setter cho ngaydat
    public String getNgayDat() {
        return ngaydat;
    }

    public void setNgayDat(String ngaydat) {
        this.ngaydat = ngaydat;
    }
}
