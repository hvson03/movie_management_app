package com.example.android_ck.model;

public class item_user {
    String tk, email;

    public item_user(String tk, String email) {
        this.tk = tk;
        this.email = email;
    }

    public String getTk() {
        return tk;
    }

    public void setTk(String tk) {
        this.tk = tk;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
