package com.example.android_ck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.android_ck.khachhang.khachhang_dangnhap;

public class IntroActivity extends AppCompatActivity {
    ImageButton btn_batdau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        btn_batdau = findViewById(R.id.btn_batdau);
        btn_batdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, khachhang_dangnhap.class);
                startActivity(intent);
            }
        });
    }
}