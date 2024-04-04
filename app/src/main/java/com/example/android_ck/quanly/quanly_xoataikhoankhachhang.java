package com.example.android_ck.quanly;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
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

        userAdapter = new userAdapter(this);
        LinearLayoutManager line = new LinearLayoutManager(this);
        rcv.setLayoutManager(line);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcv.addItemDecoration(itemDecoration);
        userAdapter.setData(getListusers());
        rcv.setAdapter(userAdapter);



        btn_xoatk_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        img_xoatk_quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private List<item_user> getListusers() {
        List<item_user> list = new ArrayList<>();
            List<item_user> danhsachtt = dbHelper.layTatCaThongTinCaNhan();
            list.addAll(danhsachtt);
        return list;
    }



}