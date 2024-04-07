package com.example.android_ck.khachhang;

import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class khachhang_dangky extends AppCompatActivity {
    EditText edit_tk, edit_mk, edit_xacnhanmk, edit_email_dk;
    ImageView img_quaylaidn, btn_dangky;
    DBHelper dbHelper;

    // Regex
    String regex_tentk = "^[a-zA-Z0-9]+$";
    String regex_matkhau = "^[a-zA-Z0-9]{4,}$";
    String regex_email = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_khachhang_dangky);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edit_tk = findViewById(R.id.edit_tk);
        edit_mk = findViewById(R.id.edit_mk);
        edit_email_dk = findViewById(R.id.edit_email_dk);
        edit_xacnhanmk = findViewById(R.id.edit_xacnhanmk);
        img_quaylaidn = findViewById(R.id.img_quaylaidn);
        btn_dangky = findViewById(R.id.btn_dangky);

        dbHelper = new DBHelper(this);

        // Lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();

        // Định dạng ngày
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Format ngày hiện tại và gán vào edit_ngaytao
        String currentDate = dateFormat.format(calendar.getTime());
//        edit_ngaytao.setText(currentDate);

        btn_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tk, mk, xacnhanmk, email;
                tk = edit_tk.getText().toString().trim();
                mk = edit_mk.getText().toString().trim();
                email = edit_email_dk.getText().toString().trim();
                xacnhanmk = edit_xacnhanmk.getText().toString().trim();

                if (tk.isEmpty()) {
                    edit_tk.setError("Vui lòng nhập tên tài khoản");
                    edit_tk.requestFocus();
                    return;
                } else if (!tk.matches(regex_tentk)) {
                    edit_tk.setError("Tên tài khoản không chứa khoảng cách và ký tự đặc biệt");
                    edit_tk.requestFocus();
                    edit_tk.setText("");
                    return;
                }

                if (email.isEmpty()) {
                    edit_email_dk.setError("Vui lòng nhập email");
                    edit_email_dk.requestFocus();
                    return;
                } else if (!email.matches(regex_email)) {
                    edit_email_dk.setError("Yêu cầu định dạng email email@gmail.com");
                    edit_email_dk.requestFocus();
                    edit_email_dk.setText("");
                    return;
                }

                if (mk.isEmpty()) {
                    edit_mk.setError("Vui lòng nhập mật khẩu");
                    edit_mk.requestFocus();
                    return;
                } else if (!mk.matches(regex_matkhau)) {
                    edit_mk.setError("Mật khẩu tối thiểu 4 ký tự và không chứa khoảng cách");
                    edit_mk.requestFocus();
                    edit_mk.setText("");
                    return;
                }

                if (xacnhanmk.isEmpty()) {
                    edit_xacnhanmk.setError("Vui lòng xác nhận mật khẩu");
                    edit_xacnhanmk.requestFocus();
                    return;
                } else if (!xacnhanmk.equals(mk)) {
                    edit_xacnhanmk.setError("Mật khẩu xác nhận không khớp");
                    edit_xacnhanmk.requestFocus();
                    edit_xacnhanmk.setText("");
                    return;
                }

                // Kiểm tra tài khoản đã tồn tại
                if (dbHelper.ktraTen(tk)) {
                    Toast.makeText(khachhang_dangky.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean dangkythanhcong = dbHelper.themTaikhoan(tk, mk, currentDate);
                if (dangkythanhcong) {
                    Intent myintent = new Intent(khachhang_dangky.this, khachhang_thongtincanhan.class);

                    // Đóng gói dữ liệu và đưa dữ liệu vào Bundle
                    Bundle mybundle = new Bundle();
                    mybundle.putString("tk", tk);
                    mybundle.putString("email", email);

                    // Đưa Bundle vào Intent
                    myintent.putExtra("dangkypackage", mybundle);
                    startActivity(myintent);

                    boolean thongtincanhan = dbHelper.themThongTinCaNhan("Tài khoản chưa có thông tin", "Đang cập nhật...", "Đang cập nhật...", email, "Đang cập nhật...", tk);
                    if (thongtincanhan) {
                        Toast.makeText(khachhang_dangky.this, "Đăng ký tài khoản thành công", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(khachhang_dangky.this, "Đăng ký tài khoản không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        img_quaylaidn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}