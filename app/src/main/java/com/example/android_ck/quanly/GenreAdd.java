package com.example.android_ck.quanly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android_ck.DBHelper;
import com.example.android_ck.R;

public class GenreAdd extends AppCompatActivity {

    ImageButton btn_back,btn_themtheloai_them;
    EditText edt_themtheloai_tentheloai;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanly_theloai_them);
        dbHelper = new DBHelper(this);
        edt_themtheloai_tentheloai = findViewById(R.id.edt_ql_themtheloai_tentheloai);
        btn_themtheloai_them = findViewById(R.id.btn_ql_themtheloai_them);
        btn_back = findViewById(R.id.imgb_theloai_them_trolai);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_themtheloai_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tentheloai = edt_themtheloai_tentheloai.getText().toString();
                if (tentheloai.isEmpty()) {
                    Toast.makeText(GenreAdd.this, "Tên thể loại không được để trống!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean exists = dbHelper.checkGenreExists(tentheloai);
                    if (exists) {
                        Toast.makeText(GenreAdd.this, "Tên thể loại đã tồn tại trong cơ sở dữ liệu!", Toast.LENGTH_SHORT).show();
                    } else {
                        boolean addSuccess = dbHelper.addGenre(tentheloai);
                        if (addSuccess) {
                            Toast.makeText(GenreAdd.this, "Thêm thể loại thành công!", Toast.LENGTH_SHORT).show();

                            finish();
                        } else {
                            Toast.makeText(GenreAdd.this, "Thêm thể loại thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });


    }
}