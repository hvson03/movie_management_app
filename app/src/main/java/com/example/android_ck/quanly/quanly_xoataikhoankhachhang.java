package com.example.android_ck.quanly;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_ck.DBHelper;
import com.example.android_ck.R;
import com.example.android_ck.model.item_user;
import com.example.android_ck.userAdapter;

import java.util.ArrayList;
import java.util.List;

public class quanly_xoataikhoankhachhang extends AppCompatActivity {
    ImageView img_xoatk_quaylai;
    Button btn_xoatk_xoa;
    RecyclerView rcv;

    DBHelper dbHelper;
    userAdapter userAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quanly_xoataikhoankhachhang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DBHelper(this);

        img_xoatk_quaylai = findViewById(R.id.img_xoatk_quaylai);
        btn_xoatk_xoa = findViewById(R.id.btn_xoatk_xoa);
        rcv = findViewById(R.id.rcv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(linearLayoutManager);
        userAdapter = new userAdapter(getListusers());
        rcv.setAdapter(userAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

    }

    private List<item_user> getListusers() {
        List<item_user> list = new ArrayList<>();
        Cursor cursor = dbHelper.layTatCaThongTinCaNhan();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Lấy thông tin từ Cursor
                String tenTaiKhoan = cursor.getString(6);
                String hoTen = cursor.getString(1);

                // Tạo đối tượng item_user và thêm vào danh sách
                item_user user = new item_user(tenTaiKhoan, hoTen);
                list.add(user);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return list;
    }



}