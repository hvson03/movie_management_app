package com.example.android_ck.model;

public class item_user {
    String tk, hoten;

    public item_user(String tk, String hoten) {
        this.tk = tk;
        this.hoten = hoten;
    }

    public String getTk() {
        return tk;
    }

    public void setTk(String tk) {
        this.tk = tk;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

}
