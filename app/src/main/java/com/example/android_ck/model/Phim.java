package com.example.android_ck.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Phim {
    @SerializedName("maphim")
    @Expose
    private Integer maphim;
    @SerializedName("tenphim")
    @Expose
    private String tenphim;
    @SerializedName("anhphim")
    @Expose
    private String anhphim;
    @SerializedName("ngaycongchieu")
    @Expose
    private String ngaycongchieu;
    @SerializedName("mota")
    @Expose
    private String mota;
    @SerializedName("thoiluong")
    @Expose
    private String thoiluong;
    @SerializedName("gia")
    @Expose
    private Integer gia;
    @SerializedName("matheloai")
    @Expose
    private Integer matheloai;

    // Getter và Setter cho maphim
    public Integer getMaPhim() {
        return maphim;
    }

    public void setMaPhim(Integer maphim) {
        this.maphim = maphim;
    }

    // Getter và Setter cho tenphim
    public String getTenPhim() {
        return tenphim;
    }

    public void setTenPhim(String tenphim) {
        this.tenphim = tenphim;
    }

    // Getter và Setter cho anhphim
    public String getAnhPhim() {
        return anhphim;
    }

    public void setAnhPhim(String anhphim) {
        this.anhphim = anhphim;
    }

    // Getter và Setter cho ngaycongchieu
    public String getNgayCongChieu() {
        return ngaycongchieu;
    }

    public void setNgayCongChieu(String ngaycongchieu) {
        this.ngaycongchieu = ngaycongchieu;
    }

    // Getter và Setter cho mota
    public String getMoTa() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    // Getter và Setter cho thoiluong
    public String getThoiLuong() {
        return thoiluong;
    }

    public void setThoiLuong(String thoiluong) {
        this.thoiluong = thoiluong;
    }

    // Getter và Setter cho gia
    public Integer getGia() {
        return gia;
    }

    public void setGia(Integer gia) {
        this.gia = gia;
    }

    // Getter và Setter cho matheloai
    public Integer getMaTheLoai() {
        return matheloai;
    }

    public void setMaTheLoai(Integer matheloai) {
        this.matheloai = matheloai;
    }
}
