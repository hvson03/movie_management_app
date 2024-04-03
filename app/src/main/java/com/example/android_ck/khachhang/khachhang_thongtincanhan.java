package com.example.android_ck.khachhang;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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

import java.util.Calendar;

public class khachhang_thongtincanhan extends AppCompatActivity {
    EditText edit_hoten, edit_ngaysinh, edit_email, edit_sdt;
    RadioGroup rb_gr;
    ImageView img_ngaysinh;
    TextView tv_boqua;
    Button btn_thongtincanhan;
    RadioButton rb_nam, rb_nu;
    DBHelper dbHelper;

    String regex_hoten = "^[a-zA-Zà-Ỹ ]+$";
    String regex_email = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
    String regex_sdt = "^(0|\\+84)(\\d{1,2})(\\d{3})(\\d{4})$";
    String regex_ngaysinh = "^(0?[1-9]|[12][0-9]|3[01])[-/.](0?[1-9]|1[0-2])[-/.](19|20)\\d\\d$";

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
        rb_nam = findViewById(R.id.rb_nam);
        rb_nu = findViewById(R.id.rb_nu);
        tv_boqua = findViewById(R.id.tv_boqua);
        img_ngaysinh = findViewById(R.id.img_ngaysinh);
        btn_thongtincanhan = findViewById(R.id.btn_thongtincanhan);

        dbHelper = new DBHelper(this);
        // Nhận Intent
        Intent myintent1 = getIntent();
        // Lấy Bundle ra khỏi Intent
        Bundle mybundle = myintent1.getBundleExtra("dangkypackage");

        img_ngaysinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(khachhang_thongtincanhan.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String dataString = dayOfMonth + "-" + (month+1) + "-" + year;
                                edit_ngaysinh.setText(dataString);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        btn_thongtincanhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoten, ngaysinh, gioitinh="", email, sdt;
                int ktraRb_bt;
                hoten = edit_hoten.getText().toString().trim();
                ngaysinh = edit_ngaysinh.getText().toString().trim();
                email = edit_email.getText().toString().trim();
                sdt = edit_sdt.getText().toString().trim();
                ktraRb_bt = rb_gr.getCheckedRadioButtonId();

                String tk = mybundle.getString("tk");

                if (ktraRb_bt == R.id.rb_nam) {
                    gioitinh = "nam";
                } else if (ktraRb_bt == R.id.rb_nu) {
                    gioitinh = "nu";
                }

                if (hoten.isEmpty()) {
                    edit_hoten.setError("Vui lòng nhập họ và tên");
                    edit_hoten.requestFocus();
                    return;
                } else if (!hoten.matches(regex_hoten)) {
                    edit_hoten.setError("Họ và tên yêu cầu chỉ nhập chữ cái");
                    edit_hoten.requestFocus();
                    edit_hoten.setText("");
                    return;
                }

                if (ngaysinh.isEmpty()) {
                    edit_ngaysinh.setError("Vui lòng nhập ngày sinh");
                    edit_ngaysinh.requestFocus();
                    return;
                } else if (!ngaysinh.matches(regex_ngaysinh)) {
                    edit_ngaysinh.setError("Yêu cầu định dạng ngày sinh DD-MM-YYYY");
                    edit_ngaysinh.requestFocus();
                    edit_ngaysinh.setText("");
                    return;
                }

                if (email.isEmpty()) {
                    edit_email.setError("Vui lòng nhập email");
                    edit_email.requestFocus();
                    return;
                } else if (!email.matches(regex_email)) {
                    edit_email.setError("Yêu cầu định dạng email email@gmail.com");
                    edit_email.requestFocus();
                    edit_email.setText("");
                    return;
                }

                if (sdt.isEmpty()) {
                    edit_sdt.setError("Vui lòng nhập số điện thoại");
                    edit_sdt.requestFocus();
                    return;
                } else if (!sdt.matches(regex_sdt)) {
                    edit_sdt.setError("Yêu cầu định dạng số điện thoại");
                    edit_sdt.requestFocus();
                    edit_sdt.setText("");
                    return;
                }

                boolean thongtincanhan = dbHelper.themThongTinCaNhan(hoten, gioitinh, ngaysinh, email, sdt, tk);
                if (thongtincanhan) {
                    Toast.makeText(khachhang_thongtincanhan.this, "Thêm thông tin thành công", Toast.LENGTH_SHORT).show();
                    Intent myintent = new Intent(khachhang_thongtincanhan.this, khachhang_dangnhap.class);
                    startActivity(myintent);
                } else {
                    Toast.makeText(khachhang_thongtincanhan.this, "Thêm thông tin không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_boqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert mybundle != null;
                String tk = mybundle.getString("tk");

                boolean thongtincanhan = dbHelper.themThongTinCaNhan("Tài khoản chưa có thông tin", "Đang cập nhật...", "Đang cập nhật...", "Đang cập nhật...", "Đang cập nhật...", tk);
                if (thongtincanhan) {
                    Toast.makeText(khachhang_thongtincanhan.this, "Thêm thông tin thành công", Toast.LENGTH_SHORT).show();
                    Intent myintent = new Intent(khachhang_thongtincanhan.this, khachhang_dangnhap.class);
                    startActivity(myintent);
                } else {
                    Toast.makeText(khachhang_thongtincanhan.this, "Thêm thông tin không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}