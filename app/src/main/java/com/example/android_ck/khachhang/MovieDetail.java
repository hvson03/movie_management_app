package com.example.android_ck.khachhang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_ck.DBHelper;
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

        Integer maphim = intent.getIntExtra("maphim",0);
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

        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String tentaikhoan = sharedPreferences.getString("tentaikhoan", "");

        imgb_kh_chitietphim_yeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper myDB = new DBHelper(MovieDetail.this);
                if (!myDB.kiemTraDSYTTonTai(tentaikhoan, maphim)) {
                    if(myDB.themDanhSachYeuThich(tentaikhoan, maphim))
                        Toast.makeText(MovieDetail.this, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MovieDetail.this, "Thêm không thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MovieDetail.this, "Đã có trong danh sách yêu thích", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txt_kh_chitietphim_datve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper myDB = new DBHelper(MovieDetail.this);
                if (!myDB.kiemTraHoaDonTonTai(tentaikhoan, maphim)) {
                    if(myDB.themHoaDonMoi(tentaikhoan, maphim))
                        Toast.makeText(MovieDetail.this, "Đã thêm vào hóa đơn", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MovieDetail.this, "Thêm không thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MovieDetail.this, "Đã có trong hóa đơn", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}