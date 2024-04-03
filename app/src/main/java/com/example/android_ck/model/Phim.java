package com.example.android_ck.model;

public class Phim {
    private Integer maphim;
    private String tenphim;
    private byte[] anhphim;
    private String ngaycongchieu;
    private String mota;
    private String thoiluong;
    private Integer gia;
    private Integer matheloai;

    public Phim(Integer maphim, String tenphim, byte[] anhphim, String ngaycongchieu, String mota, String thoiluong, Integer gia, Integer matheloai) {
        this.maphim = maphim;
        this.tenphim = tenphim;
        this.anhphim = anhphim;
        this.ngaycongchieu = ngaycongchieu;
        this.mota = mota;
        this.thoiluong = thoiluong;
        this.gia = gia;
        this.matheloai = matheloai;
    }

    // Getters và setters được giữ nguyên

    public Integer getMaphim() {
        return maphim;
    }

    public void setMaphim(Integer maphim) {
        this.maphim = maphim;
    }

    public String getTenphim() {
        return tenphim;
    }

    public void setTenphim(String tenphim) {
        this.tenphim = tenphim;
    }

    public byte[] getAnhphim() {
        return anhphim;
    }

    public void setAnhphim(byte[] anhphim) {
        this.anhphim = anhphim;
    }

    public String getNgaycongchieu() {
        return ngaycongchieu;
    }

    public void setNgaycongchieu(String ngaycongchieu) {
        this.ngaycongchieu = ngaycongchieu;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getThoiluong() {
        return thoiluong;
    }

    public void setThoiluong(String thoiluong) {
        this.thoiluong = thoiluong;
    }

    public Integer getGia() {
        return gia;
    }

    public void setGia(Integer gia) {
        this.gia = gia;
    }

    public Integer getMatheloai() {
        return matheloai;
    }

    public void setMatheloai(Integer matheloai) {
        this.matheloai = matheloai;
    }
}