package com.example.android_ck.quanly;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
    RecyclerView rcv;
    SearchView sv_xoatk;
    DBHelper dbHelper;
    userAdapter userAdapter;
    List<item_user> mylist;
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
        sv_xoatk = findViewById(R.id.sv_xoatk);
        rcv = findViewById(R.id.rcv);

        mylist = dbHelper.layTatCaThongTinCaNhan();
        userAdapter = new userAdapter(this, dbHelper, mylist);

        rcv.setAdapter(userAdapter);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        userAdapter.updateData(mylist);

        sv_xoatk.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                userAdapter.getFilter().filter(newText);
                return false;
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

    @Override
    protected void onResume() {
        super.onResume();

        List<item_user> itemUsers = dbHelper.layTatCaThongTinCaNhan();
        userAdapter.updateData(itemUsers);
    }
}