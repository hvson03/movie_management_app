package com.example.android_ck.khachhang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_ck.DBHelper;
import com.example.android_ck.R;
import com.example.android_ck.quanly.MainActivity_quanly;

public class khachhang_dangnhap extends AppCompatActivity {
    EditText edit_tentk, edit_matkhau;
    Button btn_dangnhap;
    TextView tv_dangky, tv_quenmk;

    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_khachhang_dangnhap);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edit_matkhau = findViewById(R.id.edit_matkhau);
        edit_tentk = findViewById(R.id.edit_tentk);
        btn_dangnhap = findViewById(R.id.btn_dangnhap);
        tv_dangky = findViewById(R.id.tv_dangky);
        tv_quenmk = findViewById(R.id.tv_quenmk);

        dbHelper = new DBHelper(this);

        btn_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tentk, matkhau;
                tentk = edit_tentk.getText().toString().trim();
                matkhau = edit_matkhau.getText().toString().trim();

                if(tentk.isEmpty()) {
                    edit_tentk.setError("Vui lòng nhập tên tài khoản");
                    edit_tentk.requestFocus();
                    return;
                } else if (matkhau.isEmpty()) {
                    edit_matkhau.setError("Vui lòng nhập mật khẩu");
                    edit_matkhau.requestFocus();
                    return;
                }

                    boolean ktraDangnhap = dbHelper.ktraDangnhap(tentk, matkhau);
                    if(ktraDangnhap) {
                        if (tentk.equals("admin")) {
                            Intent intent = new Intent(khachhang_dangnhap.this, MainActivity_quanly.class);
                            startActivity(intent);
                            Toast.makeText(khachhang_dangnhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent myintent = new Intent(khachhang_dangnhap.this, MainActivity_khachhang.class);
                            // Đóng gói dữ liệu và đưa dữ liệu vào Bundle
                            Bundle mybundle = new Bundle();
                            mybundle.putString("tk", tentk);

                            // Đưa Bundle vào Intent
                            myintent.putExtra("dangnhappacket", mybundle);
                            startActivity(myintent);
                            Toast.makeText(khachhang_dangnhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(khachhang_dangnhap.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                        edit_tentk.setText("");
                        edit_matkhau.setText("");
                    }

            }
        });

        tv_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(khachhang_dangnhap.this, khachhang_dangky.class);
                startActivity(myintent);
            }
        });

        tv_quenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(khachhang_dangnhap.this, khachHang_quenmatkhau.class);
                startActivity(myintent);
            }
        });
    }
}