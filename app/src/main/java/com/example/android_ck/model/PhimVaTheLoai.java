package com.example.android_ck.model;

public class PhimVaTheLoai {
    private int maphim;
    private String tenphim;
    private byte[] anhphim;
    private String ngaycongchieu;
    private String mota;
    private String thoiluong;
    private int gia;
    private int matheloai;
    private String tentheloai;

    public PhimVaTheLoai(int maphim, String tenphim, byte[] anhphim, String ngaycongchieu, String mota, String thoiluong, int gia, int matheloai, String tentheloai) {
        this.maphim = maphim;
        this.tenphim = tenphim;
        this.anhphim = anhphim;
        this.ngaycongchieu = ngaycongchieu;
        this.mota = mota;
        this.thoiluong = thoiluong;
        this.gia = gia;
        this.matheloai = matheloai;
        this.tentheloai = tentheloai;
    }

    public int getMaphim() {
        return maphim;
    }

    public String getTenphim() {
        return tenphim;
    }

    public byte[] getAnhphim() {
        return anhphim;
    }

    public String getNgaycongchieu() {
        return ngaycongchieu;
    }

    public String getMota() {
        return mota;
    }

    public String getThoiluong() {
        return thoiluong;
    }

    public int getGia() {
        return gia;
    }

    public int getMatheloai() {
        return matheloai;
    }

    public String getTentheloai() {
        return tentheloai;
    }
}
