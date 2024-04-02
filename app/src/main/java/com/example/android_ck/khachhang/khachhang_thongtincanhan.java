package com.example.android_ck.khachhang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_ck.DBHelper;
import com.example.android_ck.R;

public class khachhang_thongtincanhan extends AppCompatActivity {
    EditText edit_hoten, edit_ngaysinh, edit_email, edit_sdt;
    RadioGroup rb_gr;
    TextView tv_boqua;
    Button btn_thongtincanhan;

    DBHelper dbHelper;

    String regex_hoten = "^[a-zA-Z\\s]+$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_khachhang_thongtincanhan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edit_hoten = findViewById(R.id.edit_hoten);
        edit_ngaysinh = findViewById(R.id.edit_ngaysinh);
        edit_email = findViewById(R.id.edit_email);
        edit_sdt = findViewById(R.id.edit_sdt);
        rb_gr = findViewById(R.id.rb_gr);
        tv_boqua = findViewById(R.id.tv_boqua);
        btn_thongtincanhan = findViewById(R.id.btn_thongtincanhan);

        dbHelper = new DBHelper(this);

        btn_thongtincanhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoten, ngaysinh, gioitinh = "", email, sdt;
                int ktra_rb_gr;
                hoten = edit_hoten.getText().toString().trim();
                ngaysinh = edit_ngaysinh.getText().toString().trim();
                email = edit_email.getText().toString().trim();
                sdt = edit_sdt.getText().toString().trim();
                ktra_rb_gr = rb_gr.getCheckedRadioButtonId();

                // Nhận Intent
                Intent myintent1 = getIntent();
                // Lấy Bundle ra khỏi Intent
                Bundle mybundle = myintent1.getBundleExtra("dangkypackage");
                String tk = mybundle.getString("tk");

                if (ktra_rb_gr == R.id.rb_nam) {
                    gioitinh = "nam";
                } else if (ktra_rb_gr == R.id.rb_nu) {
                    gioitinh = "nu";
                }

                if (hoten.isEmpty()) {
                    edit_hoten.setError("Vui lòng nhập tên nhân viên");
                    edit_hoten.requestFocus();
                    return;
                } else if (!hoten.matches(regex_hoten)) {
                    edit_hoten.setError("Tên yêu cầu chỉ nhập chữ cái");
                    edit_hoten.requestFocus();
                    return;
                }

                boolean thongtincanhan = dbHelper.themThongTinCaNhan(hoten, gioitinh, ngaysinh, email, sdt, tk);
                if (thongtincanhan) {
                    Toast.makeText(khachhang_thongtincanhan.this, "Thêm thông tin thành công", Toast.LENGTH_SHORT).show();
                    Intent myintent = new Intent(khachhang_thongtincanhan.this, MainActivity_khachhang.class);
                    startActivity(myintent);
                } else {
                    Toast.makeText(khachhang_thongtincanhan.this, "Thêm thông tin không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_boqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(khachhang_thongtincanhan.this, MainActivity_khachhang.class);
                startActivity(myintent);
            }
        });

    }
}