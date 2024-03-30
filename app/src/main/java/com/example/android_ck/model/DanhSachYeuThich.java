package com.example.android_ck.model;

public class DanhSachYeuThich {
    private Integer iddansach;
    private String tentaikhoan;
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
