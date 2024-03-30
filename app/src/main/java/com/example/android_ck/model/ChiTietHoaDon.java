package com.example.android_ck.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChiTietHoaDon {
    @SerializedName("idcthoadon")
    @Expose
    private Integer idcthoadon;
    @SerializedName("mahoadon")
    @Expose
    private Integer mahoadon;
    @SerializedName("maphim")
    @Expose
    private Integer maphim;
    @SerializedName("soluong")
    @Expose
    private Integer soluong;
    @SerializedName("thanhtien")
    @Expose
    private Integer thanhtien;

    // Getter và Setter cho idcthoadon
    public Integer getIdChiTietHoaDon() {
        return idcthoadon;
    }

    public void setIdChiTietHoaDon(Integer idcthoadon) {
        this.idcthoadon = idcthoadon;
    }

    // Getter và Setter cho mahoadon
    public Integer getMaHoaDon() {
        return mahoadon;
    }

    public void setMaHoaDon(Integer mahoadon) {
        this.mahoadon = mahoadon;
    }

    // Getter và Setter cho maphim
    public Integer getMaPhim() {
        return maphim;
    }

    public void setMaPhim(Integer maphim) {
        this.maphim = maphim;
    }

    // Getter và Setter cho soluong
    public Integer getSoLuong() {
        return soluong;
    }

    public void setSoLuong(Integer soluong) {
        this.soluong = soluong;
    }

    // Getter và Setter cho thanhtien
    public Integer getThanhTien() {
        return thanhtien;
    }

    public void setThanhTien(Integer thanhtien) {
        this.thanhtien = thanhtien;
    }
}
