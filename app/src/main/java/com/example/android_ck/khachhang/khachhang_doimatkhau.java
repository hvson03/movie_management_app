package com.example.android_ck.khachhang;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_ck.DBHelper;
import com.example.android_ck.R;

public class khachhang_doimatkhau extends AppCompatActivity {
    EditText edit_dmk_mk, edit_dmk_xacnhanmk;
    ImageView img_quaylaittcn;
    TextView tv_dmk_tk, tv_dmk_mk;
    Button btn_luumk;
    String regex_matkhau = "^[a-zA-Z0-9]{4,}$";
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_khachhang_doimatkhau);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edit_dmk_mk = findViewById(R.id.edit_dmk_mk);
        edit_dmk_xacnhanmk = findViewById(R.id.edit_dmk_xacnhanmk);
        img_quaylaittcn = findViewById(R.id.img_quaylaittcn);
        tv_dmk_tk = findViewById(R.id.tv_dmk_tk);
        tv_dmk_mk = findViewById(R.id.tv_dmk_mk);
        btn_luumk = findViewById(R.id.btn_luumk);

        dbHelper = new DBHelper(this);

        Intent myintent1 = getIntent();
        // Lấy Bundle ra khỏi Intent
        Bundle mybundle1 = myintent1.getBundleExtra("dangnhappacket");

        String tk = mybundle1.getString("tk");


        Cursor cursor = dbHelper.layThongtintaikhoan(tk);
        cursor.moveToFirst();
        if (cursor.isAfterLast()==false){
            tv_dmk_tk.setText(cursor.getString(0).toString());
            tv_dmk_mk.setText(cursor.getString(1).toString());
            cursor.moveToNext();
        }
        cursor.close();

        btn_luumk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matkhau, xacnhanmk;
                matkhau = edit_dmk_mk.getText().toString().trim();
                xacnhanmk = edit_dmk_xacnhanmk.getText().toString().trim();
                    if (matkhau.isEmpty()) {
                        edit_dmk_mk.setError("Vui lòng nhập mật khẩu mới");
                        edit_dmk_mk.requestFocus();
                        return;
                    } else if(!matkhau.matches(regex_matkhau)) {
                        edit_dmk_mk.setError("Mật khẩu mới tối thiểu 4 ký tự và không chứa khoảng cách");
                        edit_dmk_mk.requestFocus();
                        edit_dmk_mk.setText("");
                        return;
                    }

                    if (xacnhanmk.isEmpty()) {
                        edit_dmk_xacnhanmk.setError("Vui lòng xác nhận mật khẩu");
                        edit_dmk_xacnhanmk.requestFocus();
                        return;
                    } else if (!xacnhanmk.equals(matkhau)) {
                        edit_dmk_xacnhanmk.setError("Mật khẩu xác nhận không khớp");
                        edit_dmk_xacnhanmk.requestFocus();
                        edit_dmk_xacnhanmk.setText("");
                        return;
                    }


                    boolean ktra = dbHelper.suatMatKhau(tk, matkhau);
                    if (ktra) {
                        Intent myintent = new Intent(khachhang_doimatkhau.this, AccountFragment.class);
                        startActivity(myintent);
                        Toast.makeText(khachhang_doimatkhau.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(khachhang_doimatkhau.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
            }
        });

        img_quaylaittcn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}