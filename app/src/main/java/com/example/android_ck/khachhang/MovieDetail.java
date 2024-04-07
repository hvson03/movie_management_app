package com.example.android_ck.khachhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android_ck.R;

public class MovieDetail extends AppCompatActivity {

    ImageButton imgb_kh_chitietphim_back, imgb_kh_chitietphim_yeuthich, txt_kh_chitietphim_datve;
    ImageView imgv_kh_chitietphim_anh;
    TextView txt_kh_chitietphim_tenphim, txt_kh_chitietphim_thoiluong, txt_kh_chitietphim_khoichieu,
            txt_kh_chitietphim_theloai,txt_kh_chitietphim_giave, txt_kh_chitietphim_mota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khachhang_phim_chitiet);
        imgb_kh_chitietphim_back = findViewById(R.id.imgb_kh_chitietphim_back);
        imgb_kh_chitietphim_yeuthich = findViewById(R.id.imgb_kh_chitietphim_yeuthich);
        imgv_kh_chitietphim_anh = findViewById(R.id.imgv_kh_chitietphim_anh);
        txt_kh_chitietphim_tenphim = findViewById(R.id.txt_kh_chitietphim_tenphim);
        txt_kh_chitietphim_thoiluong = findViewById(R.id.txt_kh_chitietphim_thoiluong);
        txt_kh_chitietphim_khoichieu = findViewById(R.id.txt_kh_chitietphim_khoichieu);
        txt_kh_chitietphim_theloai = findViewById(R.id.txt_kh_chitietphim_theloai);
        txt_kh_chitietphim_giave = findViewById(R.id.txt_kh_chitietphim_giave);
        txt_kh_chitietphim_mota = findViewById(R.id.txt_kh_chitietphim_mota);
        txt_kh_chitietphim_datve = findViewById(R.id.txt_kh_chitietphim_datve);

        imgb_kh_chitietphim_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        byte[] anhphim = intent.getByteArrayExtra("anhphim");

        // Hiển thị hình ảnh
        Bitmap bitmap = BitmapFactory.decodeByteArray(anhphim, 0, anhphim.length);
        imgv_kh_chitietphim_anh.setImageBitmap(bitmap);

        String tenphim = intent.getStringExtra("tenphim");
        String tentheloai = intent.getStringExtra("tentheloai");
        String thoiluong = intent.getStringExtra("thoiluong");
        String ngaychieu = intent.getStringExtra("ngaychieu");
        int giave = intent.getIntExtra("giave",0);
        String mota = intent.getStringExtra("mota");

        txt_kh_chitietphim_tenphim.setText(tenphim);
        txt_kh_chitietphim_theloai.setText(tentheloai);
        txt_kh_chitietphim_mota.setText(mota);
        txt_kh_chitietphim_thoiluong.setText(thoiluong);
        txt_kh_chitietphim_khoichieu.setText(ngaychieu);
        txt_kh_chitietphim_giave.setText(String.valueOf(giave)+" VNĐ / 1 vé");
    }
}