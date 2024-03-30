package com.example.android_ck.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DanhSachYeuThich {
    @SerializedName("iddansach")
    @Expose
    private Integer iddansach;
    @SerializedName("tentaikhoan")
    @Expose
    private String tentaikhoan;
    @SerializedName("maphim")
    @Expose
    private Integer maphim;

    // Getter và Setter cho iddansach
    public Integer getIdDanhSach() {
        return iddansach;
    }

    public void setIdDanhSach(Integer iddansach) {
        this.iddansach = iddansach;
    }

    // Getter và Setter cho tentaikhoan
    public String getTenTaiKhoan() {
        return tentaikhoan;
    }

    public void setTenTaiKhoan(String tentaikhoan) {
        this.tentaikhoan = tentaikhoan;
    }

    // Getter và Setter cho maphim
    public Integer getMaPhim() {
        return maphim;
    }

    public void setMaPhim(Integer maphim) {
        this.maphim = maphim;
    }
}
